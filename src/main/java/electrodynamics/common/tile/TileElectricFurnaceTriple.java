package electrodynamics.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectricFurnaceTriple extends TileElectricFurnace {
    public TileElectricFurnaceTriple(BlockPos worldPosition, BlockState blockState) {
	super(2, worldPosition, blockState);
    }
}
