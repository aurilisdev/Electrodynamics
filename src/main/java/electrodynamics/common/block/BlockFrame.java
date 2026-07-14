package electrodynamics.common.block;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.tile.machines.quarry.TileFrame;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class BlockFrame extends Block {

	// The Hoe is the removal tool to help prevent accidentally breaking the frame
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final VoxelShape FRAME = Block.box(0, 0, 0, 16, 4, 16);
	private static final VoxelShape FRAME_CORNER = VoxelShapes.or(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D), Block.box(0, 0, 0, 16, 4, 16));

	private final int type;

	public BlockFrame(int type) {
		super(Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops().randomTicks().harvestLevel(1).harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY, false).setValue(BlockStateProperties.WATERLOGGED, false).setValue(FACING, Direction.NORTH));
		this.type = type;
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		if (type == 0) {
			return FRAME;
			// room for future expansion
		} else if (type == 1) {
			return FRAME_CORNER;
		}
		return super.getShape(pState, pLevel, pPos, pContext);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY, false);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.emptyList();
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY);
		builder.add(BlockStateProperties.WATERLOGGED);
		builder.add(FACING);
	}

	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
		return state;
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state;
	}

	@Override
	public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		if (pState.hasProperty(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY) && pState.getValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY)) {
			pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
		}

	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileFrame();
	}

	@Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (newState.isAir(level, pos) && !state.getValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY) && !level.isClientSide) {
			TileEntity entity = level.getBlockEntity(pos);
			if (entity instanceof TileFrame) {
				((TileFrame) entity).purposefullyDestroyed();
			}
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	public static void writeToNbt(CompoundNBT tag, String key, BlockState state) {
		CompoundNBT data = new CompoundNBT();

		data.putInt("facing", state.getValue(FACING).ordinal());
		// data.putString("facing", state.getValue(FACING).name());
		data.putBoolean("waterlogged", state.getValue(BlockStateProperties.WATERLOGGED));
		data.putBoolean("decay", state.getValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY));
		tag.put(key, data);
	}

	public static BlockState readFromNbt(CompoundNBT tag) {
		BlockState state = ElectrodynamicsBlocks.BLOCK_FRAME.get().defaultBlockState();
		int dir = 0;
		if (tag.contains("facing", 8)) {
			String name = tag.getString("facing");
			dir = 5;
			switch (name) {
			case "DOWN": 
				dir = 0;
				break;
			case "UP": 
				dir = 1;
				break;
			case "NORTH": 
				dir = 2;
				break;
			case "SOUTH": 
				dir = 3;
				break;
			case "WEST": 
				dir = 4;
				break;
			default:
				break;
			}
		} else {
			dir = tag.getInt("facing");
		}
		state.setValue(FACING, Direction.values()[dir]);
		state.setValue(BlockStateProperties.WATERLOGGED, tag.getBoolean("waterlogged"));
		state.setValue(ElectrodynamicsBlockStates.QUARRY_FRAME_DECAY, tag.getBoolean("decay"));
		return state;
	}

}
