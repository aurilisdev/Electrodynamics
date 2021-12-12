package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.GenericTileCharger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerMV extends GenericTileCharger {

    public TileChargerMV(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_CHARGERMV.get(), 2, "mv", worldPosition, blockState);
    }

}
