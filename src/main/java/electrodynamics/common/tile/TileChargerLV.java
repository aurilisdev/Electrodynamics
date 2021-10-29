package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericCharger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerLV extends TileGenericCharger {

    public TileChargerLV(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_CHARGERLV.get(), 1, "lv", worldPosition, blockState);
    }

}
