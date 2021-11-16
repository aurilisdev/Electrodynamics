package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.IWrenchItem;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class BlockGenericMachine extends GenericEntityBlockWaterloggable {
    protected BlockGenericMachine() {
	super(Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops());
	registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (!(state.getBlock() == newState.getBlock() && state.getValue(FACING) != newState.getValue(FACING)) && tile instanceof GenericTile generic
		&& generic.hasComponent(ComponentType.Inventory)) {
	    Containers.dropContents(worldIn, pos, generic.<ComponentInventory>getComponent(ComponentType.Inventory));
	}
	super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
	return 0;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
	return 1;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
	if (worldIn.isClientSide) {
	    return InteractionResult.SUCCESS;
	}
	BlockEntity tile = worldIn.getBlockEntity(pos);
	ItemStack stack = player.getItemInHand(handIn);
	if (CapabilityUtils.hasFluidItemCap(stack)) {
	    if (tile instanceof GenericTile generic && generic.hasComponent(ComponentType.FluidHandler)) {
		AbstractFluidHandler<?> tank = generic.getComponent(ComponentType.FluidHandler);
		boolean isBucket = stack.getItem() instanceof BucketItem;

		FluidStack containedFluid = CapabilityUtils.simDrain(stack, Integer.MAX_VALUE);
		FluidTank inputFluidTank = tank.getTankFromFluid(containedFluid.getFluid(), true);
		int tankroom = inputFluidTank.getCapacity() - inputFluidTank.getFluidAmount();
		FluidStack amtTaken = CapabilityUtils.simDrain(stack, new FluidStack(containedFluid.getFluid(), tankroom));
		if (tank.isFluidValid(0, amtTaken) && amtTaken.getAmount() > 0 && !isBucket) {
		    CapabilityUtils.drain(stack, amtTaken);
		    tank.addFluidToTank(amtTaken, true);
		    worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
		    return InteractionResult.FAIL;
		} else if (tank.isFluidValid(0, amtTaken) && amtTaken.getAmount() >= 1000 && isBucket) {
		    CapabilityUtils.drain(stack, new FluidStack(amtTaken.getFluid(), 1000));
		    tank.addFluidToTank(new FluidStack(amtTaken.getFluid(), 1000), true);
		    player.setItemInHand(handIn, new ItemStack(Items.BUCKET, 1));
		    worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
		    return InteractionResult.FAIL;
		} else {
		    if (!containedFluid.getFluid().isSame(Fluids.EMPTY) && !isBucket) {
			FluidTank outputFluidTank = tank.getTankFromFluid(containedFluid.getFluid(), false);
			int amtAccepted = CapabilityUtils.simFill(stack, outputFluidTank.getFluid());
			if (amtAccepted > 0) {
			    CapabilityUtils.fill(stack, new FluidStack(containedFluid.getFluid(), amtAccepted));
			    tank.drainFluidFromTank(new FluidStack(containedFluid.getFluid(), amtAccepted), false);
			    worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
			    return InteractionResult.FAIL;
			}
		    } else {
			for (Fluid fluid : tank.getValidOutputFluids()) {
			    FluidTank outputFluidTank = tank.getTankFromFluid(fluid, false);
			    int amtAccepted = CapabilityUtils.simFill(stack, outputFluidTank.getFluid());
			    if (amtAccepted > 0 && !isBucket) {
				CapabilityUtils.fill(stack, new FluidStack(fluid, amtAccepted));
				tank.drainFluidFromTank(new FluidStack(fluid, amtAccepted), false);
				worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
				return InteractionResult.FAIL;
			    } else if (amtAccepted >= 1000 && isBucket && (outputFluidTank.getFluid().getFluid().isSame(Fluids.WATER)
				    || outputFluidTank.getFluid().getFluid().isSame(Fluids.LAVA))) {
				if (outputFluidTank.getFluid().getFluid().isSame(Fluids.WATER)) {
				    player.setItemInHand(handIn, new ItemStack(Items.WATER_BUCKET, 1));
				} else {
				    player.setItemInHand(handIn, new ItemStack(Items.LAVA_BUCKET, 1));
				}
				tank.drainFluidFromTank(new FluidStack(fluid, 1000), false);
				worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
				return InteractionResult.FAIL;
			    }
			}
		    }
		}
		if (generic.hasComponent(ComponentType.ContainerProvider)) {
		    player.openMenu(generic.getComponent(ComponentType.ContainerProvider));
		}
	    }
	    player.awardStat(Stats.INTERACT_WITH_FURNACE);
	    return InteractionResult.CONSUME;
	} else if (!(stack.getItem() instanceof IWrenchItem)) {
	    if (tile instanceof GenericTile generic && generic.hasComponent(ComponentType.ContainerProvider)) {
		player.openMenu(generic.getComponent(ComponentType.ContainerProvider));
	    }
	    player.awardStat(Stats.INTERACT_WITH_FURNACE);
	    return InteractionResult.CONSUME;
	}
	return InteractionResult.FAIL;
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

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	ItemStack stack = new ItemStack(this);
	BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
	tile.getCapability(CapabilityElectrodynamic.ELECTRODYNAMIC).ifPresent(el -> {
	    double joules = el.getJoulesStored();
	    if (joules > 0) {
		stack.getOrCreateTag().putDouble("joules", joules);
	    }
	});
	return Arrays.asList(stack);
    }

}
