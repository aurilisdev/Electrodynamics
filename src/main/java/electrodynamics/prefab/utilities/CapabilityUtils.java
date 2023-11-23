package electrodynamics.prefab.utilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class CapabilityUtils {

	/* Items */

	// FLUID

	public static boolean isFluidItemNull() {
		return ForgeCapabilities.FLUID_HANDLER_ITEM == null;
	}

	public static boolean hasFluidItemCap(ItemStack stack) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
	}

	public static int fillFluidItem(ItemStack stack, FluidStack fluid, FluidAction action) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> cap.fill(fluid, action)).orElse(0);
	}

	public static FluidStack drainFluidItem(ItemStack stack, int amt, FluidAction action) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> cap.drain(amt, action)).orElse(FluidStack.EMPTY);
	}

	public static FluidStack drainFluidItem(ItemStack stack, FluidStack fluid, FluidAction action) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> cap.drain(fluid, action)).orElse(FluidStack.EMPTY);
	}

	/* Tiles */

	// FLUID

	public static boolean isFluidNull() {
		return ForgeCapabilities.FLUID_HANDLER == null;
	}

	public static final IFluidHandler EMPTY_FLUID = new IFluidHandler() {

		@Override
		public int getTanks() {
			return 1;
		}

		@Override
		public @NotNull FluidStack getFluidInTank(int tank) {
			return FluidStack.EMPTY;
		}

		@Override
		public int getTankCapacity(int tank) {
			return 0;
		}

		@Override
		public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
			return false;
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			return 0;
		}

		@Override
		public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
			return FluidStack.EMPTY;
		}

		@Override
		public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
			return FluidStack.EMPTY;
		}

	};

}
