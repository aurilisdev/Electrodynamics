package electrodynamics.api.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbstractNetworkFinder {
    public World worldObj;
    public BlockPos start;
    public AbstractNetwork<?, ?, ?, ?> net;
    public List<TileEntity> iteratedTiles = new ArrayList<>();
    public List<BlockPos> toIgnore = new ArrayList<>();

    public AbstractNetworkFinder(World world, BlockPos location, AbstractNetwork<?, ?, ?, ?> net, BlockPos... ignore) {
	worldObj = world;
	start = location;
	this.net = net;
	if (ignore.length > 0) {
	    toIgnore = Arrays.asList(ignore);
	}
    }

    public void loopAll(BlockPos location) {
	TileEntity curr = worldObj.getTileEntity(location);
	if (net.isConductor(curr)) {
	    iteratedTiles.add(curr);
	}
	for (Direction direction : Direction.values()) {
	    BlockPos obj = new BlockPos(location).add(direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
	    if (!(toIgnore.size() == 1 ? toIgnore.get(0) == obj : toIgnore.contains(obj))) {
		if (worldObj.isBlockLoaded(obj)) {
		    TileEntity tileEntity = worldObj.getTileEntity(obj);
		    if (!iteratedTiles.contains(tileEntity) && net.isConductor(tileEntity)) {
			loopAll((IAbstractConductor) tileEntity);
		    }
		}
	    }
	}

    }

    public void loopAll(IAbstractConductor conductor) {
	iteratedTiles.add((TileEntity) conductor);
	for (TileEntity connections : conductor.getAdjacentConnections()) {
	    if (connections != null) {
		BlockPos pos = connections.getPos();
		if (!iteratedTiles.contains(connections) && !(toIgnore.size() == 1 ? toIgnore.get(0) == pos : toIgnore.contains(pos))) {
		    if (net.isConductor(connections)) {
			loopAll((IAbstractConductor) connections);
		    }
		}
	    }
	}
    }

    public List<TileEntity> exploreNetwork() {
	loopAll(start);
	return iteratedTiles;
    }
}
