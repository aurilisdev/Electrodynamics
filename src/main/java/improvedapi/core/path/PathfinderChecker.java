package improvedapi.core.path;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import improvedapi.core.capability.CapabilityConductor;
import improvedapi.core.tile.IConnectionProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Check if a conductor connects with another.
 * 
 */
public class PathfinderChecker extends Pathfinder {
    public PathfinderChecker(final World world, final IConnectionProvider targetConnector, final IConnectionProvider... ignoreConnector) {
	super(new IPathCallBack() {
	    @Override
	    public Set<BlockPos> getConnectedNodes(Pathfinder finder, BlockPos currentNode) {
		Set<BlockPos> neighbors = new HashSet<>();

		for (Direction direction : Direction.values()) {
		    BlockPos position = currentNode.offset(direction);
		    TileEntity connectedBlock = world.getTileEntity(position);
		    if (connectedBlock != null) {
			connectedBlock.getCapability(CapabilityConductor.INSTANCE).ifPresent(capability -> {
			    if (!Arrays.asList(ignoreConnector).contains(capability) && capability.canConnect(direction.getOpposite())) {
				neighbors.add(position);
			    }
			});
		    }
		}
		return neighbors;
	    }

	    @Override
	    public boolean onSearch(Pathfinder finder, BlockPos node) {
		if (world.getTileEntity(node) == targetConnector) {
		    finder.results.add(node);
		    return true;
		}

		return false;
	    }
	});
    }
}
