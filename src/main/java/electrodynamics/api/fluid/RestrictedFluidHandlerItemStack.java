package electrodynamics.api.fluid;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class RestrictedFluidHandlerItemStack extends FluidHandlerItemStack.SwapEmpty {

	private boolean hasInitHappened = false;
	private List<ResourceLocation> tags;
	private List<Fluid> fluids;

    public RestrictedFluidHandlerItemStack(ItemStack container, ItemStack emptyContainer, int capacity, 
    		Pair<List<ResourceLocation>, List<Fluid>> whitelistedFluids) {
		super(container, emptyContainer, capacity);
		tags = new ArrayList<>();
		whitelistedFluids.getFirst().forEach(h -> {
			tags.add(h);
		});
		
		fluids = new ArrayList<>();
		whitelistedFluids.getSecond().forEach(h -> {
			fluids.add(h);
		});
		
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
    	//check tags first
    	for(ResourceLocation loc : tags) {
    		List<Fluid> fluids = new ArrayList<>();
			for(INamedTag<Fluid> tg : FluidTags.getAllTags()) {
				if(tg.getName().equals(loc)) {
					fluids = tg.getAllElements();
					break;
				}
			}
    		for(Fluid fluid : fluids) {
    			//filter out flowing fluids
    			if(fluid.getRegistryName().toString().toLowerCase().contains("flow")) {
    				return false;
    			}
    			if (fluid.isEquivalentTo(stack.getFluid())) {
    				return true;
    			}
    		}
    	}
    	//next check specific fluids
		for (Fluid fluid : fluids) {
		    if (fluid.isEquivalentTo(stack.getFluid())) {
		    	return true;
		    }
		}
		return false;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
		return isFluidValid(0, fluid);
    }

    @Override
    public int fill(FluidStack resource, FluidAction doFill) {
	if (canFillFluidType(resource)) {
	    return super.fill(resource, doFill);
	}
	return 0;
    }

    /**
     * DO NOT CALL THIS
     * @param resource
     */
    public void fillInit(FluidStack resource) {
    	if(hasInitHappened) {
    		throw new UnsupportedOperationException("Do not call this method!");
    	}
    	setFluid(resource);
    }

    public ArrayList<Fluid> getWhitelistedFluids() {
    	ArrayList<Fluid> valid = new ArrayList<>();
    	ArrayList<Fluid> unique = new ArrayList<>();
    	for(ResourceLocation loc : tags) {
    		List<Fluid> fluids = new ArrayList<>();
			for(INamedTag<Fluid> tg : FluidTags.getAllTags()) {
				if(tg.getName().equals(loc)) {
					fluids = tg.getAllElements();
					break;
				}
			}
			//filter out flowing variants
			for(Fluid fluid : fluids) {
				if(!fluid.getRegistryName().toString().toLowerCase().contains("flow")) {
					valid.add(fluid);
    			}
			}
    	}
    	for(Fluid fluid : fluids) {
    		if(!valid.contains(fluid)) {
    			unique.add(fluid);
    		}
    	}
    	valid.addAll(unique);
    	
    	return valid;
    }
    
    /**
     * Can only set to true smartass
     * @param initHappened
     */
    public void hasInitHappened(boolean initHappened) {
    	if(initHappened) {
    		this.hasInitHappened = initHappened;
    	} else {
    		throw new UnsupportedOperationException("What did I tell you, don't call that method");
    	}
    }

}
