package electrodynamics.common.block;

import java.util.HashSet;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.multiblock.IMultiblockNode;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.common.tile.quarry.TileQuarry;
import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockMachine extends GenericMachineBlock implements IMultiblockNode {

	public static final BooleanProperty ON = BlockStateProperties.LIT;
	public static final IntegerProperty BATTERY_CHARGE = BlockStateProperties.AGE_7;
	
	public static final VoxelShape[] STANDARD_CUBE = new VoxelShape[] { Shapes.block(), Shapes.block(), Shapes.block(), Shapes.block(), Shapes.block(), Shapes.block() };

	public static final HashSet<Subnode> advancedsolarpanelsubnodes = new HashSet<>();
	public static final HashSet<Subnode> windmillsubnodes = new HashSet<>();
	static {
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				if (i == 0 && j == 0) {
					advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), Shapes.block()));
				} else {
					advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), Shapes.box(0, 13.0 / 16.0, 0, 1, 1, 1)));
				}
			}
		}
		windmillsubnodes.add(new Subnode(new BlockPos(0, 1, 0), Shapes.block()));
	}

	public final SubtypeMachine machine;

	public BlockMachine(SubtypeMachine machine) {
		super(machine::createTileEntity);
		this.machine = machine;
		if (machine.litBrightness > 0) {
			registerDefaultState(stateDefinition.any().setValue(ON, false));
		}

	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		if(machine.propogateLightDown) {
			return true;
		}
		return super.propagatesSkylightDown(pState, pLevel, pPos);
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (machine == SubtypeMachine.downgradetransformer || machine == SubtypeMachine.upgradetransformer) {
			TileTransformer tile = (TileTransformer) worldIn.getBlockEntity(pos);
			if (tile != null && tile.lastTransfer.getJoules() > 0) {
				ElectricityUtils.electrecuteEntity(entityIn, tile.lastTransfer);
				tile.lastTransfer = TransferPack.joulesVoltage(0, 0);
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return isValidMultiblockPlacement(state, worldIn, pos, machine == SubtypeMachine.advancedsolarpanel ? advancedsolarpanelsubnodes : machine == SubtypeMachine.windmill ? windmillsubnodes : new HashSet<Subnode>());
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (machine.showInItemGroup) {
			items.add(new ItemStack(this));
		}
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return machine.getRenderType();
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {

		if (machine.litBrightness > 0 && state.hasProperty(ON) && state.getValue(ON)) {
			return machine.litBrightness;
		}

		return super.getLightEmission(state, world, pos);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		BlockEntity tile = worldIn.getBlockEntity(pos);
		if (hasMultiBlock() && tile instanceof IMultiblockTileNode multi) {
			multi.onNodePlaced(worldIn, pos, state, placer, stack);
		}
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		BlockEntity tile = worldIn.getBlockEntity(pos);
		if (!(state.getBlock() == newState.getBlock() && state.getValue(FACING) != newState.getValue(FACING))) {

			if (tile instanceof IMultiblockTileNode multi) {
				multi.onNodeReplaced(worldIn, pos, true);
			}
			if (tile instanceof TileQuarry quarry && quarry.hasCorners()) {
				quarry.handleFramesDecay();
			}
		}
		if (newState.isAir()) {
			if (tile instanceof IMultiblockTileNode multi) {
				multi.onNodeReplaced(worldIn, pos, false);
			}
		}

		super.onRemove(state, worldIn, pos, newState, isMoving);

	}

	@Override
	public boolean hasMultiBlock() {
		return machine == SubtypeMachine.advancedsolarpanel || machine == SubtypeMachine.windmill;
	}

	@Override
	public boolean isIPlayerStorable() {
		return machine.isPlayerStorable();
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pPos, BlockState pState) {
		ItemStack stack = super.getCloneItemStack(level, pPos, pState);
		BlockEntity tile = level.getBlockEntity(pPos);
		if (tile != null) {
			tile.saveToItem(stack);
		}
		return stack;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(ON, false);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ON);
	}
}
