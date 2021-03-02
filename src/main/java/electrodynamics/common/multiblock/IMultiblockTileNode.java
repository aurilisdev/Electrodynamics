package electrodynamics.common.multiblock;

import java.util.HashSet;
import java.util.Iterator;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.TileMultiSubnode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblockTileNode {

    default void onNodeReplaced(World world, BlockPos pos, boolean update) {
	Iterator<Subnode> it = getSubNodes().iterator();
	while (it.hasNext()) {
	    BlockPos inPos = it.next().pos;
	    BlockPos offset = pos.add(inPos);
	    BlockState inState = world.getBlockState(offset);
	    Block inBlock = inState.getBlock();
	    if (update) {
		if (inBlock == Blocks.AIR) {
		    world.setBlockState(offset, DeferredRegisters.multi.getDefaultState());
		}
		TileMultiSubnode subnode = (TileMultiSubnode) world.getTileEntity(offset);
		if (subnode != null) {
		    subnode.nodePos = new BlockPos(pos);
		}
	    } else {
		if (inBlock instanceof IMultiblockSubnode) {
		    TileMultiSubnode subnode = (TileMultiSubnode) world.getTileEntity(offset);
		    if (subnode != null && subnode.nodePos.equals(pos)) {
			world.setBlockState(offset, Blocks.AIR.getDefaultState());
		    }
		}
	    }
	}
    }

    default void onNodePlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	onNodeReplaced(world, pos, true);
    }

    HashSet<Subnode> getSubNodes();
}
