package electrodynamics.prefab.tile.types;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentFluidHandler;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GenericFluidTile extends GenericTile {

	protected GenericFluidTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public ActionResultType use(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		
		ItemStack stack = player.getItemInHand(handIn);

		World world = getLevel();

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

					world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundCategory.PLAYERS, 1, 1);

					player.setItemInHand(handIn, handlerItem.getContainer());

				}

				return ActionResultType.CONSUME;

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

					world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundCategory.PLAYERS, 1, 1);

					player.setItemInHand(handIn, handlerItem.getContainer());

				}

				return ActionResultType.CONSUME;

			}
		}
		return super.use(player, handIn, hit);
	}

}
