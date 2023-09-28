package electrodynamics.common.block;

import electrodynamics.api.multiblock.child.IMultiblockChildBlock;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockMultiSubnode extends BaseEntityBlock implements IMultiblockChildBlock {

	public BlockMultiSubnode() {
		super(Properties.copy(Blocks.GLASS).strength(3.5F).sound(SoundType.METAL).isRedstoneConductor((a, b, c) -> false).noOcclusion());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return worldIn.getBlockEntity(pos) instanceof TileMultiSubnode subnode ? subnode.getShape() : Shapes.block();
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1.0f;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
		if (level.getBlockEntity(pos) instanceof TileMultiSubnode subnode) {
			return new ItemStack(level.getBlockState(subnode.parentPos.get()).getBlock());
		}
		return super.getCloneItemStack(state, target, level, pos, player);
	}

	/*
	 * @Override public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) { BlockEntity tile = worldIn.getBlockEntity(pos); if (tile instanceof TileMultiSubnode subnode && subnode.nodePos.get() != null) { worldIn.getBlockState(subnode.nodePos.get()).getBlock().use(worldIn.getBlockState(subnode.nodePos.get()), worldIn, subnode.nodePos.get(), player, handIn, hit); } return InteractionResult.SUCCESS; }
	 */

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	/*
	 * @Override public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) { BlockEntity tile = blockAccess.getBlockEntity(pos); if (tile instanceof TileMultiSubnode subnode && subnode.nodePos.get() != null) { return blockAccess.getBlockState(subnode.nodePos.get()).getBlock().getDirectSignal(blockAccess.getBlockState(subnode.nodePos.get()), blockAccess, subnode.nodePos.get(), side); } return super.getDirectSignal(blockState, blockAccess, pos, side); }
	 * 
	 * @Override public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) { BlockEntity tile = blockAccess.getBlockEntity(pos); if (tile instanceof TileMultiSubnode subnode && subnode.nodePos.get() != null) { return blockAccess.getBlockState(subnode.nodePos.get()).getBlock().getSignal(blockAccess.getBlockState(subnode.nodePos.get()), blockAccess, subnode.nodePos.get(), side); } return super.getSignal(blockState, blockAccess, pos, side); }
	 */

	@Override
	public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		if (level.getBlockEntity(pos) instanceof GenericTile generic) {
			return generic.getDirectSignal(direction);
		}
		return super.getDirectSignal(state, level, pos, direction);
	}

	@Override
	public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		if (level.getBlockEntity(pos) instanceof GenericTile generic) {
			return generic.getSignal(direction);
		}
		return super.getSignal(state, level, pos, direction);
	}

	/*
	 * @Override public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) { BlockEntity tile = worldIn.getBlockEntity(pos); if (tile instanceof TileMultiSubnode subnode && subnode.nodePos.get() != null) { worldIn.destroyBlock(subnode.nodePos.get(), true); } super.onRemove(state, worldIn, pos, newState, isMoving); }
	 */

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileMultiSubnode(pos, state);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (newState.isAir() && level.getBlockEntity(pos) instanceof GenericTile generic) {
			generic.onBlockDestroyed();
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, level, pos, neighbor);
		if (level.getBlockEntity(pos) instanceof GenericTile generic) {
			generic.onNeightborChanged(neighbor);
		}
	}

	@Override
	public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(newState, level, pos, oldState, isMoving);
		if (level.getBlockEntity(pos) instanceof GenericTile generic) {
			generic.onPlace(oldState, isMoving);
		}
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
		super.neighborChanged(state, level, pos, block, neighbor, isMoving);
		if (level.getBlockEntity(pos) instanceof GenericTile generic) {
			generic.onNeightborChanged(neighbor);
		}

	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof GenericTile generic) {
			return generic.getComparatorSignal();
		}
		return super.getAnalogOutputSignal(state, level, pos);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.getBlockEntity(pos) instanceof GenericTile generic) {
			return generic.use(player, handIn, hit);
		}

		return InteractionResult.FAIL;
	}
}
