package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.generic.GenericTileCharger;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileChargerHV extends GenericTileCharger {

	public TileChargerHV(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CHARGERHV.get(), 4, SubtypeMachine.chargerhv, worldPosition, blockState);
	}

}
