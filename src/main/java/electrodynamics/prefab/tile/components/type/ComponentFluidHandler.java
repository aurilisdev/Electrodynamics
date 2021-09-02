package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.UtilitiesTiles;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentFluidHandler implements Component, IFluidHandler {
    private GenericTile holder;

    @Override
    public void holder(GenericTile holder) {
	this.holder = holder;
    }

    public GenericTile getHolder() {
	return holder;
    }

    /*
     * We have to do this to make a distinction between input and output fluids. For
     * example, the chemical mixer with the old system has two sulfuric acid tanks
     * b/c it is both an input and output.
     * 
     * This is the most straight-forward solution to the problem to still keep the
     * multiple fluids in a machine at once system. The more complex solution is to
     * have each machine have a single universal input and output tank.
     */
    protected HashMap<Fluid, FluidTank> inputFluids = new HashMap<>();
    protected HashMap<Fluid, FluidTank> outputFluids = new HashMap<>();

    protected HashSet<Direction> relativeOutputDirections = new HashSet<>();
    protected HashSet<Direction> relativeInputDirections = new HashSet<>();
    protected HashSet<Direction> outputDirections = new HashSet<>();
    protected HashSet<Direction> inputDirections = new HashSet<>();
    protected Direction lastDirection = null;

    private IRecipeType<?> recipeType;
    private Class<? extends ElectrodynamicsRecipe> recipeClass;
    private int tankCapacity;
    private boolean hasInput;
    private boolean hasOutput;

    public ComponentFluidHandler(GenericTile source) {
	holder(source);
	if (holder.hasComponent(ComponentType.PacketHandler)) {
	    holder(source);
	    ComponentPacketHandler handler = holder.getComponent(ComponentType.PacketHandler);
	    handler.guiPacketWriter(this::writeGuiPacket);
	    handler.guiPacketReader(this::readGuiPacket);
	}
    }

    private void writeGuiPacket(CompoundNBT nbt) {
	saveToNBT(nbt);
    }

    private void readGuiPacket(CompoundNBT nbt) {
	loadFromNBT(null, nbt);
    }

    @Override
    public void loadFromNBT(BlockState state, CompoundNBT nbt) {

	ListNBT inputList = nbt.getList("inputList", 10);
	for (INBT tag : inputList) {
	    CompoundNBT compound = (CompoundNBT) tag;
	    FluidTank tank = new FluidTank(0).readFromNBT(compound);
	    addFluidTank(tank.getFluid(), compound.getInt("cap"), true);
	}

	ListNBT outputList = nbt.getList("outputList", 10);
	for (INBT tag : outputList) {
	    CompoundNBT compound = (CompoundNBT) tag;
	    FluidTank tank = new FluidTank(0).readFromNBT(compound);
	    addFluidTank(tank.getFluid(), compound.getInt("cap"), false);
	}
    }

    @Override
    public void saveToNBT(CompoundNBT nbt) {
	ListNBT inputList = new ListNBT();
	for (FluidTank tank : inputFluids.values()) {
	    CompoundNBT tag = new CompoundNBT();
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

	ListNBT outputList = new ListNBT();
	for (FluidTank tank : outputFluids.values()) {
	    CompoundNBT tag = new CompoundNBT();
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

    public ComponentFluidHandler universalInput() {
	for (Direction dir : Direction.values()) {
	    input(dir);
	}
	return this;
    }

    public ComponentFluidHandler input(Direction dir) {
	inputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler output(Direction dir) {
	outputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler relativeInput(Direction... dir) {
	relativeInputDirections.addAll(Arrays.asList(dir));
	return this;
    }

    public ComponentFluidHandler relativeOutput(Direction... dir) {
	relativeOutputDirections.addAll(Arrays.asList(dir));
	return this;
    }

    // Should only be used to manually add NEW fluid tanks (such as in the
    // combustion generator)
    // Otherwise use the JSON system
    public ComponentFluidHandler addFluidTank(Fluid fluid, int capacity, boolean isInput) {
	if (isInput) {
	    if (!getValidInputFluids().contains(fluid)) {
		inputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
		inputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	    }
	} else {
	    if (!getValidOutputFluids().contains(fluid)) {
		outputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
		outputFluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	    }
	}
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

    // Use categorized methods
    @Override
    public int getTanks() {
	return getCombinedTanks().size();
    }

    public int getInputTanks() {
	return inputFluids.values().size();
    }

    public int getOutputTanks() {
	return outputFluids.values().size();
    }

    // Use boolean method
    @Override
    public FluidStack getFluidInTank(int tank) {
	return ((FluidTank) getCombinedTanks().toArray()[tank]).getFluid();
    }

    public FluidStack getFluidInTank(int tank, boolean isInput) {
	if (isInput) {
	    return ((FluidTank) inputFluids.values().toArray()[tank]).getFluid();
	}
	return ((FluidTank) outputFluids.values().toArray()[tank]).getFluid();
    }

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

    public FluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
	if (isInput) {
	    return inputFluids.get(fluid);
	}
	return outputFluids.get(fluid);
    }

    public ComponentFluidHandler setFluidInTank(FluidStack stack, int tank, boolean isInput) {
	if (isInput) {
	    ((FluidTank) inputFluids.values().toArray()[tank]).setFluid(stack);
	} else {
	    ((FluidTank) outputFluids.values().toArray()[tank]).setFluid(stack);
	}
	return this;
    }

    public void addFluidToTank(FluidStack fluid, boolean isInput) {
	getTankFromFluid(fluid.getFluid(), isInput).getFluid().grow(fluid.getAmount());
    }

    public void drainFluidFromTank(FluidStack fluid, boolean isInput) {
	getTankFromFluid(fluid.getFluid(), isInput).getFluid().shrink(fluid.getAmount());
    }

    @Override
    public int getTankCapacity(int tank) {
	return ((FluidTank) getCombinedTanks().toArray()[tank]).getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
    	if(!getValidInputFluids().contains(stack.getFluid())) return false;
    	
		FluidTank fluidTank = getTankFromFluid(stack.getFluid(), true);
		if(fluidTank.getCapacity() > 0) {
			return true;
		} else {
			for(Fluid fluid : getValidInputFluids()) {
    			FluidTank temp = getTankFromFluid(fluid, true);
    			if(!temp.getFluid().getFluid().isEquivalentTo(stack.getFluid()) && temp.getFluidAmount() > 0) {
    				return false;
    			}
    		}
			return true;
		}
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
	Direction relative = UtilitiesTiles.getRelativeSide(
		holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
			: Direction.UP,
		lastDirection);
	boolean canFill = inputDirections.contains(lastDirection)
		|| holder.hasComponent(ComponentType.Direction) && relativeInputDirections.contains(relative);
	return canFill && isFluidValid(0, resource) ? inputFluids.get(resource.getFluid()).fill(resource, action) : 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	Direction relative = UtilitiesTiles.getRelativeSide(
		holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
			: Direction.UP,
		lastDirection);
	boolean canDrain = outputDirections.contains(lastDirection)
		|| holder.hasComponent(ComponentType.Direction) && relativeOutputDirections.contains(relative);
	return canDrain ? outputFluids.get(resource.getFluid()).drain(resource, action)
		: FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	return FluidStack.EMPTY;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction side) {
	lastDirection = side;
	if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
	    return false;
	}
	if (side == null || inputDirections.contains(side) || outputDirections.contains(side)) {
	    return true;
	}
	Direction dir = holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
		: null;
	if (dir != null) {
	    return relativeInputDirections.contains(UtilitiesTiles.getRelativeSide(dir, side))
		    || relativeOutputDirections.contains(UtilitiesTiles.getRelativeSide(dir, side));
	}
	return false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	lastDirection = facing;
	return hasCapability(capability, facing) ? (LazyOptional<T>) LazyOptional.of(() -> this) : LazyOptional.empty();
    }

    @Override
    public ComponentType getType() {
	return ComponentType.FluidHandler;
    }

    public Collection<FluidTank> getInputFluidTanks() {
	return inputFluids.values();
    }

    public Collection<FluidTank> getOutputFluidTanks() {
	return outputFluids.values();
    }

    private Collection<FluidTank> getCombinedTanks() {
	return Stream.concat(inputFluids.values().stream(), outputFluids.values().stream()).collect(Collectors.toList());
    }

    public List<Fluid> getValidInputFluids() {
	List<Fluid> valid = new ArrayList<>();
	valid.addAll(inputFluids.keySet());
	return valid;
    }

    public List<Fluid> getValidOutputFluids() {
	List<Fluid> valid = new ArrayList<>();
	valid.addAll(outputFluids.keySet());
	return valid;
    }

    public <T extends ElectrodynamicsRecipe> ComponentFluidHandler setAddFluidsValues(Class<T> recipeClass, IRecipeType<?> recipeType, int capacity,
	    boolean hasInput, boolean hasOutput) {
	this.recipeClass = recipeClass;
	this.recipeType = recipeType;
	tankCapacity = capacity;
	this.hasInput = hasInput;
	this.hasOutput = hasOutput;
	return this;
    }
    
    //needed for Universal; ignore here
    public ComponentFluidHandler setValidFluids(List<Fluid> fluids) {
		return this;
	}

    // this has to be called on world load; otherwise the RecipeManager is null
    public void addFluids() {
	if (recipeType != null) {
	    Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(recipeType, holder.getWorld());
	    for (IRecipe<?> iRecipe : recipes) {
		if (hasInput) {
		    List<Ingredient> ingredients = recipeClass.cast(iRecipe).getIngredients();
		    Fluid fluid = ((FluidIngredient) recipeClass.cast(iRecipe).getIngredients().get(0 + ingredients.size() - 1)).getFluidStack()
			    .getFluid();
		    addFluidTank(fluid, tankCapacity, true);
		}
		if (hasOutput) {
		    IFluidRecipe fRecipe = (IFluidRecipe) recipeClass.cast(iRecipe);
		    Fluid fluid = fRecipe.getFluidRecipeOutput().getFluid();
		    addFluidTank(fluid, tankCapacity, false);
		}
	    }
	}
    }
}
