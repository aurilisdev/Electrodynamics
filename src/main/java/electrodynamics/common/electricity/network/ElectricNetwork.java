package electrodynamics.common.electricity.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import electrodynamics.api.conductor.IConductor;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.electricity.ElectricityUtilities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectricNetwork {
	public HashSet<IConductor> conductorSet = new HashSet<>();
	private HashSet<TileEntity> acceptorSet = new HashSet<>();
	private HashMap<TileEntity, HashSet<Direction>> acceptorInputMap = new HashMap<>();
	private HashMap<SubtypeWire, HashSet<IConductor>> conductorTypeMap = new HashMap<>();
	private double ampsTransmittedBuffer = 0;
	private double ampsTransmittedSavedBuffer = 0;
	private HashSet<Double> transferVoltages = new HashSet<>();
	private HashSet<Double> transferSavedVoltages = new HashSet<>();
	private double networkMaxTransfer = 0;
	private double networkResistance = 0;
	private double fixTimerTicksSinceNetworkCreate = 1180;
	private double ticksSinceNetworkRefresh = 0;
	private boolean fixed = false;

	public double getSavedAmpsTransmissionBuffer() {
		return ampsTransmittedSavedBuffer;
	}

	public double getNetworkMaxTransfer() {
		return networkMaxTransfer;
	}

	public HashSet<Double> getTransferVoltages() {
		return transferSavedVoltages;
	}

	public ElectricNetwork(IConductor... varCables) {
		conductorSet.addAll(Arrays.asList(varCables));
		ElectricNetworkRegistry.registerNetwork(this);
	}

	public ElectricNetwork(Set<ElectricNetwork> networks) {
		for (ElectricNetwork net : networks) {
			if (net != null) {
				addAllCables(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		ElectricNetworkRegistry.registerNetwork(this);
	}

	public TransferPack emit(TransferPack maxTransfer, ArrayList<TileEntity> ignored) {// TODO: Remove this emission setup. Rather add the transfer power here to some
		if (maxTransfer.getJoules() > 0) {
			Set<TileEntity> availableAcceptors = getEnergyAcceptors();
			double ampsSent = 0;
			if (!availableAcceptors.isEmpty()) {
				HashSet<TileEntity> validAcceptors = new HashSet<>();
				for (TileEntity acceptor : availableAcceptors) {
					if (!ignored.contains(acceptor)) {
						if (ElectricityUtilities.isElectricReceiver(acceptor)) {
							validAcceptors.add(acceptor);
						}
					}
				}

				if (validAcceptors.size() > 0) {
					TransferPack perReceiver = TransferPack.ampsVoltage((maxTransfer.getAmps() / (validAcceptors.size()) / networkResistance), maxTransfer.getVoltage());
					for (TileEntity receiver : validAcceptors) {
						if (!checkForOverload(TransferPack.ampsVoltage(ampsTransmittedBuffer, perReceiver.getVoltage()))) {
							for (Direction connection : acceptorInputMap.get(receiver)) {
								TransferPack pack = ElectricityUtilities.receivePower(receiver, connection, perReceiver, false);
								ampsSent += pack.getAmps();
								ampsTransmittedBuffer += pack.getAmps();
							}
						}

					}
				}
			}
			if (ampsSent > 0.0) {
				double lost = maxTransfer.getAmps() - maxTransfer.getAmps() / networkResistance;
				transferVoltages.add(maxTransfer.getVoltage());
				ampsSent += lost;
			}
			return TransferPack.ampsVoltage(ampsSent, maxTransfer.getVoltage());
		}
		return TransferPack.EMPTY;

	}

	public double getNetworkResistance() {
		return networkResistance;
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

	public TileEntity[] getConnectedEnergyAcceptors(TileEntity tileEntity) {
		TileEntity[] acceptors = { null, null, null, null, null, null };
		for (Direction orientation : Direction.values()) {
			TileEntity acceptor = tileEntity.getWorld().getTileEntity(new BlockPos(tileEntity.getPos()).add(orientation.getXOffset(), orientation.getYOffset(), orientation.getZOffset()));
			if (ElectricityUtilities.isElectricReceiver(acceptor) && !ElectricityUtilities.isConductor(acceptor) && acceptor instanceof IElectricTile
					&& ((IElectricTile) acceptor).canConnectElectrically(orientation.getOpposite())) {
				acceptors[orientation.getOpposite().ordinal()] = acceptor;
			}
		}
		return acceptors;
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
			TileEntity[] acceptors = getConnectedEnergyAcceptors((TileEntity) cable);
			for (TileEntity acceptor : acceptors) {
				if (acceptor != null && !ElectricityUtilities.isConductor(acceptor)) {
					acceptorSet.add(acceptor);
					HashSet<Direction> directions = acceptorInputMap.containsKey(acceptor) ? acceptorInputMap.get(acceptor) : new HashSet<>();
					directions.add(Direction.values()[Arrays.asList(acceptors).indexOf(acceptor)]);
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
					NetworkFinder finder = new NetworkFinder(((TileEntity) splitPoint).getWorld(), new BlockPos(connectedBlockA.getPos()), new BlockPos[] { new BlockPos(((TileEntity) splitPoint).getPos()) });
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
			NetworkFinder finder = new NetworkFinder(((TileEntity) cable).getWorld(), new BlockPos(((TileEntity) cable).getPos()));
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
		ElectricNetworkRegistry.removeNetwork(this);
	}

	public static class NetworkFinder {
		public World worldObj;
		public BlockPos start;
		public List<BlockPos> iterated = new ArrayList<>();
		public List<BlockPos> toIgnore = new ArrayList<>();

		public NetworkFinder(World world, BlockPos location, BlockPos... ignore) {
			worldObj = world;
			start = location;
			if (ignore.length > 0) {
				toIgnore = Arrays.asList(ignore);
			}
		}

		public void loopAll(BlockPos location) {
			if (worldObj.getTileEntity(location) instanceof IConductor) {
				iterated.add(location);
			}
			for (Direction direction : Direction.values()) {
				BlockPos obj = new BlockPos(location).add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
				if (!iterated.contains(obj) && !toIgnore.contains(obj)) {
					TileEntity tileEntity = worldObj.getTileEntity(obj);
					if (ElectricityUtilities.isConductor(tileEntity)) {
						loopAll(obj);
					}
				}
			}
		}

		public List<BlockPos> exploreNetwork() {
			loopAll(start);

			return iterated;
		}
	}

	public void tick() {
		clearTransmissionBuffer();
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

	public void clearTransmissionBuffer() {
		ampsTransmittedSavedBuffer = ampsTransmittedBuffer;
		ampsTransmittedBuffer = 0;
		transferSavedVoltages.addAll(transferVoltages);
		transferVoltages.clear();
	}

}