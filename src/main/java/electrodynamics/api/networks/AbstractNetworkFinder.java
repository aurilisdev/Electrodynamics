package electrodynamics.api.networks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbstractNetworkFinder {
    public World worldObj;
    public BlockPos start;
    public AbstractNetwork<?, ?, ?, ?> net;
    public List<BlockPos> iterated = new ArrayList<>();
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
	if (net.isConductor(worldObj.getTileEntity(location))) {
	    iterated.add(location);
	}
	for (Direction direction : Direction.values()) {
	    BlockPos obj = new BlockPos(location).add(direction.getXOffset(), direction.getYOffset(),
		    direction.getZOffset());
	    if (!iterated.contains(obj) && !toIgnore.contains(obj)) {
		TileEntity tileEntity = worldObj.getTileEntity(obj);
		if (net.isConductor(tileEntity)) {
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
