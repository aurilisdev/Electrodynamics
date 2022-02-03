package electrodynamics.common.block;

import java.util.List;
import java.util.Random;

import org.apache.commons.compress.utils.Lists;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.tile.TileFrame;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

public class BlockFrame extends GenericMachineBlock {
	
	//The Hoe is the removal tool to help prevent accidentally breaking the frame
	
	public BlockFrame() {
		super(TileFrame::new);
		registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY, Boolean.FALSE));
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
		return Lists.newArrayList();
	}
	
	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY);
	}
	
	@Override
	public void onRotate(ItemStack stack, BlockPos pos, Player player) {}
	
	@Override
	public void onPickup(ItemStack stack, BlockPos pos, Player player) {}
	
	@Override
	public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
		return state;
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state;
	}
	
	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
		pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
	}

}
