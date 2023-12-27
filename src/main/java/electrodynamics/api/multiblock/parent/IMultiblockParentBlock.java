package electrodynamics.api.multiblock.parent;

import electrodynamics.api.multiblock.Subnode;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public interface IMultiblockParentBlock {
	
	boolean hasMultiBlock();

	default boolean isValidMultiblockPlacement(BlockState state, IWorldReader worldIn, BlockPos pos, Subnode[] nodes) {
		for (Subnode sub : nodes) {
			BlockPos check = pos.offset(sub.pos);
			if (!worldIn.getBlockState(check).getMaterial().isReplaceable()) {
				return false;
			}
		}
		return true;
	}
}
