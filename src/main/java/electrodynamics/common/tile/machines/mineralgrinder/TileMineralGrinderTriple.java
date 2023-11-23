package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralGrinderTriple extends TileMineralGrinder {
	public TileMineralGrinderTriple(BlockPos pos, BlockState state) {
		super(SubtypeMachine.mineralgrindertriple, 2, pos, state);
	}
}
