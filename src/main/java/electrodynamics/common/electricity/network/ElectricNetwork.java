package electrodynamics.common.electricity.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import electrodynamics.api.network.ITickableNetwork;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.electricity.ElectricityUtilities;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class ElectricNetwork implements ITickableNetwork {
	public HashSet<IConductor> conductorSet = new HashSet<>();
	private HashSet<TileEntity> acceptorSet = new HashSet<>();
	private HashMap<TileEntity, HashSet<Direction>> acceptorInputMap = new HashMap<>();
	private HashMap<SubtypeWire, HashSet<IConductor>> conductorTypeMap = new HashMap<>();
	private double joulesTransmittedBuffer = 0;
	private double joulesTransmittedSavedBuffer = 0;
	private double lockedVoltage = 0.0;
	private double lockedSavedVoltage = 0.0;
	private double networkMaxTransfer = 0;
	private double networkResistance = 0;
	private double fixTimerTicksSinceNetworkCreate = 1180;
	private double ticksSinceNetworkRefresh = 0;
	private boolean fixed = false;

	public double getSavedAmpsTransmissionBuffer() {
		return joulesTransmittedSavedBuffer;
	}

	public double getNetworkMaxTransfer() {
		return networkMaxTransfer;
	}

	public double getLockedSavedVoltage() {
		return lockedSavedVoltage;
	}

	public double getNetworkResistance() {
		return networkResistance;
	}

	public ElectricNetwork(IConductor... varCables) {
		conductorSet.addAll(Arrays.asList(varCables));
		NetworkRegistry.register(this);
	}

	public ElectricNetwork(Set<ElectricNetwork> networks) {
		for (ElectricNetwork net : networks) {
			if (net != null) {
				addAllCables(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	public TransferPack emit(TransferPack maxTransfer, ArrayList<TileEntity> ignored) {
		if ((lockedVoltage == 0 || lockedVoltage == maxTransfer.getVoltage()) && maxTransfer.getJoules() > 0) {
			Set<TileEntity> availableAcceptors = getEnergyAcceptors();
			double joulesSent = 0;
			availableAcceptors.removeAll(ignored);
			if (!availableAcceptors.isEmpty()) {
				if (lockedVoltage == 0.0) {
					lockedVoltage = maxTransfer.getVoltage();
				}
				TransferPack perReceiver = TransferPack.joulesVoltage(maxTransfer.getJoules() / availableAcceptors.size() / networkResistance, maxTransfer.getVoltage());
				for (TileEntity receiver : availableAcceptors) {
					if (acceptorInputMap.containsKey(receiver)) {
						for (Direction connection : acceptorInputMap.get(receiver)) {
							TransferPack pack = ElectricityUtilities.receivePower(receiver, connection, perReceiver, false);
							joulesSent += pack.getJoules();
							joulesTransmittedBuffer += pack.getJoules();
						}
						checkForOverload(TransferPack.joulesVoltage(joulesTransmittedBuffer, perReceiver.getVoltage()));
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

	public void refresh() {
		@SuppressWarnings("unchecked")
		Set<IConductor> iterCables = (Set<IConductor>) conductorSet.clone();
		Iterator<IConductor> it = iterCables.iterator();

		acceptorSet.clear();
		acceptorInputMap.clear();
		while (it.hasNext()) {
			IConductor conductor = it.next();
			if (conductor == null || ((TileEntity) conductor).isRemoved()) {
				it.remove();
				conductorSet.remove(conductor);
			} else {
				conductor.setNetwork(this);
			}
		}
		for (IConductor cable : iterCables) {
			TileEntity tileEntity = (TileEntity) cable;
			for (Direction orientation : Direction.values()) {
				TileEntity acceptor = tileEntity.getWorld().getTileEntity(new BlockPos(tileEntity.getPos()).add(orientation.getXOffset(), orientation.getYOffset(), orientation.getZOffset()));
				if (ElectricityUtilities.isElectricReceiver(acceptor) && !ElectricityUtilities.isConductor(acceptor) && acceptor instanceof IElectricTile
						&& ((IElectricTile) acceptor).canConnectElectrically(orientation.getOpposite())) {
					acceptorSet.add(acceptor);
					HashSet<Direction> directions = acceptorInputMap.containsKey(acceptor) ? acceptorInputMap.get(acceptor) : new HashSet<>();
					directions.add(orientation.getOpposite());
					acceptorInputMap.put(acceptor, directions);
				}
			}
		}
		updateStatistics();
	}

	private void updateStatistics() {
		networkMaxTransfer = 0;
		networkResistance = 1;
		conductorTypeMap.clear();
		for (SubtypeWire type : SubtypeWire.values()) {
			conductorTypeMap.put(type, new HashSet<>());
		}
		for (IConductor cable : conductorSet) {
			conductorTypeMap.get(cable.getWireType()).add(cable);
			networkMaxTransfer = networkMaxTransfer == 0 ? cable.getWireType().maxAmps : Math.min(networkMaxTransfer, cable.getWireType().maxAmps);
			networkResistance += cable.getWireType().resistance;
		}
	}

	public void merge(ElectricNetwork network) {
		if (network != null && network != this) {
			Set<ElectricNetwork> networks = new HashSet<>();
			networks.add(this);
			networks.add(network);
			ElectricNetwork newNetwork = new ElectricNetwork(networks);
			newNetwork.refresh();
		}
	}

	public void addAllCables(Set<IConductor> newCables) {
		conductorSet.addAll(newCables);
	}

	public void split(IConductor splitPoint) {
		if (splitPoint instanceof TileEntity) {
			removeWire(splitPoint);
			TileEntity[] connectedTiles = new TileEntity[6];
			boolean[] dealtWith = { false, false, false, false, false, false };
			for (Direction direction : Direction.values()) {
				TileEntity sideTile = ((TileEntity) splitPoint).getWorld().getTileEntity(new BlockPos(((TileEntity) splitPoint).getPos()).add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset()));
				if (sideTile != null) {
					connectedTiles[Arrays.asList(Direction.values()).indexOf(direction)] = sideTile;
				}
			}
			for (int countOne = 0; countOne < connectedTiles.length; countOne++) {
				TileEntity connectedBlockA = connectedTiles[countOne];
				if (connectedBlockA instanceof IConductor && dealtWith[countOne] == false) {
					ElectricNetworkFinder finder = new ElectricNetworkFinder(((TileEntity) splitPoint).getWorld(), new BlockPos(connectedBlockA.getPos()),
							new BlockPos[] { new BlockPos(((TileEntity) splitPoint).getPos()) });
					List<BlockPos> partNetwork = finder.exploreNetwork();
					for (int countTwo = countOne + 1; countTwo < connectedTiles.length; countTwo++) {
						TileEntity connectedBlockB = connectedTiles[countTwo];
						if (connectedBlockB instanceof IConductor && dealtWith[countTwo] == false) {
							if (partNetwork.contains(new BlockPos(connectedBlockB.getPos()))) {
								dealtWith[countTwo] = true;
							}
						}
					}
					ElectricNetwork newNetwork = new ElectricNetwork(new IConductor[0]);
					for (BlockPos node : finder.iterated) {
						TileEntity nodeTile = ((TileEntity) splitPoint).getWorld().getTileEntity(node);
						if (ElectricityUtilities.isConductor(nodeTile)) {
							if (nodeTile != splitPoint) {
								newNetwork.conductorSet.add((IConductor) nodeTile);
							}
						}
					}
					newNetwork.refresh();
				}
			}
			deregister();
		}
	}

	public void fixMessedUpNetwork(IConductor cable) {
		if (cable instanceof TileEntity) {
			ElectricNetworkFinder finder = new ElectricNetworkFinder(((TileEntity) cable).getWorld(), new BlockPos(((TileEntity) cable).getPos()));
			List<BlockPos> partNetwork = finder.exploreNetwork();
			Set<IConductor> newCables = new HashSet<>();
			for (BlockPos node : partNetwork) {
				TileEntity nodeTile = ((TileEntity) cable).getWorld().getTileEntity(node);
				if (ElectricityUtilities.isConductor(nodeTile)) {
					((IConductor) nodeTile).removeFromNetwork();
					newCables.add((IConductor) nodeTile);
				}
			}
			ElectricNetwork newNetwork = new ElectricNetwork(newCables.toArray(new IConductor[0]));
			newNetwork.refresh();
			newNetwork.fixed = true;
			deregister();
		}
	}

	public void removeWire(IConductor cable) {
		conductorSet.remove(cable);
		if (conductorSet.size() == 0) {
			deregister();
		}
	}

	public void deregister() {
		conductorSet.clear();
		NetworkRegistry.deregister(this);
	}

	@Override
	public int getSize() {
		return conductorSet.size();
	}

	@Override
	public void tick() {
		joulesTransmittedSavedBuffer = joulesTransmittedBuffer;
		joulesTransmittedBuffer = 0;
		lockedSavedVoltage = lockedVoltage;
		lockedVoltage = 0;
		if (!fixed) {
			fixTimerTicksSinceNetworkCreate += 1;
			if (fixTimerTicksSinceNetworkCreate > 1200) {
				fixTimerTicksSinceNetworkCreate = 0;
				fixMessedUpNetwork(conductorSet.toArray(new IConductor[0])[0]);
			}

		}
		ticksSinceNetworkRefresh++;
		if (ticksSinceNetworkRefresh > 1600) {
			ticksSinceNetworkRefresh = 0;
			refresh();
		}
	}

}