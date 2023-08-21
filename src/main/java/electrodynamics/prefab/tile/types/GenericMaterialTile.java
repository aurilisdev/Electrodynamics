package electrodynamics.prefab.tile.types;

import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentFluidHandler;
import electrodynamics.prefab.tile.components.utils.IComponentGasHandler;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

//You come up with a better name :D
public class GenericMaterialTile extends GenericTile {

	public GenericMaterialTile(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
		super(tileEntityTypeIn, worldPos, blockState);
	}

	@Override
	public InteractionResult use(Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(handIn);
		if (CapabilityUtils.hasFluidItemCap(stack) && hasComponent(ComponentType.FluidHandler)) {
			Level world = getLevel();
			IComponentFluidHandler handler = getComponent(ComponentType.FluidHandler);
			boolean isBucket = stack.getItem() instanceof BucketItem;
			// first try to drain the item
			FluidStack containedFluid = CapabilityUtils.drainFluidItem(stack, Integer.MAX_VALUE, FluidAction.SIMULATE);
			for (FluidTank tank : handler.getInputTanks()) {
				int amtTaken = tank.fill(containedFluid, FluidAction.SIMULATE);
				FluidStack taken = new FluidStack(containedFluid.getFluid(), amtTaken);
				if (isBucket && amtTaken == 1000 && (taken.getFluid().isSame(Fluids.WATER) || taken.getFluid().isSame(Fluids.LAVA))) {
					if (!world.isClientSide()) {
						CapabilityUtils.drainFluidItem(stack, taken, FluidAction.EXECUTE);
						tank.fill(taken, FluidAction.EXECUTE);
						if (!player.isCreative()) {
							player.setItemInHand(handIn, new ItemStack(Items.BUCKET, 1));
						}
						world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
					}
					return InteractionResult.CONSUME;
				}
				if (amtTaken > 0 && !isBucket) {
					if (!world.isClientSide()) {
						if (!player.isCreative()) {
							CapabilityUtils.drainFluidItem(stack, taken, FluidAction.EXECUTE);
						}
						tank.fill(taken, FluidAction.EXECUTE);
						world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);
					}
					return InteractionResult.CONSUME;
				}
			}
			// now try to fill it
			for (FluidTank tank : handler.getOutputTanks()) {
				FluidStack tankFluid = tank.getFluid();
				int amtTaken = CapabilityUtils.fillFluidItem(stack, tankFluid, FluidAction.SIMULATE);
				FluidStack taken = new FluidStack(tankFluid.getFluid(), amtTaken);
				if (isBucket && amtTaken == 1000 && (tankFluid.getFluid().isSame(Fluids.WATER) || tankFluid.getFluid().isSame(Fluids.LAVA))) {
					if (!level.isClientSide()) {
						player.setItemInHand(handIn, new ItemStack(taken.getFluid().getBucket(), 1));
						tank.drain(taken, FluidAction.EXECUTE);
						world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
					}
					return InteractionResult.CONSUME;
				}
				if (amtTaken > 0 && !isBucket) {
					if (!level.isClientSide()) {
						CapabilityUtils.fillFluidItem(stack, taken, FluidAction.EXECUTE);
						tank.drain(taken, FluidAction.EXECUTE);
						world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
					}
					return InteractionResult.CONSUME;
				}
			}
		}
		if (CapabilityUtils.hasGasItemCap(stack)) {
			if (hasComponent(ComponentType.GasHandler)) {

				Level world = getLevel();
				IComponentGasHandler handler = getComponent(ComponentType.GasHandler);
				// first try to drain the item
				GasStack containedGas = CapabilityUtils.drainGasItem(stack, Integer.MAX_VALUE, GasAction.SIMULATE);
				for (GasTank tank : handler.getInputTanks()) {
					double amtTaken = tank.fill(containedGas, GasAction.SIMULATE);
					GasStack taken = new GasStack(containedGas.getGas(), amtTaken, containedGas.getTemperature(), containedGas.getPressure());
					if (amtTaken > 0) {
						if (!level.isClientSide()) {
							if (!player.isCreative()) {
								CapabilityUtils.drainGasItem(stack, taken, GasAction.EXECUTE);
							}
							tank.fill(taken, GasAction.EXECUTE);
							world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), SoundSource.PLAYERS, 1, 1);
						}

						return InteractionResult.CONSUME;
					}
				}
				// now try to fill it
				for (GasTank tank : handler.getOutputTanks()) {
					GasStack tankGas = tank.getGas();

					double amtTaken = CapabilityUtils.fillGasItem(stack, tankGas, GasAction.SIMULATE);
					GasStack taken = new GasStack(tankGas.getGas(), amtTaken, tankGas.getTemperature(), tankGas.getPressure());
					if (amtTaken > 0) {
						if (!level.isClientSide()) {
							CapabilityUtils.fillGasItem(stack, taken, GasAction.EXECUTE);
							tank.drain(taken, GasAction.EXECUTE);
							world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), SoundSource.PLAYERS, 1, 1);
						}

						return InteractionResult.CONSUME;
					}
				}

			}
		}
		return super.use(player, handIn, hit);
	}

}
