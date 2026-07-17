package electrodynamics.common.network.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;

import electrodynamics.common.tile.pipelines.fluid.GenericTileFluidPipe;
import electrodynamics.common.tile.pipelines.fluid.TileFluidPipePump;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import voltaic.api.network.cable.type.IFluidPipe;
import voltaic.common.network.NetworkRegistry;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.prefab.network.AbstractNetwork;

public class FluidNetwork extends AbstractNetwork<GenericTileFluidPipe, IFluidPipe, FluidStack, FluidNetwork> {

    public HashMap<Integer, HashSet<TileFluidPipePump>> priorityPumpMap = new HashMap<>();

    public FluidNetwork(Collection<GenericTileFluidPipe> varCables) {
	conductorSet.addAll(varCables);
	NetworkRegistry.register(this);
    }

    public FluidNetwork(Set<FluidNetwork> networks) {
	for (FluidNetwork net : networks) {
	    conductorSet.addAll(net.conductorSet);
	    net.deregister();
	}
	NetworkRegistry.register(this);
    }

    @Override
    public void refreshNewNetwork() {
	priorityPumpMap.clear();
	networkMaxTransfer = 0;
	super.refreshNewNetwork();
    }

    @Override
    public FluidStack emit(FluidStack inserted, ArrayList<BlockEntity> ignored, boolean debug) {

	FluidStack transfer = new FluidStack(inserted.getFluid(),
		Math.min((int) networkMaxTransfer, inserted.getAmount()));

	if (transfer.getAmount() <= 0) {
	    return FluidStack.EMPTY;
	}

	FluidStack initial = transfer.copy();
	FluidStack taken = new FluidStack(transfer.getFluid(), 0);

	Pair<FluidStack, Set<TileFluidPipePump>> priorityFilled = emitToPumps(transfer, ignored);

	initial.shrink(priorityFilled.getFirst().getAmount());
	taken.grow(priorityFilled.getFirst().getAmount());

	if (initial.isEmpty()) {

	    return taken;

	}

	HashSet<BlockEntity> availableAcceptors = Sets.newHashSet();

	availableAcceptors.addAll(acceptorSet);

	availableAcceptors.removeAll(ignored);

	if (priorityFilled.getSecond().size() > 0) {
	    availableAcceptors.removeAll(priorityFilled.getSecond());
	}

	if (availableAcceptors.isEmpty()) {
	    return taken;
	}

	// This algorithm is not perfect, but it helps deal with tiles that do not
	// accept the full amount allotted to them

	FluidStack perTile, prePerTile, perConnection, prePerConnection;

	int size = availableAcceptors.size();

	int connectionsSize, amtTaken, takenAmt;

	HashSet<Direction> connections;

	for (BlockEntity tile : availableAcceptors) {

	    if (tile == null || tile.isRemoved()) {
		acceptorInputMap.remove(tile);
		acceptorSet.remove(tile);
		size--;
		continue;
	    }

	    if (initial.isEmpty() || size <= 0) {
		break;
	    }

	    connections = acceptorInputMap.getOrDefault(tile, new HashSet<>());

	    connectionsSize = connections.size();

	    if (connectionsSize <= 0) {
		size--;
		continue;
	    }

	    perTile = new FluidStack(initial.getFluid(), Math.max(1, initial.getAmount() / size));
	    prePerTile = perTile.copy();

	    for (Direction dir : connections) {

		if (perTile.isEmpty() || connectionsSize <= 0) {
		    break;
		}

		perConnection = new FluidStack(initial.getFluid(), Math.max(1, perTile.getAmount() / connectionsSize));
		prePerConnection = perConnection.copy();

		amtTaken = FluidUtilities.receiveFluid(tile, dir, perConnection, false);

		perConnection.shrink(amtTaken);

		perTile.shrink(prePerConnection.getAmount() - perConnection.getAmount());

		connectionsSize--;
	    }

	    takenAmt = prePerTile.getAmount() - perTile.getAmount();

	    initial.shrink(takenAmt);

	    taken.grow(takenAmt);

	    size--;
	}

	return taken;

    }

    private Pair<FluidStack, Set<TileFluidPipePump>> emitToPumps(FluidStack transfer, ArrayList<BlockEntity> ignored) {

	FluidStack taken = new FluidStack(transfer.getFluid(), 0);

	HashSet<TileFluidPipePump> acceptedPumps = new HashSet<>();

	if (priorityPumpMap.isEmpty()) {
	    return Pair.of(taken, acceptedPumps);
	}

	Pair<FluidStack, Set<TileFluidPipePump>> accepted;

	Set<TileFluidPipePump> prioritySet;

	FluidStack copy = transfer.copy();

	for (int i = 9; i >= 0; i--) {

	    if (copy.isEmpty()) {
		return Pair.of(taken, acceptedPumps);
	    }

	    prioritySet = priorityPumpMap.getOrDefault(i, new HashSet<>());

	    if (prioritySet.isEmpty()) {
		continue;
	    }

	    accepted = emitToPumpSet(copy, prioritySet, ignored);

	    acceptedPumps.addAll(accepted.getSecond());

	    taken.grow(accepted.getFirst().getAmount());

	    copy.shrink(accepted.getFirst().getAmount());

	}

	return Pair.of(taken, acceptedPumps);

    }

