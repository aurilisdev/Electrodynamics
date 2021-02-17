package electrodynamics.api.networks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import electrodynamics.api.network.ITickableNetwork;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import universalcables.common.tile.TileUniversalCable;

public abstract class AbstractNetwork<Conductor extends IAbstractConductor, ConductorType, Acceptor, TransferPacket> implements ITickableNetwork {
	public HashSet<Conductor> conductorSet = new HashSet<>();
	public HashSet<Acceptor> acceptorSet = new HashSet<>();
	public HashMap<Acceptor, HashSet<Direction>> acceptorInputMap = new HashMap<>();
	public HashMap<ConductorType, HashSet<Conductor>> conductorTypeMap = new HashMap<>();
	public double networkMaxTransfer;
	public double transmittedLastTick;
	public double transmittedThisTick;
	public long ticksSinceNetworkRefresh;
	public long ticksSinceLastFix = 1180;
	public boolean fixed;

	@SuppressWarnings("unchecked")
	public void refresh() {
		Set<Conductor> iterCables = (Set<Conductor>) conductorSet.clone();
		Iterator<Conductor> it = iterCables.iterator();
		acceptorSet.clear();
		acceptorInputMap.clear();
		while (it.hasNext()) {
			Conductor conductor = it.next();
			if (conductor == null || ((TileEntity) conductor).isRemoved()) {
				it.remove();
				conductorSet.remove(conductor);
			} else {
				conductor.setNetwork(this);
			}
		}
		for (Conductor conductor : iterCables) {
			TileEntity tileEntity = (TileEntity) conductor;
			for (Direction direction : Direction.values()) {
				TileEntity acceptor = tileEntity.getWorld().getTileEntity(new BlockPos(tileEntity.getPos()).add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset()));
				if(acceptor instanceof TileUniversalCable)
				{
					System.out.println("lol");
				}
				if (!isConductor(acceptor)) {
					if (isAcceptor(acceptor, direction)) {
						if (canConnect(acceptor, direction)) {
							acceptorSet.add((Acceptor) acceptor);
							HashSet<Direction> directions = acceptorInputMap.containsKey(acceptor) ? acceptorInputMap.get(acceptor) : new HashSet<>();
							directions.add(direction.getOpposite());
							acceptorInputMap.put((Acceptor) acceptor, directions);
						}
					}
				}
			}
		}
		updateStatistics();
	}

	public double getTransmittedLastTick() {
		return transmittedLastTick;
	}

	public void updateStatistics() {
		conductorTypeMap.clear();
		for (ConductorType type : getConductorTypes()) {
			conductorTypeMap.put(type, new HashSet<>());
		}
		for (Conductor wire : conductorSet) {
			conductorTypeMap.get(wire.getConductorType()).add(wire);
			networkMaxTransfer = networkMaxTransfer == 0 ? wire.getMaxTransfer() : Math.min(networkMaxTransfer, wire.getMaxTransfer());
			updateStatistics(wire);
		}
	}

	public void updateStatistics(Conductor cable) {
	}

	@SuppressWarnings("unchecked")
	public void split(Conductor splitPoint) {
		if (splitPoint instanceof TileEntity) {
			removeFromNetwork(splitPoint);
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
				if (isConductor(connectedBlockA) && dealtWith[countOne] == false) {
					AbstractNetworkFinder finder = new AbstractNetworkFinder(((TileEntity) splitPoint).getWorld(), new BlockPos(connectedBlockA.getPos()), this,
							new BlockPos[] { new BlockPos(((TileEntity) splitPoint).getPos()) });
					List<BlockPos> partNetwork = finder.exploreNetwork();
					for (int countTwo = countOne + 1; countTwo < connectedTiles.length; countTwo++) {
						TileEntity connectedBlockB = connectedTiles[countTwo];
						if (isConductor(connectedBlockB) && dealtWith[countTwo] == false) {
							if (partNetwork.contains(new BlockPos(connectedBlockB.getPos()))) {
								dealtWith[countTwo] = true;
							}
						}
					}
					AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> newNetwork = createInstance();
					for (BlockPos node : finder.iterated) {
						TileEntity nodeTile = ((TileEntity) splitPoint).getWorld().getTileEntity(node);
						if (isConductor(nodeTile)) {
							if (nodeTile != splitPoint) {
								newNetwork.conductorSet.add((Conductor) nodeTile);
							}
						}
					}
					newNetwork.refresh();
				}
			}
			deregister();
		}
	}

	public void merge(AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> network) {
		if (network != null && network != this) {
			Set<AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket>> networks = new HashSet<>();
			networks.add(this);
			networks.add(network);
			AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> newNetwork = createInstance(networks);
			newNetwork.refresh();
		}
	}

	@SuppressWarnings("unchecked")
	public void fixMessedUpNetwork(Conductor cable) {
		if (cable instanceof TileEntity) {
			AbstractNetworkFinder finder = new AbstractNetworkFinder(((TileEntity) cable).getWorld(), new BlockPos(((TileEntity) cable).getPos()), this);
			List<BlockPos> partNetwork = finder.exploreNetwork();
			Set<Conductor> newConductors = new HashSet<>();
			for (BlockPos nodePosition : partNetwork) {
				TileEntity nodeTile = ((TileEntity) cable).getWorld().getTileEntity(nodePosition);
				if (isConductor(nodeTile)) {
					Conductor nodeConductor = (Conductor) nodeTile;
					nodeConductor.removeFromNetwork();
					newConductors.add(nodeConductor);
				}
			}
			AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> newNetwork = createInstanceConductor(newConductors);
			newNetwork.refresh();
			newNetwork.fixed = true;
			deregister();
		}
	}

	public void removeFromNetwork(Conductor conductor) {
		conductorSet.remove(conductor);
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

	@SuppressWarnings("unchecked")
	@Override
	public void tick() {
		transmittedLastTick = transmittedThisTick;
		transmittedThisTick = 0;
		if (!fixed) {
			ticksSinceLastFix += 1;
			if (ticksSinceLastFix > 1200) {
				ticksSinceLastFix = 0;
				if (conductorSet.size() > 0) {
					fixMessedUpNetwork((Conductor) conductorSet.toArray()[0]);
				} else {
					deregister();
				}
			}

		}
		ticksSinceNetworkRefresh++;
		if (ticksSinceNetworkRefresh > 1600) {
			ticksSinceNetworkRefresh = 0;
			refresh();
		}
	}

	public abstract TransferPacket emit(TransferPacket transfer, ArrayList<TileEntity> ignored);

	public abstract boolean isConductor(TileEntity tile);

	public abstract boolean isAcceptor(TileEntity acceptor, Direction orientation);

	public abstract boolean canConnect(TileEntity acceptor, Direction orientation);

	public abstract AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> createInstance();

	public abstract AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> createInstanceConductor(Set<Conductor> conductors);

	public abstract AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket> createInstance(Set<AbstractNetwork<Conductor, ConductorType, Acceptor, TransferPacket>> networks);

	public abstract ConductorType[] getConductorTypes();
}