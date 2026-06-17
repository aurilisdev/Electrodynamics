package electrodynamics.common.block.gastransformer;

import com.mojang.serialization.MapCodec;

import electrodynamics.common.block.gastransformer.util.BlockGenericAdvancedGasTransformer;
import electrodynamics.common.tile.pipelines.gas.gastransformer.compressor.GenericTileAdvancedCompressor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import voltaic.common.block.states.VoltaicBlockStates;

public class BlockAdvancedCompressor extends BlockGenericAdvancedGasTransformer {

    public BlockAdvancedCompressor(boolean isDecompressor) {
	super(isDecompressor ? GenericTileAdvancedCompressor.TileAdvancedDecompressor::new
		: GenericTileAdvancedCompressor.TileAdvancedCompressor::new);
	registerDefaultState(stateDefinition.any().setValue(VoltaicBlockStates.LIT, false));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
	if (state.hasProperty(VoltaicBlockStates.LIT) && state.getValue(VoltaicBlockStates.LIT)) {
	    return 15;
	}
	return super.getLightEmission(state, level, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
	return super.getStateForPlacement(context).setValue(VoltaicBlockStates.LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
	super.createBlockStateDefinition(builder);
	builder.add(VoltaicBlockStates.LIT);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
	throw new UnsupportedOperationException("Need to implement CODEC");
    }

}
