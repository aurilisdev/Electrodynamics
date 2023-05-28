package electrodynamics.prefab.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import electrodynamics.api.network.ITickableNetwork;
import electrodynamics.api.network.cable.IAbstractConductor;
import electrodynamics.api.network.util.AbstractNetworkFinder;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AbstractNetwork<C extends IAbstractConductor, T, A, P> implements ITickableNetwork {
	public HashSet<C> conductorSet = new HashSet<>();
	public HashSet<A> acceptorSet = new HashSet<>();
	public HashMap<A, HashSet<Direction>> acceptorInputMap = new HashMap<>();
	public HashMap<T, HashSet<C>> conductorTypeMap = new HashMap<>();
	public double networkMaxTransfer;
	public double transmittedLastTick;
	public double transmittedThisTick;
	public boolean fixed;

	public void refresh() {
		Iterator<C> it = conductorSet.iterator();
		acceptorSet.clear();
		acceptorInputMap.clear();
		while (it.hasNext()) {
			C conductor = it.next();
			if (conductor == null || ((BlockEntity) conductor).isRemoved()) {
				it.remove();
			} else {
				conductor.setNetwork(this);
			}
		}
		for (C conductor : conductorSet) {
			BlockEntity tileEntity = (BlockEntity) conductor;
			for (Direction direction : Direction.values()) {
				BlockEntity acceptor = tileEntity.getLevel().getBlockEntity(new BlockPos(tileEntity.getBlockPos()).offset(direction.getNormal()));
				if (acceptor != null && !isConductor(acceptor)) {
					if (isAcceptor(acceptor, direction)) {
						if (canConnect(acceptor, direction)) {
							acceptorSet.add((A) acceptor);
							updateRecieverStatistics((A) acceptor, direction);
							HashSet<Direction> directions = acceptorInputMap.containsKey(acceptor) ? acceptorInputMap.get(acceptor) : new HashSet<>();
							directions.add(direction.getOpposite());
							acceptorInputMap.put((A) acceptor, directions);
						}
					}
				}
			}
		}
		updateStatistics();
	}

	public double getActiveTransmitted() {
		return transmittedLastTick;
	}

	public void updateStatistics() {
		conductorTypeMap.clear();
		for (T type : getConductorTypes()) {
			conductorTypeMap.put(type, new HashSet<>());
		}
		for (C wire : conductorSet) {
			conductorTypeMap.get(wire.getConductorType()).add(wire);
			networkMaxTransfer = networkMaxTransfer == 0 ? wire.getMaxTransfer() : Math.min(networkMaxTransfer, wire.getMaxTransfer());
			updateConductorStatistics(wire);
		}
		for(A reciever : acceptorSet) {
			for(Direction dir : acceptorInputMap.getOrDefault(reciever, new HashSet<>())) {
				updateRecieverStatistics(reciever, dir);
			}
		}
	}

	/**
	 * Override method to define statistics per-cable
	 * 
	 * @param cable
	 */
	public void updateConductorStatistics(C cable) {
		// Just a default method
	}
	
	/**
	 * Override method to define statistics per-receiver
	 *  
	 * @param reciever
	 */
	public void updateRecieverStatistics(A reciever, Direction dir) {
		
	}

	public void split(@Nonnull C splitPoint) {
		if (splitPoint instanceof BlockEntity blockentity) {
			removeFromNetwork(splitPoint);
			BlockEntity[] connectedTiles = new BlockEntity[6];
			boolean[] dealtWith = { false, false, false, false, false, false };
			for (Direction direction : Direction.values()) {
				BlockPos ex = blockentity.getBlockPos().offset(direction.getNormal());
				if (blockentity.getLevel().hasChunkAt(ex)) {
					BlockEntity sideTile = blockentity.getLevel().getBlockEntity(ex);
					if (sideTile != null) {
						connectedTiles[Arrays.asList(Direction.values()).indexOf(direction)] = sideTile;
					}
				}
			}
			for (int countOne = 0; countOne < connectedTiles.length; countOne++) {
				BlockEntity connectedBlockA = connectedTiles[countOne];
				if (connectedBlockA != null) {
					if (isConductor(connectedBlockA) && !dealtWith[countOne]) {
						AbstractNetworkFinder finder = new AbstractNetworkFinder(blockentity.getLevel(), connectedBlockA.getBlockPos(), this, blockentity.getBlockPos());
						List<BlockEntity> partNetwork = finder.exploreNetwork();
						for (int countTwo = countOne + 1; countTwo < connectedTiles.length; countTwo++) {
							BlockEntity connectedBlockB = connectedTiles[countTwo];
							if (isConductor(connectedBlockB) && !dealtWith[countTwo] && partNetwork.contains(connectedBlockB)) {
								dealtWith[countTwo] = true;
							}
						}
						AbstractNetwork<C, T, A, P> newNetwork = createInstance();

						for (BlockEntity tile : finder.iteratedTiles) {
							if (tile != splitPoint) {
								newNetwork.conductorSet.add((C) tile);
							}
						}
						newNetwork.refresh();
					}
				}
			}
			deregister();
		}
	}

	public void merge(AbstractNetwork<C, T, A, P> network) {
		if (network != null && network != this) {
			Set<AbstractNetwork<C, T, A, P>> networks = new HashSet<>();
			networks.add(this);
			networks.add(network);
			AbstractNetwork<C, T, A, P> newNetwork = createInstance(networks);
			newNetwork.refresh();
		}
	}

	public void removeFromNetwork(C conductor) {
		conductorSet.remove(conductor);
		if (conductorSet.isEmpty()) {
			deregister();
		}
	}

	public void deregister() {
		conductorSet.clear();
		acceptorSet.clear();
		acceptorInputMap.clear();
		conductorTypeMap.clear();
		NetworkRegistry.deregister(this);
	}

	@Override
	public int getSize() {
		return conductorSet.size();
	}

	@Override
	public void tick() {
		transmittedLastTick = transmittedThisTick;
		transmittedThisTick = 0;
	}

	public double getNetworkMaxTransfer() {
		return networkMaxTransfer;
	}

	public P emit(P transfer, ArrayList<BlockEntity> ignored, boolean debug) {
		return null;
	}

	public abstract boolean isConductor(BlockEntity tile);

	public abstract boolean isAcceptor(BlockEntity acceptor, Direction orientation);

	public abstract boolean canConnect(BlockEntity acceptor, Direction orientation);

	public abstract AbstractNetwork<C, T, A, P> createInstance();

	public abstract AbstractNetwork<C, T, A, P> createInstanceConductor(Set<C> conductors);

	public abstract AbstractNetwork<C, T, A, P> createInstance(Set<AbstractNetwork<C, T, A, P>> networks);

	public abstract T[] getConductorTypes();

}