package electrodynamics.common.block.gastransformer.compressor;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.gastransformer.BlockGenericGasTransformer;
import electrodynamics.common.tile.pipelines.gas.gastransformer.compressor.TileCompressor;
import electrodynamics.common.tile.pipelines.gas.gastransformer.compressor.TileDecompressor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

public class BlockCompressor extends BlockGenericGasTransformer {

	public BlockCompressor(boolean isDecompressor) {
		super(isDecompressor ? TileDecompressor::new : TileCompressor::new);
		registerDefaultState(stateDefinition.any().setValue(BlockMachine.ON, false));
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		if (state.hasProperty(BlockMachine.ON) && state.getValue(BlockMachine.ON)) {
			return 15;
		}
		return super.getLightEmission(state, level, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(BlockMachine.ON, false);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockMachine.ON);
	}

}
