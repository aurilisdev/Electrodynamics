package electrodynamics.common.tile.machines.furnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectricFurnaceTriple extends TileElectricFurnace {
	public TileElectricFurnaceTriple(BlockPos worldPosition, BlockState blockState) {
		super(SubtypeMachine.electricfurnacetriple, 2, worldPosition, blockState);
	}
}
