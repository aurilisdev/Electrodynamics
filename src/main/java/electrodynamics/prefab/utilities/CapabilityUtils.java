package electrodynamics.prefab.utilities;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

public class CapabilityUtils {

	/* Items */

	// FLUID

	public static boolean isFluidItemNull() {
		return ForgeCapabilities.FLUID_HANDLER_ITEM == null;
	}

	public static Capability<IFluidHandlerItem> getFluidItemCap() {
		return ForgeCapabilities.FLUID_HANDLER_ITEM;
	}

	public static boolean hasFluidItemCap(ItemStack stack) {
		return stack.getCapability(getFluidItemCap()).map(m -> true).orElse(false);
	}

	public static int simFill(ItemStack stack, FluidStack fluid) {
		return stack.getCapability(getFluidItemCap()).map(m -> m.fill(fluid, FluidAction.SIMULATE)).orElse(0);
	}

	public static void fill(ItemStack stack, FluidStack fluid) {
		stack.getCapability(getFluidItemCap()).ifPresent(h -> h.fill(fluid, FluidAction.EXECUTE));
	}

	public static FluidStack simDrain(ItemStack stack, FluidStack fluid) {
		return stack.getCapability(getFluidItemCap()).map(m -> m.drain(fluid, FluidAction.SIMULATE)).orElse(FluidStack.EMPTY);
	}

	public static FluidStack simDrain(ItemStack stack, int amount) {
		return stack.getCapability(getFluidItemCap()).map(m -> m.drain(amount, FluidAction.SIMULATE)).orElse(FluidStack.EMPTY);
	}

	public static void drain(ItemStack stack, FluidStack fluid) {
		stack.getCapability(getFluidItemCap()).ifPresent(h -> h.drain(fluid, FluidAction.EXECUTE));
	}

	public static boolean canFillItemStack(ItemStack stack, FluidStack fluid) {
		return stack.getCapability(getFluidItemCap()).map(m -> {
			if (m instanceof FluidHandlerItemStack f) {
				return f.canFillFluidType(fluid);
			} else if (m instanceof FluidHandlerItemStackSimple f2) {
				return f2.canFillFluidType(fluid);
			} else {
				return false;
			}
		}).orElse(false);
	}

	public static FluidStack getItemStackFluid(ItemStack stack, int tank) {
		return stack.getCapability(getFluidItemCap()).map(m -> {
			if (m instanceof FluidHandlerItemStack || m instanceof FluidHandlerItemStackSimple) {
				return m.getFluidInTank(tank);
			}
			return FluidStack.EMPTY;
		}).orElse(FluidStack.EMPTY);
	}

	public static int getItemStackCapacity(ItemStack stack, int tank) {
		return stack.getCapability(getFluidItemCap()).map(m -> {
			if (m instanceof FluidHandlerItemStack || m instanceof FluidHandlerItemStackSimple) {
				return m.getTankCapacity(tank);
			}
			return 0;
		}).orElse(0);
	}

	// ITEM

	/* Tiles */

	// FLUID

	public static boolean isFluidNull() {
		return ForgeCapabilities.FLUID_HANDLER == null;
	}

	public static Capability<IFluidHandler> getFluidCap() {
		return ForgeCapabilities.FLUID_HANDLER;
	}

	public static boolean hasFluidCap(BlockEntity tile) {
		return tile.getCapability(getFluidCap()).map(m -> true).orElse(false);
	}

	public static int simFill(BlockEntity tile, FluidStack fluid) {
		return tile.getCapability(getFluidCap()).map(m -> m.fill(fluid, FluidAction.SIMULATE)).orElse(0);
	}

	public static void fill(BlockEntity tile, FluidStack fluid) {
		tile.getCapability(getFluidCap()).ifPresent(h -> h.fill(fluid, FluidAction.EXECUTE));
	}

	public static FluidStack simDrain(BlockEntity tile, FluidStack fluid) {
		return tile.getCapability(getFluidCap()).map(m -> m.drain(fluid, FluidAction.SIMULATE)).orElse(FluidStack.EMPTY);
	}

	public static void drain(BlockEntity tile, FluidStack fluid) {
		tile.getCapability(getFluidCap()).ifPresent(h -> h.drain(fluid, FluidAction.EXECUTE));
	}

}
