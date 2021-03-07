package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.item.IWrench;
import electrodynamics.api.tile.IWrenchable;
import electrodynamics.api.tile.electric.IElectrodynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockGenericMachine extends Block implements IWrenchable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public BlockGenericMachine() {
	super(Properties.create(Material.IRON).hardnessAndResistance(3.5F).sound(SoundType.METAL)
		.harvestTool(ToolType.PICKAXE).notSolid());
	setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Deprecated
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof IInventory
		&& !(state.getBlock() == newState.getBlock() && state.get(FACING) != newState.get(FACING))) {
	    InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tile);
	}
	super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
	return 0;
    }

    @Override
    @Deprecated
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
	return 1;
    }

    @Override
    @Deprecated
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
	    Hand handIn, BlockRayTraceResult hit) {
	if (worldIn.isRemote) {
	    return ActionResultType.SUCCESS;
	} else if (!(player.getHeldItem(handIn).getItem() instanceof IWrench)) {
	    TileEntity tileentity = worldIn.getTileEntity(pos);
	    if (tileentity instanceof INamedContainerProvider) {
		player.openContainer((INamedContainerProvider) tileentity);
	    }
	    player.addStat(Stats.INTERACT_WITH_FURNACE);
	    return ActionResultType.CONSUME;
	}
	return ActionResultType.FAIL;
    }

    @Override
    @Deprecated
    public BlockState rotate(BlockState state, Rotation rot) {
	return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Deprecated
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
	return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
	return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	builder.add(FACING);
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	ItemStack stack = new ItemStack(this);
	TileEntity tile = builder.get(LootParameters.BLOCK_ENTITY);
	if (tile instanceof IElectrodynamic) {
	    double joules = ((IElectrodynamic) tile).getJoulesStored();
	    if (joules > 0) {
		stack.getOrCreateTag().putDouble("joules", joules);
	    }
	}
	return Arrays.asList(stack);
    }

    @Override
    @Deprecated
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof IElectrodynamic && stack.hasTag()) {
	    IElectrodynamic el = (IElectrodynamic) tile;
	    el.setJoulesStored(stack.getOrCreateTag().getDouble("joules"));
	}
    }

    @Deprecated
    @Override
    public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
	player.world.setBlockState(pos, rotate(player.world.getBlockState(pos), Rotation.CLOCKWISE_90));
    }

    @Override
    public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
	World world = player.world;
	TileEntity te = world.getTileEntity(pos);
	if (te instanceof IInventory) {
	    InventoryHelper.dropInventoryItems(player.world, pos, (IInventory) te);
	}
	world.setBlockState(pos, Blocks.AIR.getDefaultState());
	world.addEntity(
		new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(getSelf())));
    }
}
