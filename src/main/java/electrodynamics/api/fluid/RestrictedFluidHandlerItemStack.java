package electrodynamics.api.fluid;

import java.util.ArrayList;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class RestrictedFluidHandlerItemStack extends FluidHandlerItemStack.SwapEmpty {

    private ArrayList<Fluid> WHITELISTED_FLUIDS;

    public RestrictedFluidHandlerItemStack(ItemStack container, ItemStack emptyContainer, int capacity, ArrayList<Fluid> whitelistedFluids) {
	super(container, emptyContainer, capacity);
	WHITELISTED_FLUIDS = whitelistedFluids;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	for (Fluid fluid : WHITELISTED_FLUIDS) {
	    if (fluid.isSame(stack.getFluid())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
	for (Fluid stack : WHITELISTED_FLUIDS) {
	    if (stack.isSame(fluid.getFluid())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction doFill) {
	if (canFillFluidType(resource)) {
	    return super.fill(resource, doFill);
	}
	return 0;
    }

    public ArrayList<Fluid> getWhitelistedFluids() {
	return WHITELISTED_FLUIDS;
    }

}
