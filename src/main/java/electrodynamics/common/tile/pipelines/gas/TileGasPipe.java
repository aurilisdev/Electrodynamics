package electrodynamics.common.tile.pipelines.gas;

import javax.annotation.Nullable;

import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.network.cable.type.IGasPipe;

public class TileGasPipe extends GenericTileGasPipe {

    private @Nullable IGasPipe pipe = null;

    public TileGasPipe(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_GAS_PIPE.get(), worldPos, blockState);
    }

    @Override
    public IGasPipe getCableType() {
	IGasPipe pipe = this.pipe;
	if (pipe == null) {
	    pipe = this.pipe = ((BlockGasPipe) getBlockState().getBlock()).pipe;
	}
	return pipe;
    }

}
