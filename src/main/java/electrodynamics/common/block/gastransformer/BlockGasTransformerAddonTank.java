package electrodynamics.common.block.gastransformer;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates.AddonTankNeighborType;
import electrodynamics.common.tile.pipelines.gas.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockGasTransformerAddonTank extends GenericMachineBlock {

	public static final VoxelShape SHAPE = Shapes.or(Block.box(4, 0, 2, 12, 16, 14), Block.box(2, 0, 4, 4, 16, 12), Block.box(12, 0, 4, 14, 16, 12));

	public BlockGasTransformerAddonTank() {
		super(TileGasTransformerAddonTank::new);
		registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS, AddonTankNeighborType.NONE));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return getStateFromNeighbors(context.getLevel(), context.getClickedPos(), state);
	}

	public BlockState getStateFromNeighbors(Level world, BlockPos pos, BlockState baseState) {
		if (!baseState.hasProperty(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS)) {
			return baseState;
		}
		BlockState above = world.getBlockState(pos.above());
		BlockState below = world.getBlockState(pos.below());
		boolean isTankBelow = below.is(ElectrodynamicsBlocks.blockGasTransformerSide) || below.is(ElectrodynamicsBlocks.blockGasTransformerAddonTank);
		boolean isTankAbove = above.is(ElectrodynamicsBlocks.blockGasTransformerAddonTank);
		AddonTankNeighborType type;
		if (isTankAbove && isTankBelow) {
			type = AddonTankNeighborType.BOTTOMANDTOPTANK;
		} else if (isTankAbove) {
			type = AddonTankNeighborType.TOPTANK;
		} else if (isTankBelow) {
			type = AddonTankNeighborType.BOTTOMTANK;
		} else {
			type = AddonTankNeighborType.NONE;
		}
		return baseState.setValue(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS, type);
	}

	@Override
	public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, level, pos, neighbor);
		if (level instanceof Level world) {
			world.setBlockAndUpdate(pos, getStateFromNeighbors(world, pos, state));
		}
	}

	@Override
	public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return true;
	}

}
