package electrodynamics.common.electricity.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.electricity.ElectricityUtilities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectricNetworkFinder {
	public World worldObj;
	public BlockPos start;
	public List<BlockPos> iterated = new ArrayList<>();
	public List<BlockPos> toIgnore = new ArrayList<>();

	public ElectricNetworkFinder(World world, BlockPos location, BlockPos... ignore) {
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