    private Pair<FluidStack, Set<TileFluidPipePump>> emitToPumpSet(FluidStack transfer,
	    Set<TileFluidPipePump> recievingTiles, ArrayList<BlockEntity> ignored) {

	FluidStack initial = transfer.copy();
	FluidStack taken = new FluidStack(transfer.getFluid(), 0);

	FluidStack perTile, prePerTile, perConnection, prePerConnection;

	HashSet<TileFluidPipePump> handledPumps = new HashSet<>();

	int size = recievingTiles.size();

	int connectionsSize, amtTaken, takenAmt;

	HashSet<Direction> connections;

	for (TileFluidPipePump tile : recievingTiles) {
	    if (tile == null || tile.isRemoved()) {
		acceptorInputMap.remove(tile);
		acceptorSet.remove(tile);
		size--;
		continue;
	    }
	    if (!tile.isPowered() || ignored.contains(tile)) {
		size--;
		continue;
	    }
            if (initial.isEmpty() || size <= 0) {
                break;
            }
	    connections = acceptorInputMap.getOrDefault(tile, new HashSet<>());

	    connectionsSize = connections.size();

            if (connectionsSize <= 0) {
                handledPumps.add(tile);
                size--;
                continue;
            }

            perTile = new FluidStack(initial.getFluid(), Math.max(1, initial.getAmount() / size));
            prePerTile = perTile.copy();
	    for (Direction dir : connections) {

                if (perTile.isEmpty() || connectionsSize <= 0) {
                    break;
                }
		perConnection = new FluidStack(initial.getFluid(), perTile.getAmount() / connectionsSize);
		prePerConnection = perConnection.copy();

		amtTaken = FluidUtilities.receiveFluid(tile, dir, perConnection, false);

		perConnection.shrink(amtTaken);

		perTile.shrink(prePerConnection.getAmount() - perConnection.getAmount());

		connectionsSize--;
	    }

	    takenAmt = prePerTile.getAmount() - perTile.getAmount();

	    initial.shrink(takenAmt);

	    taken.grow(takenAmt);

	    handledPumps.add(tile);

	    size--;
	}

	return Pair.of(taken, handledPumps);

    }

    @Override
    public void updateRecieverStatistics(BlockEntity reciever, Direction dir) {

	if (reciever instanceof TileFluidPipePump pump) {
	    int priority = pump.priority.getValue();
	    HashSet<TileFluidPipePump> set = priorityPumpMap.getOrDefault(priority, new HashSet<>());
	    set.add(pump);
	    priorityPumpMap.put(priority, set);

	}

    }

    public void updateFluidPipePumpStats(TileFluidPipePump changedPump, int newPriority, int prevPriority) {
	HashSet<TileFluidPipePump> oldSet = priorityPumpMap.getOrDefault(prevPriority, new HashSet<>());
	oldSet.remove(changedPump);
	priorityPumpMap.put(prevPriority, oldSet);

	HashSet<TileFluidPipePump> newSet = priorityPumpMap.getOrDefault(newPriority, new HashSet<>());
	newSet.add(changedPump);
	priorityPumpMap.put(newPriority, newSet);
    }

    @Override
    public void updateConductorStatistics(GenericTileFluidPipe cable, boolean remove) {
	super.updateConductorStatistics(cable, remove);

	if (!remove) {

	    if (networkMaxTransfer == 0 || cable.getCableType().getMaxTransfer() < networkMaxTransfer) {
		networkMaxTransfer = cable.getCableType().getMaxTransfer();
	    }

	}
    }

    @Override
    public void resetConductorStatistics() {
	networkMaxTransfer = 0;
	super.resetConductorStatistics();
    }

    @Override
    public void resetReceiverStatistics() {
	priorityPumpMap.clear();
	super.resetReceiverStatistics();
    }

    /*
     *
     * There is no need for this as we have no concept of fluid pressure nor
     * temperature
     *
     * private boolean checkForOverload(int attemptSend) { if (attemptSend >=
     * networkMaxTransfer) { HashSet<SubtypeFluidPipe> checkList = new HashSet<>();
     * for (SubtypeFluidPipe type : SubtypeFluidPipe.values()) { if
     * (type.maxTransfer <= attemptSend) { checkList.add(type); } } for
     * (SubtypeFluidPipe index : checkList) { for (IFluidPipe conductor :
     * conductorTypeMap.get(index)) { conductor.destroyViolently(); } } return true;
     * } return false; }
     */

    @Override
    public boolean isConductor(BlockEntity tile, GenericTileFluidPipe requsterCable) {
	return tile instanceof GenericTileFluidPipe;
    }

    @Override
    public FluidNetwork createInstanceConductor(Set<GenericTileFluidPipe> conductors) {
	return new FluidNetwork(conductors);
    }
}
