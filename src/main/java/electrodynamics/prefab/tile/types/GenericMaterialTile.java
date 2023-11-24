package electrodynamics.prefab.tile.types;

import electrodynamics.api.capability.types.gas.IGasHandlerItem;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
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

		Level world = getLevel();

		if (CapabilityUtils.hasFluidItemCap(stack) && hasComponent(IComponentType.FluidHandler)) {

			IComponentFluidHandler fluidHandler = getComponent(IComponentType.FluidHandler);

			IFluidHandlerItem handlerItem = CapabilityUtils.getFluidHandlerItem(stack);

			// first try to drain the item
			for (FluidTank tank : fluidHandler.getInputTanks()) {

				int space = tank.getSpace();

				FluidStack containedFluid = handlerItem.drain(space, FluidAction.SIMULATE);

				if (containedFluid.isEmpty()) {
					continue;
				}

				if (!world.isClientSide) {

					tank.fill(containedFluid, FluidAction.EXECUTE);

					if (!player.isCreative()) {

						handlerItem.drain(space, FluidAction.EXECUTE);

					}

					world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1, 1);

					player.setItemInHand(handIn, handlerItem.getContainer());

				}

				return InteractionResult.CONSUME;

			}
			// now try to fill it
			for (FluidTank tank : fluidHandler.getOutputTanks()) {

				FluidStack tankFluid = tank.getFluid();

				int taken = handlerItem.fill(tankFluid, FluidAction.EXECUTE);

				if (taken <= 0) {
					continue;
				}

				if (!world.isClientSide) {

					tank.drain(taken, FluidAction.EXECUTE);

					world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);

					player.setItemInHand(handIn, handlerItem.getContainer());

				}

				return InteractionResult.CONSUME;

			}
		}
		if (CapabilityUtils.hasGasItemCap(stack) && hasComponent(IComponentType.GasHandler)) {

			IComponentGasHandler gasHandler = getComponent(IComponentType.GasHandler);

			IGasHandlerItem handlerItem = CapabilityUtils.getGasHandlerItem(stack);

			// first try to drain the item
			for (GasTank tank : gasHandler.getInputTanks()) {

				double space = tank.getSpace();

				GasStack containedGas = handlerItem.drainTank(0, space, GasAction.SIMULATE);

				if (containedGas.isEmpty()) {
					continue;
				}

				if (!world.isClientSide) {

					tank.fill(containedGas, GasAction.EXECUTE);

					if (!player.isCreative()) {

						handlerItem.drainTank(0, space, GasAction.EXECUTE);

					}

					world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), SoundSource.PLAYERS, 1, 1);

					player.setItemInHand(handIn, handlerItem.getContainer());

				}

				return InteractionResult.CONSUME;

			}
			// now try to fill it
			for (GasTank tank : gasHandler.getOutputTanks()) {

				GasStack tankGas = tank.getGas();

				double taken = handlerItem.fillTank(0, tankGas, GasAction.EXECUTE);

				if (taken <= 0) {
					continue;
				}

				if (!world.isClientSide) {

					tank.drain(taken, GasAction.EXECUTE);

					world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), SoundSource.PLAYERS, 1, 1);

					player.setItemInHand(handIn, handlerItem.getContainer());

				}

				return InteractionResult.CONSUME;

			}
		}
		return super.use(player, handIn, hit);
	}

}
