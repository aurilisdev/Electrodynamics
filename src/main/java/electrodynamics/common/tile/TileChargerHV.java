package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericCharger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerHV extends TileGenericCharger {

    public TileChargerHV(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_CHARGERHV.get(), 4, "hv", worldPosition, blockState);
    }

}
