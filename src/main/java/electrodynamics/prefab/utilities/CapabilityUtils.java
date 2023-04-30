package electrodynamics.prefab.utilities;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
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

}
