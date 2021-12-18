package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileCreativeFluidSource extends GenericTile {

	public TileCreativeFluidSource(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_CREATIVEFLUIDSOURCE.get(), worldPos, blockState);
	}

}
