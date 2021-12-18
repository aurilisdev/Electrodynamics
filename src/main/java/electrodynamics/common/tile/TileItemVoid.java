package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileItemVoid extends GenericTile {

	public TileItemVoid(BlockPos worldPos, BlockState blockState) {
		super(null, worldPos, blockState);
	}

}
