package electrodynamics.prefab.utilities;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
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

	// ITEM

	// GAS

	public static boolean isGasItemNull() {
		return ElectrodynamicsCapabilities.GAS_HANDLER_ITEM == null;
	}
	
	public static boolean hasGasItemCap(ItemStack stack) {
		return stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).isPresent();
	}
	
	public static double fillGasItem(ItemStack stack, GasStack gas, GasAction action) {
		return stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).map(cap -> cap.fillTank(0, gas, action)).orElse(0.0);
	}
	
	public static GasStack drainGasItem(ItemStack stack, GasStack gas, GasAction action) {
		return stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).map(cap -> cap.drainTank(0, gas, action)).orElse(GasStack.EMPTY);
	}
	
	public static GasStack drainGasItem(ItemStack stack, double amount, GasAction action) {
		return stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).map(cap -> cap.drainTank(0, amount, action)).orElse(GasStack.EMPTY);
	}

	/* Tiles */

	// FLUID

	public static boolean isFluidNull() {
		return ForgeCapabilities.FLUID_HANDLER == null;
	}
	
	
	public static final IGasHandler EMPTY_GAS = new IGasHandler() {

		@Override
		public int getTanks() {
			return 1;
		}

		@Override
		public GasStack getGasInTank(int tank) {
			return GasStack.EMPTY;
		}

		@Override
		public double getTankCapacity(int tank) {
			return 0;
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			return 0;
		}

		@Override
		public int getTankMaxPressure(int tank) {
			return 0;
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
			return false;
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			return 0;
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			return GasStack.EMPTY;
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			return GasStack.EMPTY;
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			return -1;
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			return -1;
		}

	};
	
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
