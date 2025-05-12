package electrodynamics.common.tile.pipelines.fluid;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.registers.ElectrodynamicsTiles;
import voltaic.api.network.cable.type.IFluidPipe;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;

public class TileFluidPipe extends GenericTileFluidPipe {
	public SingleProperty<Double> transmit = property(new SingleProperty<>(PropertyTypes.DOUBLE, "transmit", 0.0));

	public TileFluidPipe() {
		super(ElectrodynamicsTiles.TILE_PIPE.get());
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
