package electrodynamics.common.tile.pipelines.fluid;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.network.cable.type.IFluidPipe;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;

public class TileFluidPipe extends GenericTileFluidPipe {
	public SingleProperty<Double> transmit = property(new SingleProperty<>(PropertyTypes.DOUBLE, "transmit", 0.0));

	public TileFluidPipe(BlockPos pos, BlockState state) {
		super(ElectrodynamicsTiles.TILE_PIPE.get(), pos, state);
	}

	public IFluidPipe pipe = null;

	@Override
	public IFluidPipe getCableType() {
		if (pipe == null) {
			pipe = ((BlockFluidPipe) getBlockState().getBlock()).pipe;
		}
		return pipe;
	}

}
