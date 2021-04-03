package improvedapi.core.path;

import java.util.Set;

import net.minecraft.util.math.BlockPos;

public interface IPathCallBack {
    /**
     * @param finder      - The Pathfinder object.
     * @param currentNode - The node being iterated through.
     * @return A set of nodes connected to the currentNode. Essentially one should
     *         return a set of neighboring nodes.
     */
    public Set<BlockPos> getConnectedNodes(Pathfinder finder, BlockPos currentNode);

    /**
     * Called when looping through nodes.
     * 
     * @param finder - The Pathfinder.
     * @param node   - The node being searched.
     * @return True to stop the path finding operation.
     */
    public boolean onSearch(Pathfinder finder, BlockPos node);
}