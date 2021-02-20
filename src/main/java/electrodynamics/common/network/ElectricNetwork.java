package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public class ElectricNetwork extends AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> {
	private double networkResistance;
	private double networkVoltage = 0.0;
	private double networkSavedVoltage = 0.0;

	public double getNetworkVoltage() {
		return networkVoltage;
	}

	public double getNetworkSavedVoltage() {
		return networkSavedVoltage;
	}

	public double getNetworkResistance() {
		return networkResistance;
	}

	public ElectricNetwork() {
		this(new HashSet<IConductor>());
	}

	public ElectricNetwork(Collection<? extends IConductor> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public ElectricNetwork(Set<AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack>> networks) {
		for (AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	public ElectricNetwork(Set<ElectricNetwork> networks, boolean special) {
		for (ElectricNetwork net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	@Override
	public TransferPack emit(TransferPack maxTransfer, ArrayList<TileEntity> ignored) {
		if (networkVoltage == 0.0) {
			networkVoltage = maxTransfer.getVoltage();
		}
		if (maxTransfer.getJoules() > 0) {
			Set<TileEntity> availableAcceptors = new HashSet<>();
			availableAcceptors.addAll(getEnergyAcceptors());
			double joulesSent = 0;
			availableAcceptors.removeAll(ignored);
			if (!availableAcceptors.isEmpty()) {
				Iterator<TileEntity> it = availableAcceptors.iterator();
				while (it.hasNext()) {
					TileEntity receiver = it.next();
					if (acceptorInputMap.containsKey(receiver)) {
						boolean shouldRemove = true;
						for (Direction connection : acceptorInputMap.get(receiver)) {
							TransferPack pack = ElectricityUtilities.receivePower(receiver, connection, maxTransfer, true);
							if (pack.getJoules() != 0) {
								shouldRemove = false;
								break;
							}
						}
						if (shouldRemove) {
							it.remove();
						}
					}
				}
				TransferPack perReceiver = TransferPack.joulesVoltage(maxTransfer.getJoules() / availableAcceptors.size() / networkResistance, maxTransfer.getVoltage());
				for (TileEntity receiver : availableAcceptors) {
					if (acceptorInputMap.containsKey(receiver)) {
						for (Direction connection : acceptorInputMap.get(receiver)) {
							TransferPack pack = ElectricityUtilities.receivePower(receiver, connection, perReceiver, false);
							joulesSent += pack.getJoules();
							transmittedThisTick += pack.getJoules();
						}
						checkForOverload(TransferPack.joulesVoltage(transmittedThisTick, perReceiver.getVoltage()));
					}
				}

			}
			if (joulesSent > 0.0) {
				double lost = maxTransfer.getJoules() - maxTransfer.getJoules() / networkResistance;
				joulesSent += lost;
			}
			return TransferPack.joulesVoltage(joulesSent, maxTransfer.getVoltage());
		}
		return TransferPack.EMPTY;
	}

	public Set<TileEntity> getEnergyAcceptors() {
		Set<TileEntity> toReturn = new HashSet<>();
		for (TileEntity acceptor : acceptorSet) {
			if (ElectricityUtilities.isElectricReceiver(acceptor)) {
				for (Direction connection : acceptorInputMap.get(acceptor)) {
					if (ElectricityUtilities.canInputPower(acceptor, connection)) {
						toReturn.add(acceptor);
					}
				}
			}
		}
		return toReturn;
	}

	private boolean checkForOverload(TransferPack attemptSend) {
		if (attemptSend.getAmps() >= networkMaxTransfer) {
			HashSet<SubtypeWire> checkList = new HashSet<>();
			for (SubtypeWire type : SubtypeWire.values()) {
				if (type.maxAmps <= attemptSend.getAmps()) {
					checkList.add(type);
				}
			}
			for (SubtypeWire index : checkList) {
				for (IConductor conductor : conductorTypeMap.get(index)) {
					conductor.destroyViolently();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void updateStatistics(IConductor cable) {
		super.updateStatistics(cable);
		networkResistance += cable.getWireType().resistance;
	}

	@Override
	public void updateStatistics() {
		networkResistance = 1;
		super.updateStatistics();
	}

	@Override
	public void tick() {
		super.tick();
		networkSavedVoltage = networkVoltage;
		networkVoltage = 0;
	}

	@Override
	public boolean isConductor(TileEntity tile) {
		return ElectricityUtilities.isConductor(tile);
	}

	@Override
	public boolean isAcceptor(TileEntity acceptor, Direction orientation) {
		return ElectricityUtilities.isElectricReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> createInstance() {
		return new ElectricNetwork();
	}

	@Override
	public AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> createInstanceConductor(Set<IConductor> conductors) {
		return new ElectricNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> createInstance(Set<AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack>> networks) {
		return new ElectricNetwork(networks);

	}

	@Override
	public SubtypeWire[] getConductorTypes() {
		return SubtypeWire.values();
	}

	@Override
	public boolean canConnect(TileEntity acceptor, Direction orientation) {
		return ElectricityUtilities.canInputPower(acceptor, orientation.getOpposite());
	}
}
