package electrodynamics.common.tile.machines.furnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectricFurnaceDouble extends TileElectricFurnace {
	public TileElectricFurnaceDouble(BlockPos worldPosition, BlockState blockState) {
		super(SubtypeMachine.electricfurnacedouble, 1, worldPosition, blockState);
	}
}
