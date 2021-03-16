package electrodynamics.common.multiblock;

import java.util.Collection;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public interface IMultiblockNode {
    boolean hasMultiBlock();

    default boolean isValidMultiblockPlacement(BlockState state, IWorldReader worldIn, BlockPos pos, Collection<Subnode> nodes) {
	for (Subnode sub : nodes) {
	    BlockPos check = pos.add(sub.pos);
	    if (!worldIn.getBlockState(check).getMaterial().isReplaceable()) {
		return false;
	    }
	}
	return true;
    }
}
