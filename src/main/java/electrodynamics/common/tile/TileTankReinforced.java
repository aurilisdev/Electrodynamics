package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.generic.GenericTileTank;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileTankReinforced extends GenericTileTank {

	public static final int CAPACITY = 32000;

	public TileTankReinforced(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_TANKREINFORCED.get(), CAPACITY, SubtypeMachine.tankreinforced, pos, state);
	}
}
