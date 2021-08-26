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

	// We have to do this to make a distinction between input and output fluids
	// For example, the chemical mixer has two sulfuric acid tanks b/c it is both an
	// input and output
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
	private boolean inputFromNBT = false;
	private boolean outputFromNBT = false;

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
		ListNBT inputCaps = nbt.getList("inputCaps", 10);
		ListNBT inputList = nbt.getList("inputList", 10);

		for (int i = 0; i < inputList.size(); i++) {
			CompoundNBT compound = (CompoundNBT) inputList.get(i);
			FluidTank tank = new FluidTank(inputCaps.getInt(i)).readFromNBT(compound);
			addFluidTank(tank.getFluid(), tank.getCapacity(), true);
		}

		ListNBT outputCaps = nbt.getList("outputCaps", 10);
		ListNBT outputList = nbt.getList("outputList", 10);

		for (int i = 0; i < outputList.size(); i++) {
			CompoundNBT compound = (CompoundNBT) outputList.get(i);
			FluidTank tank = new FluidTank(outputCaps.getInt(i)).readFromNBT(compound);
			addFluidTank(tank.getFluid(), tank.getCapacity(), false);
		}
	}

	@Override
	public void saveToNBT(CompoundNBT nbt) {
		// Need tank capacities for JSON-based loading
		ListNBT inputCaps = new ListNBT();
		for (FluidTank tank : inputFluids.values()) {
			CompoundNBT cap = new CompoundNBT();
			cap.putInt("cap", tank.getCapacity());
			inputCaps.add(cap);
		}
		nbt.put("inputCaps", inputCaps);

		ListNBT inputList = new ListNBT();
		for (FluidTank tank : inputFluids.values()) {
			CompoundNBT tag = new CompoundNBT();
			tag.putString("FluidName", tank.getFluid().getRawFluid().getRegistryName().toString());
			tag.putInt("Amount", tank.getFluid().getAmount());

			if (tank.getFluid().getTag() != null) {
				tag.put("Tag", tank.getFluid().getTag());
			}
			inputList.add(tag);
		}
		nbt.put("inputList", inputList);

		ListNBT outputCaps = new ListNBT();
		for (FluidTank tank : outputFluids.values()) {
			CompoundNBT cap = new CompoundNBT();
			cap.putInt("cap", tank.getCapacity());
			outputCaps.add(cap);
		}
		nbt.put("outputCaps", outputCaps);

		ListNBT outputList = new ListNBT();
		for (FluidTank tank : outputFluids.values()) {
			CompoundNBT tag = new CompoundNBT();
			tag.putString("FluidName", tank.getFluid().getRawFluid().getRegistryName().toString());
			tag.putInt("Amount", tank.getFluid().getAmount());

			if (tank.getFluid().getTag() != null) {
				tag.put("Tag", tank.getFluid().getTag());
			}
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

	public ComponentFluidHandler addFluidTank(Fluid fluid, int capacity, boolean isInput) {
		// ensures no duplicate entries like the fluid tanks
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

	//private method for NBT to use; avoids overwriting fluids with empty values if they're JSON-based
	private void addFluidTank(FluidStack stack, int capacity, boolean isInput) {
		if (isInput) {
			Fluid fluid = stack.getFluid();
			if (!getValidInputFluids().contains(fluid)) {
				inputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
			}
			inputFluids.get(fluid).setFluid(stack);
			this.inputFromNBT = true;
		} else {
			Fluid fluid = stack.getFluid();
			if (!getValidOutputFluids().contains(fluid)) {
				outputFluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
			}
			outputFluids.get(fluid).setFluid(stack);
			this.outputFromNBT = true;
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
		} else {
			return ((FluidTank) outputFluids.values().toArray()[tank]).getFluid();
		}
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
		} else {
			return outputFluids.get(fluid);
		}
	}

	public ComponentFluidHandler setFluidInTank(FluidStack stack, int tank, boolean isInput) {
		if (isInput) {
			((FluidTank) inputFluids.values().toArray()[tank]).setFluid(stack);
		} else {
			((FluidTank) outputFluids.values().toArray()[tank]).setFluid(stack);
		}
		return this;
	}

	@Override
	public int getTankCapacity(int tank) {
		return ((FluidTank) getCombinedTanks().toArray()[tank]).getCapacity();
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return ((FluidTank) getCombinedTanks().toArray()[tank]).isFluidValid(stack);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		Direction relative = UtilitiesTiles.getRelativeSide(holder.hasComponent(ComponentType.Direction)
				? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
				: Direction.UP, lastDirection);
		boolean canFill = inputDirections.contains(lastDirection)
				|| holder.hasComponent(ComponentType.Direction) && relativeInputDirections.contains(relative);
		return canFill ? inputFluids.get(resource.getFluid()).fill(resource, action) : 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		Direction relative = UtilitiesTiles.getRelativeSide(holder.hasComponent(ComponentType.Direction)
				? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
				: Direction.UP, lastDirection);
		boolean canDrain = outputDirections.contains(lastDirection)
				|| holder.hasComponent(ComponentType.Direction) && relativeOutputDirections.contains(relative);
		return canDrain ? outputFluids.get(resource.getFluid()).drain(resource, action) : FluidStack.EMPTY;
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
		Direction dir = holder.hasComponent(ComponentType.Direction)
				? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
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
		Collection<FluidTank> fluids = Stream.concat(inputFluids.values().stream(), outputFluids.values().stream())
				.collect(Collectors.toList());
		return fluids;
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

	public <T extends ElectrodynamicsRecipe> ComponentFluidHandler setAddFluidsValues(Class<T> recipeClass,
			IRecipeType<?> recipeType, int capacity, boolean hasInput, boolean hasOutput) {
		this.recipeClass = recipeClass;
		this.recipeType = recipeType;
		this.tankCapacity = capacity;
		this.hasInput = hasInput;
		this.hasOutput = hasOutput;
		return this;
	}

	// Now it has some teeth
	public void addFluids() {
		if (this.recipeType != null) {
			Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(this.recipeType, this.holder.getWorld());
			for (IRecipe<?> iRecipe : recipes) {
				if (this.hasInput && !inputFromNBT) {
					List<Ingredient> ingredients = this.recipeClass.cast(iRecipe).getIngredients();
					Fluid fluid = ((FluidIngredient) this.recipeClass.cast(iRecipe).getIngredients()
							.get(0 + ingredients.size() - 1)).getFluidStack().getFluid();
					addFluidTank(fluid, this.tankCapacity, true);
				}
				if (this.hasOutput && !outputFromNBT) {
					IFluidRecipe fRecipe = (IFluidRecipe) this.recipeClass.cast(iRecipe);
					Fluid fluid = fRecipe.getFluidRecipeOutput().getFluid();
					addFluidTank(fluid, this.tankCapacity, false);
				}
			}
		}
	}
}
