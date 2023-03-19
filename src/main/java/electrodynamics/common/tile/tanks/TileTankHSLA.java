package electrodynamics.common.tile.tanks;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileTankHSLA extends GenericTileTank {

	public static final int CAPACITY = 128000;

	public TileTankHSLA(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_TANKHSLA.get(), CAPACITY, SubtypeMachine.tankhsla, pos, state);
	}

}
