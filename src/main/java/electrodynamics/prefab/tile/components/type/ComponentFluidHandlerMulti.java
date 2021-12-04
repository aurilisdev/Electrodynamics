package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.AbstractFluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentFluidHandlerMulti extends AbstractFluidHandler<ComponentFluidHandlerMulti> {

    // initialized to avoid any null errors in theory
    private FluidTank[] inputTanks = new FluidTank[0];
    private FluidTank[] outputTanks = new FluidTank[0];

    private List<Fluid> validInputs = new ArrayList<>();
    private List<Fluid> validOutputs = new ArrayList<>();

    public ComponentFluidHandlerMulti(GenericTile source) {
	super(source);
    }

    @Override
    public void loadFromNBT(CompoundTag nbt) {
	inputTanks = new FluidTank[nbt.getInt("inputSize")];
	ListTag inputList = nbt.getList("inputList", 10);
	for (int i = 0; i < inputList.size(); i++) {
	    CompoundTag compound = (CompoundTag) inputList.get(i);
	    FluidTank tank = new FluidTank(compound.getInt("cap")).readFromNBT(compound);
	    inputTanks[i] = tank;
	}
	outputTanks = new FluidTank[nbt.getInt("outputSize")];
	ListTag outputList = nbt.getList("outputList", 10);
	for (int i = 0; i < outputList.size(); i++) {
	    CompoundTag compound = (CompoundTag) outputList.get(i);
	    FluidTank tank = new FluidTank(compound.getInt("cap")).readFromNBT(compound);
	    outputTanks[i] = tank;
	}
    }

    @Override
    public void saveToNBT(CompoundTag nbt) {
	nbt.putInt("inputSize", inputTanks.length);
	ListTag inputList = new ListTag();
	for (FluidTank tank : inputTanks) {
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
	nbt.putInt("outputSize", outputTanks.length);
	ListTag outputList = new ListTag();
	for (FluidTank tank : outputTanks) {
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
    public int getTanks() {
	return inputTanks.length;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
	return inputTanks[tank].getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
	return inputTanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	if (getValidInputFluids().contains(stack.getFluid())) {
	    if (tank < inputTanks.length) {
		return inputTanks[tank].isFluidValid(stack);
	    }
	}
	return false;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	for (FluidTank tank : outputTanks) {
	    if (!tank.isEmpty()) {
		return tank.drain(maxDrain, action);
	    }
	}
	return FluidStack.EMPTY;
    }

    @Override
    protected void addFluidTank(Fluid fluid, boolean isInput) {
	if (isInput) {
	    boolean alreadyHas = false;
	    for (FluidTank tank : inputTanks) {
		if (tank.getFluid().getFluid().isSame(fluid)) {
		    alreadyHas = true;
		}
	    }
	    if (!alreadyHas) {
		for (FluidTank tank : inputTanks) {
		    if (tank.getFluid().isEmpty()) {
			tank.setFluid(new FluidStack(fluid, tankCapacity));
		    }
		}
	    }
	} else {
	    boolean alreadyHas = false;
	    for (FluidTank tank : outputTanks) {
		if (tank.getFluid().getFluid().isSame(fluid)) {
		    alreadyHas = true;
		}
	    }
	    if (!alreadyHas) {
		for (FluidTank tank : outputTanks) {
		    if (tank.getFluid().isEmpty()) {
			tank.setFluid(new FluidStack(fluid, tankCapacity));
		    }
		}
	    }
	}

    }

    @Override
    protected void setFluidInTank(FluidStack stack, int tank, boolean isInput) {
	if (isInput && tank < inputTanks.length) {
	    inputTanks[tank].setFluid(stack);
	} else if (tank < outputTanks.length) {
	    outputTanks[tank].setFluid(stack);
	}
    }

    @Override
    public ComponentFluidHandlerMulti setManualFluids(int tankCount, boolean isInput, int capacity, Fluid... fluids) {
	tankCapacity = capacity;
	for (Fluid fluid : fluids) {
	    addValidFluid(fluid, isInput);
	}
	if (isInput && inputTanks.length == 0) {
	    inputTanks = new FluidTank[tankCount];
	    for (int i = 0; i < tankCount; i++) {
		inputTanks[i] = new FluidTank(capacity);
	    }
	} else if (outputTanks.length == 0) {
	    outputTanks = new FluidTank[tankCount];
	    for (int i = 0; i < tankCount; i++) {
		outputTanks[i] = new FluidTank(capacity);
	    }
	}
	return this;
    }

    @Override
    public AbstractFluidHandler<ComponentFluidHandlerMulti> setManualFluidTags(int tanks, boolean isInput, int capacity,
	    IOptionalNamedTag<Fluid>... tags) {

	List<Fluid> fluids = new ArrayList<>();
	for (IOptionalNamedTag<Fluid> tag : tags) {
	    fluids.addAll(tag.getValues());
	}
	Fluid[] arr = new Fluid[fluids.size()];
	for (int i = 0; i < fluids.size(); i++) {
	    arr[i] = fluids.get(i);
	}
	return setManualFluids(tanks, isInput, capacity, arr);
    }

    @Override
    public FluidStack getFluidInTank(int tank, boolean isInput) {
	if (isInput) {
	    return inputTanks[tank].getFluid();
	}
	return outputTanks[tank].getFluid();
    }

    @Override
    public FluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
    	if (isInput) {
		    for (FluidTank tank : inputTanks) {
				if (tank.getFluid().getFluid().isSame(fluid)) {
				    return tank;
				}
		    }
		    for(FluidTank tank : inputTanks) {
		    	if(tank.isEmpty()) {
		    		return tank;
		    	}
		    }
		} else {
		    for (FluidTank tank : outputTanks) {
				if (tank.getFluid().getFluid().isSame(fluid)) {
				    return tank;
				}
		    }
		    for(FluidTank tank : outputTanks) {
		    	if(tank.isEmpty()) {
		    		return tank;
		    	}
		    }
		}
    	return new FluidTank(0);
    }

    @Override
    public FluidTank[] getInputTanks() {
	return inputTanks;
    }

    @Override
    public FluidTank[] getOutputTanks() {
	return outputTanks;
    }

    @Override
    public List<Fluid> getValidInputFluids() {
	return validInputs;
    }

    @Override
    public List<Fluid> getValidOutputFluids() {
	return validOutputs;
    }

    @Override
    protected void addValidFluid(Fluid fluid, boolean isInput) {
	if (isInput) {
	    if (!validInputs.contains(fluid)) {
		validInputs.add(fluid);
	    }
	} else if (!validOutputs.contains(fluid)) {
	    validOutputs.add(fluid);
	}
    }

    @Override
    public int getInputTankCount() {
	return inputTanks.length;
    }

    @Override
    public int getOutputTankCount() {
	return outputTanks.length;
    }

    @Override
    public void addFluidToTank(FluidStack fluid, boolean isInput) {
	FluidTank tank = getTankFromFluid(fluid.getFluid(), isInput);
	if (isInput) {
	    if (tank != null) {
		tank.fill(fluid, FluidAction.EXECUTE);
	    } else {
		for (int i = 0; i < inputTanks.length; i++) {
		    tank = inputTanks[i];
		    if (tank.getFluid().isEmpty()) {
			setFluidInTank(fluid, i, isInput);
			break;
		    }
		}
	    }
	} else if (tank != null) {
	    tank.fill(fluid, FluidAction.EXECUTE);
	} else {
	    for (int i = 0; i < outputTanks.length; i++) {
		tank = outputTanks[i];
		if (tank.getFluid().isEmpty()) {
		    setFluidInTank(fluid, i, isInput);
		    break;
		}
	    }
	}
    }

    @Override
    public void drainFluidFromTank(FluidStack fluid, boolean isInput) {
	FluidTank tank = getTankFromFluid(fluid.getFluid(), isInput);
	if (tank != null) {
	    tank.drain(fluid, FluidAction.EXECUTE);
	}
    }

    @Override
    public void addFluids() {
	if (recipeType != null) {
	    Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(recipeType, getHolder().getLevel());

	    int inTankCount = 0;
	    int outTankCount = 0;
	    List<Fluid> inputFluidHolder = new ArrayList<>();
	    List<Fluid> outputFluidHohlder = new ArrayList<>();

	    for (Recipe<?> iRecipe : recipes) {
		AbstractFluidRecipe recipe = (AbstractFluidRecipe) iRecipe;

		if (hasInput) {
		    int ingCount = recipe.getFluidIngredients().size();
		    if (ingCount > inTankCount) {
			inTankCount = ingCount;
		    }
		    for (FluidIngredient ing : recipe.getFluidIngredients()) {
			ing.getMatchingFluids().forEach(h -> inputFluidHolder.add(h.getFluid()));
		    }
		}

		int length = 0;
		if (hasOutput) {
		    outputFluidHohlder.add(recipe.getFluidRecipeOutput().getFluid());
		    length++;
		}
		if (recipe.hasFluidBiproducts()) {
		    for (FluidStack stack : recipe.getFullFluidBiStacks()) {
			outputFluidHohlder.add(stack.getFluid());
		    }
		    length += recipe.getFluidBiproductCount();
		}
		if (length > outTankCount) {
		    outTankCount = length;
		}
	    }
	    setManualFluids(inTankCount, true, tankCapacity, inputFluidHolder.toArray(new Fluid[inputFluidHolder.size()]));
	    setManualFluids(outTankCount, false, tankCapacity, outputFluidHohlder.toArray(new Fluid[inputFluidHolder.size()]));
	}
    }
}
