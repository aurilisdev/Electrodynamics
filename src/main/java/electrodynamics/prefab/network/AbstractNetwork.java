package electrodynamics.prefab.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import electrodynamics.api.network.AbstractNetworkFinder;
import electrodynamics.api.network.IAbstractConductor;
import electrodynamics.api.network.ITickableNetwork;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

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
	    if (conductor == null || ((TileEntity) conductor).isRemoved()) {
		it.remove();
	    } else {
		conductor.setNetwork(this);
	    }
	}
	for (C conductor : conductorSet) {
	    TileEntity tileEntity = (TileEntity) conductor;
	    for (Direction direction : Direction.values()) {
		TileEntity acceptor = tileEntity.getWorld()
			.getTileEntity(new BlockPos(tileEntity.getPos()).add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset()));
		if (!isConductor(acceptor) && isAcceptor(acceptor, direction) && canConnect(acceptor, direction)) {
		    acceptorSet.add((A) acceptor);
		    HashSet<Direction> directions = acceptorInputMap.containsKey(acceptor) ? acceptorInputMap.get(acceptor) : new HashSet<>();
		    directions.add(direction.getOpposite());
		    acceptorInputMap.put((A) acceptor, directions);
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
	    updateStatistics(wire);
	}
    }

    /**
     * Override method to define statistics per-cable
     * 
     * @param cable
     */
    public void updateStatistics(C cable) {
	// Just a default method
    }

    public void split(@Nonnull C splitPoint) {
	if (splitPoint instanceof TileEntity) {
	    removeFromNetwork(splitPoint);
	    TileEntity[] connectedTiles = new TileEntity[6];
	    boolean[] dealtWith = { false, false, false, false, false, false };
	    for (Direction direction : Direction.values()) {
		BlockPos ex = ((TileEntity) splitPoint).getPos().add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
		if (((TileEntity) splitPoint).getWorld().isBlockLoaded(ex)) {
		    TileEntity sideTile = ((TileEntity) splitPoint).getWorld().getTileEntity(ex);
		    if (sideTile != null) {
			connectedTiles[Arrays.asList(Direction.values()).indexOf(direction)] = sideTile;
		    }
		}
	    }
	    for (int countOne = 0; countOne < connectedTiles.length; countOne++) {
		TileEntity connectedBlockA = connectedTiles[countOne];
		if (connectedBlockA != null && isConductor(connectedBlockA) && !dealtWith[countOne]) {
		    AbstractNetworkFinder finder = new AbstractNetworkFinder(((TileEntity) splitPoint).getWorld(), connectedBlockA.getPos(), this,
			    ((TileEntity) splitPoint).getPos());
		    List<TileEntity> partNetwork = finder.exploreNetwork();
		    for (int countTwo = countOne + 1; countTwo < connectedTiles.length; countTwo++) {
			TileEntity connectedBlockB = connectedTiles[countTwo];
			if (isConductor(connectedBlockB) && !dealtWith[countTwo] && partNetwork.contains(connectedBlockB)) {
			    dealtWith[countTwo] = true;
			}
		    }
		    AbstractNetwork<C, T, A, P> newNetwork = createInstance();

		    for (TileEntity tile : finder.iteratedTiles) {
			if (tile != splitPoint) {
			    newNetwork.conductorSet.add((C) tile);
			}
		    }
		    newNetwork.refresh();
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

    public P emit(P transfer, ArrayList<TileEntity> ignored, boolean debug) {
	return null;
    }

    public abstract boolean isConductor(TileEntity tile);

    public abstract boolean isAcceptor(TileEntity acceptor, Direction orientation);

    public abstract boolean canConnect(TileEntity acceptor, Direction orientation);

    public abstract AbstractNetwork<C, T, A, P> createInstance();

    public abstract AbstractNetwork<C, T, A, P> createInstanceConductor(Set<C> conductors);

    public abstract AbstractNetwork<C, T, A, P> createInstance(Set<AbstractNetwork<C, T, A, P>> networks);

    public abstract T[] getConductorTypes();
}