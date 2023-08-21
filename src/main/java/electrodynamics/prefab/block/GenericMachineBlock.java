package electrodynamics.prefab.block;

import java.util.HashMap;

import javax.annotation.Nullable;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GenericMachineBlock extends GenericEntityBlockWaterloggable {

	protected BlockEntitySupplier<BlockEntity> blockEntitySupplier;

	public static HashMap<BlockPos, LivingEntity> IPLAYERSTORABLE_MAP = new HashMap<>();

	public GenericMachineBlock(BlockEntitySupplier<BlockEntity> blockEntitySupplier) {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops());
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
		this.blockEntitySupplier = blockEntitySupplier;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		BlockEntity entity = worldIn.getBlockEntity(pos);
		if (entity instanceof GenericTile tile) {
			if (tile.getComponent(ComponentType.Direction) instanceof ComponentDirection direc) {
				return VoxelShapes.getShape(worldIn.getBlockState(pos).getBlock(), direc.getDirection());
			}
		}
		return super.getShape(state, worldIn, pos, context);
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
		if (isIPlayerStorable()) {
			IPLAYERSTORABLE_MAP.put(pPos, pPlacer);
		}
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
	}

	@Override
	public final BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return blockEntitySupplier.create(pos, state);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	public boolean isIPlayerStorable() {
		return false;
	}

}
