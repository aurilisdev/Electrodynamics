package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentFluidHandlerSimple extends AbstractFluidHandler<ComponentFluidHandlerSimple> {

    @Nullable
    private FluidTank fluidTank;
    private List<Fluid> validFluids = new ArrayList<>();

    public ComponentFluidHandlerSimple(GenericTile source) {
    	super(source);
    }
    
    @Override
    public void loadFromNBT(CompoundTag nbt) {
    	CompoundTag tag = new CompoundTag();
    	tag.putString("FluidName", fluidTank.getFluid().getRawFluid().getRegistryName().toString());
    	tag.putInt("Amount", fluidTank.getFluid().getAmount());
    	if (fluidTank.getFluid().getTag() != null) {
    	    tag.put("Tag", fluidTank.getFluid().getTag());
    	}
    	tag.putInt("cap", fluidTank.getCapacity());
    	nbt.put("fluidtank", tag);
    }
    
    @Override
    public void saveToNBT(CompoundTag nbt) {
    	CompoundTag compound = nbt.getCompound("fluidtank");
    	int cap = compound.getInt("cap");
    	FluidStack stack = FluidStack.loadFluidStackFromNBT(compound);
    	FluidTank tank = new FluidTank(cap);
    	tank.setFluid(stack);
    	fluidTank = tank;
    }

	@Override
	public int getTanks() {
		return 1;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return fluidTank.getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		return fluidTank.getCapacity();
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return fluidTank.isFluidValid(stack);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return fluidTank.drain(maxDrain, action);
	}

	@Override
	protected void addFluidTank(Fluid fluid, boolean isInput) {
		if(fluidTank == null) {
			fluidTank = new FluidTank(this.tankCapacity);
			fluidTank.setFluid(new FluidStack(fluid, 0));
		}
	}

	@Override
	protected void setFluidInTank(FluidStack stack, int tank, boolean isInput) {
		fluidTank.setFluid(stack);
	}

	@Override
	public ComponentFluidHandlerSimple setManualFluids(int tankCount, boolean isInput, int capacity, Fluid... fluids) {
		this.tankCapacity = capacity;
		for(Fluid fluid : fluids) {
			addValidFluid(fluid, isInput);
		}
		if(fluidTank == null) {
			fluidTank = new FluidTank(capacity);
		}
		return this;
	}

	@Override
	public ComponentFluidHandlerSimple setManualFluidTags(int tankCount, boolean isInput,
			int capacity, IOptionalNamedTag<Fluid>... tags) {
		List<Fluid> fluids = new ArrayList<>();
		for(IOptionalNamedTag<Fluid> tag : tags) {
			fluids.addAll(tag.getValues());
		}
		Fluid[] arr = new Fluid[fluids.size()];
		for(int i = 0; i < fluids.size(); i++) {
			arr[i] = fluids.get(i);
		}
		return setManualFluids(tankCount, isInput, capacity, arr);
	}

	@Override
	public FluidStack getFluidInTank(int tank, boolean isInput) {
		return fluidTank.getFluid();
	}

	@Override
	public FluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
		return fluidTank;
	}

	@Override
	public FluidTank[] getInputTanks() {
		return new FluidTank[] {fluidTank};
	}

	@Override
	public FluidTank[] getOutputTanks() {
		return new FluidTank[] {fluidTank};
	}

	@Override
	public List<Fluid> getValidInputFluids() {
		return validFluids;
	}

	@Override
	public List<Fluid> getValidOutputFluids() {
		return validFluids;
	}

	@Override
	protected void addValidFluid(Fluid fluid, boolean isInput) {
		if(!validFluids.contains(fluid)) {
			validFluids.add(fluid);
		}
	}

	@Override
	public int getInputTankCount() {
		return getTanks();
	}

	@Override
	public int getOutputTankCount() {
		return getTanks();
	}

	@Override
	public void addFluidToTank(FluidStack fluid, boolean isInput) {
		fluidTank.fill(fluid, FluidAction.EXECUTE);
	}

	@Override
	public void drainFluidFromTank(FluidStack fluid, boolean isInput) {
		fluidTank.drain(fluid, FluidAction.EXECUTE);
	}

	@Override
	public void addFluids() {
		if (recipeType != null) {
		    Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(recipeType, getHolder().getLevel());
		    fluidTank = new FluidTank(this.tankCapacity);
		    List<Fluid> fluids = new ArrayList<>();
		    for (Recipe<?> iRecipe : recipes) {
		    	ElectrodynamicsRecipe recipe  = (ElectrodynamicsRecipe) iRecipe;
		    	if(this.hasInput) {
		    		//implement this at a later date
		    	}
		    	if(this.hasOutput) {
		    		//implement this at a later date
		    	}
		    }
		}
	}
}
