package electrodynamics.common.tile.machines.wiremill;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileWireMillDouble extends TileWireMill {
	public TileWireMillDouble(BlockPos worldPosition, BlockState blockState) {
		super(SubtypeMachine.wiremilldouble, 1, worldPosition, blockState);
	}
}
