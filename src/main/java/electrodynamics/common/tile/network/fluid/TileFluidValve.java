package electrodynamics.common.tile.network.fluid;

import electrodynamics.common.tile.network.GenericTileValve;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileFluidValve extends GenericTileValve {

	public TileFluidValve(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_FLUIDVALVE.get(), pos, state);
	}

}
