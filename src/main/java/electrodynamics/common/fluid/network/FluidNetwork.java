package electrodynamics.common.fluid.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import electrodynamics.api.network.pipe.IPipe;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.fluid.FluidUtilities;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public class FluidNetwork extends AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer> {
	public FluidNetwork() {
		this(new HashSet<IPipe>());
	}

	public FluidNetwork(Collection<? extends IPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer>> networks) {
		for (AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer> net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<FluidNetwork> networks, boolean special) {
		for (FluidNetwork net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	@Override
	public Integer emit(Integer maxTransfer, ArrayList<TileEntity> ignored) {
		if (maxTransfer > 0) {
			Set<TileEntity> availableAcceptors = getEnergyAcceptors();
			Integer joulesSent = 0;
			availableAcceptors.removeAll(ignored);
			if (!availableAcceptors.isEmpty()) {
				Integer perReceiver = maxTransfer / availableAcceptors.size();
				for (TileEntity receiver : availableAcceptors) {
					if (acceptorInputMap.containsKey(receiver)) {
						for (Direction connection : acceptorInputMap.get(receiver)) {
							int rec = FluidUtilities.receivePower(receiver, connection, perReceiver, false);
							joulesSent += rec;
							transmittedThisTick += rec;
						}
						checkForOverload((int) transmittedThisTick);
					}
				}
			}
			return joulesSent;
		}
		return 0;
	}

	public Set<TileEntity> getEnergyAcceptors() {
		Set<TileEntity> toReturn = new HashSet<>();
		for (TileEntity acceptor : acceptorSet) {
			if (FluidUtilities.isFluidReceiver(acceptor)) {
				for (Direction connection : acceptorInputMap.get(acceptor)) {
					if (FluidUtilities.canInputPower(acceptor, connection)) {
						toReturn.add(acceptor);
					}
				}
			}
		}
		return toReturn;
	}

	private boolean checkForOverload(Integer attemptSend) {
		if (attemptSend >= networkMaxTransfer) {
			HashSet<SubtypePipe> checkList = new HashSet<>();
			for (SubtypePipe type : SubtypePipe.values()) {
				if (type.maxTransfer <= attemptSend) {
					checkList.add(type);
				}
			}
			for (SubtypePipe index : checkList) {
				for (IPipe conductor : conductorTypeMap.get(index)) {
					conductor.destroyViolently();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isConductor(TileEntity tile) {
		return tile instanceof IPipe;
	}

	@Override
	public boolean isAcceptor(TileEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer> createInstance() {
		return new FluidNetwork();
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer> createInstanceConductor(Set<IPipe> conductors) {
		return new FluidNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer> createInstance(Set<AbstractNetwork<IPipe, SubtypePipe, TileEntity, Integer>> networks) {
		return new FluidNetwork(networks);

	}

	@Override
	public SubtypePipe[] getConductorTypes() {
		return SubtypePipe.values();
	}

	@Override
	public boolean canConnect(TileEntity acceptor, Direction orientation) {
		return acceptor instanceof IElectricTile && FluidUtilities.canInputPower(acceptor, orientation.getOpposite());
	}
}
