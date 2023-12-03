package electrodynamics.common.tile.machines.wiremill;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileWireMillTriple extends TileWireMill {
	public TileWireMillTriple(BlockPos worldPosition, BlockState blockState) {
		super(SubtypeMachine.wiremilltriple, 2, worldPosition, blockState);
	}
}