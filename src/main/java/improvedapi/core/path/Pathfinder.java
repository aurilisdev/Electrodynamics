package improvedapi.core.path;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.math.BlockPos;

/**
 * A class that allows flexible pathfinding for different positions. Compared to
 * AStar pathfinding, this version is faster but does not calculated the most
 * optimal path.
 * 
 */
public class Pathfinder {
    /**
     * A pathfinding call back interface used to call back on paths.
     */
    public IPathCallBack callBackCheck;

    /**
     * A list of nodes that the pathfinder already went through.
     */
    public Set<BlockPos> closedSet;

    /**
     * The resulted path found by the pathfinder. Could be null if no path was
     * found.
     */
    public Set<BlockPos> results;

    public Pathfinder(IPathCallBack callBack) {
	this.callBackCheck = callBack;
	this.reset();
    }

    /**
     * @return True on success finding, false on failure.
     */
    public boolean findNodes(BlockPos currentNode) {
	this.closedSet.add(currentNode);

	if (this.callBackCheck.onSearch(this, currentNode)) {
	    return false;
	}

	for (BlockPos node : this.callBackCheck.getConnectedNodes(this, currentNode)) {
	    if (!this.closedSet.contains(node) && this.findNodes(node)) {
		return true;
	    }
	}

	return false;
    }

    /**
     * Called to execute the pathfinding operation.
     */
    public Pathfinder init(BlockPos startNode) {
	this.findNodes(startNode);
	return this;
    }

    public Pathfinder reset() {
	this.closedSet = new HashSet<>();
	this.results = new HashSet<>();
	return this;
    }
}
