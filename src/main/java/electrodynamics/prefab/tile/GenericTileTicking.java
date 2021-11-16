package electrodynamics.prefab.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GenericTileTicking extends GenericTile {

    protected GenericTileTicking(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPosition, BlockState blockState) {
	super(tileEntityTypeIn, worldPosition, blockState);
    }
}
