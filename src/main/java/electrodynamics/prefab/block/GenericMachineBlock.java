package electrodynamics.prefab.block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import electrodynamics.api.IWrenchItem;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.generic.AbstractFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GenericMachineBlock extends GenericEntityBlockWaterloggable {

	protected BlockEntitySupplier<BlockEntity> blockEntitySupplier;

	public static HashMap<BlockPos, LivingEntity> IPLAYERSTORABLE_MAP = new HashMap<>();

	public GenericMachineBlock(BlockEntitySupplier<BlockEntity> blockEntitySupplier) {
		super(Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops());
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
		this.blockEntitySupplier = blockEntitySupplier;
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
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		BlockEntity tile = worldIn.getBlockEntity(pos);
		if (!(state.getBlock() == newState.getBlock() && state.getValue(FACING) != newState.getValue(FACING)) && tile instanceof GenericTile generic && generic.hasComponent(ComponentType.Inventory)) {
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

				AbstractFluidHandler<?> handler = generic.getComponent(ComponentType.FluidHandler);
				boolean isBucket = stack.getItem() instanceof BucketItem;
				// first try to drain the item
				for (FluidTank tank : handler.getInputTanks()) {
					FluidStack containedFluid = CapabilityUtils.simDrain(stack, Integer.MAX_VALUE);
					int amtTaken = handler.getValidInputFluids().contains(containedFluid.getFluid()) ? tank.fill(containedFluid, FluidAction.SIMULATE) : 0;
					FluidStack taken = new FluidStack(containedFluid.getFluid(), amtTaken);
					if (amtTaken == 1000) {
						CapabilityUtils.drain(stack, taken);
						tank.fill(taken, FluidAction.EXECUTE);
						if (!player.isCreative()) {
							player.setItemInHand(handIn, new ItemStack(Items.BUCKET, 1));
						}
						worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
						return InteractionResult.FAIL;
					} else if (amtTaken > 0 && !isBucket) {
						CapabilityUtils.drain(stack, taken);
						tank.fill(taken, FluidAction.EXECUTE);
						worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
						return InteractionResult.FAIL;
					}
				}
				// now try to fill it
				for (FluidTank tank : handler.getOutputTanks()) {
					FluidStack tankFluid = tank.getFluid();
					int amtTaken = CapabilityUtils.simFill(stack, tankFluid);
					FluidStack taken = new FluidStack(tankFluid.getFluid(), amtTaken);
					if (isBucket && amtTaken == 1000 && (tankFluid.getFluid().isSame(Fluids.WATER) || tankFluid.getFluid().isSame(Fluids.LAVA))) {
						player.setItemInHand(handIn, new ItemStack(taken.getFluid().getBucket(), 1));
						tank.drain(taken, FluidAction.EXECUTE);
						worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
						return InteractionResult.FAIL;
					} else if (amtTaken > 0 && !isBucket) {
						CapabilityUtils.fill(stack, taken);
						tank.drain(taken, FluidAction.EXECUTE);
						worldIn.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
						return InteractionResult.FAIL;
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
		tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC).ifPresent(el -> {
			double joules = el.getJoulesStored();
			if (joules > 0) {
				stack.getOrCreateTag().putDouble("joules", joules);
			}
		});
		return Arrays.asList(stack);
	}

	public boolean isIPlayerStorable() {
		return false;
	}

}
