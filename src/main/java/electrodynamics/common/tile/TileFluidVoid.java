package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileFluidVoid extends GenericTile {

	public TileFluidVoid(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_FLUIDVOID.get(), worldPos, blockState);
	}

}
