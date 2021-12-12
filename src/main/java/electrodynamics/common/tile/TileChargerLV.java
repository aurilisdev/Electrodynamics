package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.GenericTileCharger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerLV extends GenericTileCharger {

    public TileChargerLV(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_CHARGERLV.get(), 1, "lv", worldPosition, blockState);
    }

}
