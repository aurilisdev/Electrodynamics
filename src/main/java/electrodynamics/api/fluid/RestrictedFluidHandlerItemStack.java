package electrodynamics.api.fluid;

import java.util.ArrayList;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class RestrictedFluidHandlerItemStack extends FluidHandlerItemStack.SwapEmpty {

    private ArrayList<Fluid> whitelistedFluids;

    public RestrictedFluidHandlerItemStack(ItemStack container, ItemStack emptyContainer, int capacity, ArrayList<Fluid> whitelistedFluids) {
	super(container, emptyContainer, capacity);
	this.whitelistedFluids = whitelistedFluids;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	for (Fluid fluid : whitelistedFluids) {
	    if (fluid.isSame(stack.getFluid())) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
	for (Fluid stack : whitelistedFluids) {
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
	return whitelistedFluids;
    }

}
