package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import electrodynamics.Electrodynamics;
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

    protected HashMap<Fluid, FluidTank> fluids = new HashMap<>();
    
    protected HashSet<Direction> relativeOutputDirections = new HashSet<>();
    protected HashSet<Direction> relativeInputDirections = new HashSet<>();
    protected HashSet<Direction> outputDirections = new HashSet<>();
    protected HashSet<Direction> inputDirections = new HashSet<>();
    protected Direction lastDirection = null;

    //Inputs in slot 0, outputs in slot 1 ALWAYS
    private List<List<Fluid>> VALID_FLUIDS = getInitList();     


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

    public ComponentFluidHandler fluidTank(Fluid fluid, int capacity) {
	fluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
	fluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	return this;
    }

    @Override
    public int getTanks() {
	return fluids.values().size();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
	return ((FluidTank) fluids.values().toArray()[tank]).getFluid();
    }

    public FluidStack getStackFromFluid(Fluid fluid) {
	if (fluids.get(fluid).getFluid() == null) {
	    fluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	}
	return fluids.get(fluid).getFluid();
    }

    public FluidTank getTankFromFluid(Fluid fluid) {
	return fluids.get(fluid);
    }

    public ComponentFluidHandler setFluidInTank(FluidStack stack, int tank) {
	((FluidTank) fluids.values().toArray()[tank]).setFluid(stack);
	return this;
    }

    @Override
    public int getTankCapacity(int tank) {
    	return ((FluidTank) fluids.values().toArray()[tank]).getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	return ((FluidTank) fluids.values().toArray()[tank]).isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
	Direction relative = UtilitiesTiles.getRelativeSide(
		holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
			: Direction.UP,
		lastDirection);
	boolean canFill = inputDirections.contains(lastDirection)
		|| holder.hasComponent(ComponentType.Direction) && relativeInputDirections.contains(relative);
	return canFill ? fluids.get(resource.getFluid()).fill(resource, action) : 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	Direction relative = UtilitiesTiles.getRelativeSide(
		holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
			: Direction.UP,
		lastDirection);
	boolean canDrain = outputDirections.contains(lastDirection)
		|| holder.hasComponent(ComponentType.Direction) && relativeOutputDirections.contains(relative);
	return canDrain ? fluids.get(resource.getFluid()).drain(resource, action) : FluidStack.EMPTY;
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
    public void loadFromNBT(BlockState state, CompoundNBT nbt) {
		ListNBT list = nbt.getList("list", 10);
		for (INBT tag : list) {
		    CompoundNBT compound = (CompoundNBT) tag;
		    FluidTank tank = new FluidTank(0).readFromNBT(compound);
		    fluids.get(tank.getFluid().getRawFluid()).setFluid(tank.getFluid());
		}
    }

    @Override
    public void saveToNBT(CompoundNBT nbt) {
		ListNBT list = new ListNBT();
		for (FluidTank stack : fluids.values()) {
		    CompoundNBT tag = new CompoundNBT();
		    // Don't use native read cause it doesn't use getRawFluid
		    tag.putString("FluidName", stack.getFluid().getRawFluid().getRegistryName().toString());
		    tag.putInt("Amount", stack.getFluid().getAmount());
	
		    if (stack.getFluid().getTag() != null) {
			tag.put("Tag", stack.getFluid().getTag());
		    }
		    list.add(tag);
		}
    }

    public GenericTile getHolder() {
	return holder;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.FluidHandler;
    }

    public Collection<FluidTank> getFluidTanks() {
	return fluids.values();
    }

    public Set<Fluid> getFluids() {
	return fluids.keySet();
    }
    
    public List<List<Fluid>> getValidFluids(){
    	return VALID_FLUIDS;
    }

    //Now it has some teeth
    public <T extends ElectrodynamicsRecipe> ComponentFluidHandler addFluids(Class<T> recipeClass, IRecipeType<?> recipeType, 
    	int capacity, boolean hasInput, boolean hasOutput) {
    	
    	List<Fluid> input = new ArrayList<>();
    	List<Fluid> output = new ArrayList<>();
    	Electrodynamics.LOGGER.info("Method Called");
    	Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(recipeType, this.holder.getWorld());
    	Electrodynamics.LOGGER.info("Recipes retrieved");
    	recipes.forEach(recipe -> Electrodynamics.LOGGER.info("recipe: " + recipe.toString()));
    	for (IRecipe<?> iRecipe : recipes) {
    		T recipe = recipeClass.cast(iRecipe);
    		if(hasInput) {
    			List<Ingredient> ingredients = recipe.getIngredients();
    			Fluid fluid = ((FluidIngredient)recipe.getIngredients().get(0 + ingredients.size() - 1)).getFluidStack().getFluid();
    			input.add(fluid);
    			Electrodynamics.LOGGER.info("Input Fluids");
    			Electrodynamics.LOGGER.info(fluid.getRegistryName().toString());
    			fluidTank(fluid, capacity);
    		}
    		if(hasOutput) {
    			IFluidRecipe fRecipe = (IFluidRecipe)recipe;
    			Fluid fluid = fRecipe.getFluidRecipeOutput().getFluid();
    			output.add(fluid);
    			Electrodynamics.LOGGER.info("Output Fluids");
    			Electrodynamics.LOGGER.info(fluid.getRegistryName().toString());
    			fluidTank(fluid, capacity);
    		}
    	}
    	VALID_FLUIDS.set(0,input);
    	VALID_FLUIDS.set(1,output);
    	
    	return this;
    }
    
    private static List<List<Fluid>> getInitList(){
    	List<List<Fluid>> list = new ArrayList<>();
    	list.add(new ArrayList<Fluid>());
    	list.add(new ArrayList<Fluid>());
    	return list;
    }

}
