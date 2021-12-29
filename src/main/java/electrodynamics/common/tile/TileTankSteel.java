package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.GenericTileTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileTankSteel extends GenericTileTank {

	public static final int CAPACITY = 8000;
	private static String name = "steel";

	public TileTankSteel(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_TANKSTEEL.get(), CAPACITY, name, pos, state);
	}

}
