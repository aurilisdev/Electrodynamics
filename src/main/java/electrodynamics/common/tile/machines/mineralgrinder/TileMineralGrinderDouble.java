package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralGrinderDouble extends TileMineralGrinder {
	public TileMineralGrinderDouble(BlockPos pos, BlockState state) {
		super(SubtypeMachine.mineralgrinderdouble, 1, pos, state);
	}
}
