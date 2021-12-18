package electrodynamics.common.multiblock;

import java.util.Collection;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface IMultiblockNode {
	boolean hasMultiBlock();

	default boolean isValidMultiblockPlacement(BlockState state, LevelReader worldIn, BlockPos pos, Collection<Subnode> nodes) {
		for (Subnode sub : nodes) {
			BlockPos check = pos.offset(sub.pos);
			if (!worldIn.getBlockState(check).getMaterial().isReplaceable()) {
				return false;
			}
		}
		return true;
	}
}
