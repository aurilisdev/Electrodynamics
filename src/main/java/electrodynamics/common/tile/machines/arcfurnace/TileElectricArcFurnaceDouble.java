package electrodynamics.common.tile.machines.arcfurnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectricArcFurnaceDouble extends TileElectricArcFurnace {
	public TileElectricArcFurnaceDouble(BlockPos worldPosition, BlockState blockState) {
		super(SubtypeMachine.electricarcfurnacedouble, 1, worldPosition, blockState);
	}
}
