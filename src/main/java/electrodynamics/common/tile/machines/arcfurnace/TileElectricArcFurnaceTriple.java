package electrodynamics.common.tile.machines.arcfurnace;

import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectricArcFurnaceTriple extends TileElectricArcFurnace {
	public TileElectricArcFurnaceTriple(BlockPos worldPosition, BlockState blockState) {
		super(SubtypeMachine.electricarcfurnacetriple, 2, worldPosition, blockState);
	}
}
