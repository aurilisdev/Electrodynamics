package electrodynamics.common.tile.pipelines.fluid;

import javax.annotation.Nullable;

import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.network.cable.type.IFluidPipe;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;

public class TileFluidPipe extends GenericTileFluidPipe {
    private @Nullable IFluidPipe pipe = null;

    public SingleProperty<Double> transmit = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "transmit", 0.0));

    public TileFluidPipe(BlockPos pos, BlockState state) {
	super(ElectrodynamicsTiles.TILE_PIPE.get(), pos, state);
    }

    @Override
    public IFluidPipe getCableType() {
	IFluidPipe pipe = this.pipe;
	if (pipe == null) {
	    pipe = this.pipe = ((BlockFluidPipe) getBlockState().getBlock()).pipe;
	}
	return pipe;
    }

}
