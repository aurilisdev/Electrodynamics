package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentFluidHandlerMulti extends AbstractFluidHandler<ComponentFluidHandlerMulti> {

    private HashMap<Fluid, FluidTank> inputFluids = new HashMap<>();
    private HashMap<Fluid, FluidTank> outputFluids = new HashMap<>();

    private RecipeType<?> recipeType;
    private Class<? extends ElectrodynamicsRecipe> recipeClass;
    private int tankCapacity;
    private boolean hasInput;
    private boolean hasOutput;

    public ComponentFluidHandlerMulti(GenericTile source) {
	super(source);
    }

    @Override
    public void loadFromNBT(CompoundTag nbt) {
	ListTag inputList = nbt.getList("inputList", 10);
	for (Tag tag : inputList) {
	    CompoundTag compound = (CompoundTag) tag;
	    FluidTank tank = new FluidTank(compound.getInt("cap")).readFromNBT(compound);
	    addFluidTank(tank.getFluid(), compound.getInt("cap"), true);
	}

	ListTag outputList = nbt.getList("outputList", 10);
	for (Tag tag : outputList) {
	    CompoundTag compound = (CompoundTag) tag;
	    FluidTank tank = new FluidTank(compound.getInt("cap")).readFromNBT(compound);
	    addFluidTank(tank.getFluid(), compound.getInt("cap"), false);
	}
    }

    @Override
    public void saveToNBT(CompoundTag nbt) {
	ListTag inputList = new ListTag();
	for (FluidTank tank : inputFluids.values()) {
	    CompoundTag tag = new CompoundTag();
	    tag.putString("FluidName", tank.getFluid().getRawFluid().getRegistryName().toString());
	    tag.putInt("Amount", tank.getFluid().getAmount());

	    if (tank.getFluid().getTag() != null) {
		tag.put("Tag", tank.getFluid().getTag());
	    }
	    // tank capacities needed for JSON tanks
	    tag.putInt("cap", tank.getCapacity());
	    inputList.add(tag);
	}
	nbt.put("inputList", inputList);

	ListTag outputList = new ListTag();
	for (FluidTank tank : outputFluids.values()) {
	    CompoundTag tag = new CompoundTag();
	    tag.putString("FluidName", tank.getFluid().getRawFluid().getRegistryName().toString());
	    tag.putInt("Amount", tank.getFluid().getAmount());

	    if (tank.getFluid().getTag() != null) {
		tag.put("Tag", tank.getFluid().getTag());
	    }
	    tag.putInt("cap", tank.getCapacity());
	    outputList.add(tag);
	}
	nbt.put("outputList", outputList);
    }

    @Override
    public ComponentFluidHandlerMulti addFluidTank(Fluid fluid, int capacity, boolean isInput) {
	if (isInput) {
	    if (!getValidInputFluids().contains(fluid)) {
		inputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
		inputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	    }
	} else if (!getValidOutputFluids().contains(fluid)) {
	    outputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
	    outputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	}
	return this;
    }
    
    @Override
	public ComponentFluidHandlerMulti addFluidTank(IOptionalNamedTag<Fluid> tag, int capacity, boolean isInput) {
    	if (isInput) {
    	    for(Fluid fluid : tag.getValues()) {
    	    	if (!fluid.getRegistryName().toString().toLowerCase().contains("flow") &&
    	    			!getValidInputFluids().contains(fluid)) {
    	    		inputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
    	    		inputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
        	    }	
    	    }
    	} else {
    		for(Fluid fluid : tag.getValues()) {
    			if (!fluid.getRegistryName().toString().toLowerCase().contains("flow") &&
    					!getValidOutputFluids().contains(fluid)) {
    	    		outputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
    	    		outputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
    			}
    		}
    	}
		return this;
	}

    // Use categorized methods
    @Override
    public int getTanks() {
	return getCombinedTanks().size();
    }

    @Override
    public int getInputTanks() {
	return inputFluids.size();
    }

    @Override
    public int getOutputTanks() {
	return outputFluids.size();
    }

    // Use boolean method
    @Override
    public FluidStack getFluidInTank(int tank) {
	return ((FluidTank) getCombinedTanks().toArray()[tank]).getFluid();
    }

    @Override
    public FluidStack getFluidInTank(int tank, boolean isInput) {
	if (isInput) {
	    return ((FluidTank) inputFluids.values().toArray()[tank]).getFluid();
	}
	return ((FluidTank) outputFluids.values().toArray()[tank]).getFluid();
    }

    @Override
    public FluidStack getStackFromFluid(Fluid fluid, boolean isInput) {
	if (isInput && getValidInputFluids().contains(fluid)) {
	    if (inputFluids.get(fluid).getFluid() == null) {
		inputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	    }
	    return inputFluids.get(fluid).getFluid();
	} else if (getValidOutputFluids().contains(fluid)) {
	    if (outputFluids.get(fluid).getFluid() == null) {
		outputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	    }
	    return outputFluids.get(fluid).getFluid();
	}
	return FluidStack.EMPTY;
    }

    @Override
    public FluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
	if (isInput) {
	    return getValidInputFluids().contains(fluid) ? inputFluids.get(fluid) : new FluidTank(0);
	}
	return getValidOutputFluids().contains(fluid) ? outputFluids.get(fluid) : new FluidTank(0);
    }

    @Override
    public ComponentFluidHandlerMulti setFluidInTank(FluidStack stack, int tank, boolean isInput) {
	if (isInput) {
	    ((FluidTank) inputFluids.values().toArray()[tank]).setFluid(stack);
	} else {
	    ((FluidTank) outputFluids.values().toArray()[tank]).setFluid(stack);
	}
	return this;
    }

    @Override
    public void addFluidToTank(FluidStack fluid, boolean isInput) {
	getTankFromFluid(fluid.getFluid(), isInput).getFluid().grow(fluid.getAmount());
    }

    @Override
    public void drainFluidFromTank(FluidStack fluid, boolean isInput) {
	getTankFromFluid(fluid.getFluid(), isInput).getFluid().shrink(fluid.getAmount());
    }

    @Override
    public int getTankCapacity(int tank) {
	return ((FluidTank) getCombinedTanks().toArray()[tank]).getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	if (!getValidInputFluids().contains(stack.getFluid())) {
	    return false;
	}

	FluidTank fluidTank = getTankFromFluid(stack.getFluid(), true);
	if (fluidTank.getCapacity() > 0) {
	    return true;
	}
	for (Fluid fluid : getValidInputFluids()) {
	    FluidTank temp = getTankFromFluid(fluid, true);
	    if (!temp.getFluid().getFluid().isSame(stack.getFluid()) && temp.getFluidAmount() > 0) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	return FluidStack.EMPTY;
    }

    @Override
    public Collection<FluidTank> getInputFluidTanks() {
	return inputFluids.values();
    }

    @Override
    public Collection<FluidTank> getOutputFluidTanks() {
	return outputFluids.values();
    }

    @Override
    public List<Fluid> getValidInputFluids() {
	return new ArrayList<>(inputFluids.keySet());
    }

    @Override
    public List<Fluid> getValidOutputFluids() {
	return new ArrayList<>(outputFluids.keySet());
    }

    @Override
    public void addFluids() {
	if (recipeType != null) {
	    Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(recipeType, getHolder().getLevel());
	    for (Recipe<?> iRecipe : recipes) {
		if (hasInput) {
		    List<Ingredient> ingredients = recipeClass.cast(iRecipe).getIngredients();
		    List<FluidStack> fluids = ((FluidIngredient) recipeClass.cast(iRecipe).getIngredients().get(0 + ingredients.size() - 1)).getMatchingFluids();
		    for(FluidStack fluid : fluids) {
		    	if(!fluid.getFluid().getRegistryName().toString().toLowerCase().contains("flow")) {
		    		addFluidTank(fluid.getFluid(), tankCapacity, true);
		    	}
		    }
		}
		if (hasOutput) {
		    IFluidRecipe fRecipe = (IFluidRecipe) recipeClass.cast(iRecipe);
		    Fluid fluid = fRecipe.getFluidRecipeOutput().getFluid();
		    addFluidTank(fluid, tankCapacity, false);
		}
	    }
	}
    }

    public <T extends ElectrodynamicsRecipe> ComponentFluidHandlerMulti setAddFluidsValues(Class<T> recipeClass, RecipeType<?> recipeType,
	    int capacity, boolean hasInput, boolean hasOutput) {
	this.recipeClass = recipeClass;
	this.recipeType = recipeType;
	tankCapacity = capacity;
	this.hasInput = hasInput;
	this.hasOutput = hasOutput;
	return this;
    }

    // private method for NBT to use; avoids overwriting fluids with empty values if
    // they're JSON-based
    private void addFluidTank(FluidStack stack, int capacity, boolean isInput) {
	if (isInput) {
	    Fluid fluid = stack.getRawFluid();
	    if (!getValidInputFluids().contains(fluid)) {
		inputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
	    }
	    inputFluids.get(fluid).setFluid(stack);
	} else {
	    Fluid fluid = stack.getRawFluid();
	    if (!getValidOutputFluids().contains(fluid)) {
		outputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
	    }
	    outputFluids.get(fluid).setFluid(stack);
	}
    }

    private Collection<FluidTank> getCombinedTanks() {
	return Stream.concat(inputFluids.values().stream(), outputFluids.values().stream()).collect(Collectors.toList());
    }

}
