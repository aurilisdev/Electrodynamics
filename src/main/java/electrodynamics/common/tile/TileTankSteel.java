package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.generic.GenericTileTank;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileTankSteel extends GenericTileTank {

	public static final int CAPACITY = 8000;

	public TileTankSteel(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_TANKSTEEL.get(), CAPACITY, SubtypeMachine.tanksteel, pos, state);
	}

}
