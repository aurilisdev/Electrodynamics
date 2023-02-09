package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.generic.GenericTileCharger;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerMV extends GenericTileCharger {

	public TileChargerMV(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CHARGERMV.get(), 2, SubtypeMachine.chargermv, worldPosition, blockState);
	}

}
