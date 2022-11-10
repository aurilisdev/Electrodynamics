package electrodynamics.common.tile;

import electrodynamics.common.tile.generic.GenericTileCharger;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerLV extends GenericTileCharger {

	public TileChargerLV(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CHARGERLV.get(), 1, "lv", worldPosition, blockState);
	}

}
