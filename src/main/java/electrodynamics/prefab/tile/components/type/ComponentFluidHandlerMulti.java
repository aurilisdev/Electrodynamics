package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import electrodynamics.api.fluid.PropertyFluidTank;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.CapabilityInputType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentFluidHandler;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * This class is separate from ComponentFluidHandlerSimple as it has segregated input and output tanks. These tanks are then
 * dispatched when the Capability is requested. The only way to fill an output tank or drain an input tank is through internal
 * tile logic.
 * 
 * This class also allows for RecipeTypes to be used as filters, as Recipes inherently have segregated inputs and outputs.
 * 
 * @author skip999
 *
 */
public class ComponentFluidHandlerMulti implements IComponentFluidHandler {

	private GenericTile holder;

	@Nullable
	public Direction[] inputDirections;
	@Nullable
	public Direction[] outputDirections;

	private PropertyFluidTank[] inputTanks = new PropertyFluidTank[0];
	private PropertyFluidTank[] outputTanks = new PropertyFluidTank[0];

	@Nullable
	private RecipeType<? extends AbstractMaterialRecipe> recipeType;

	@Nullable
	private TagKey<Fluid>[] validInputFluidTags;
	@Nullable
	private Fluid[] validInputFluids;
	private HashSet<Fluid> inputValidatorFluids = new HashSet<>();

	@Nullable
	private TagKey<Fluid>[] validOutputFluidTags;
	@Nullable
	private Fluid[] validOutputFluids;
	private HashSet<Fluid> outputValidatorFluids = new HashSet<>();

	public ComponentFluidHandlerMulti(GenericTile holder) {
		this.holder = holder;
	}

	public ComponentFluidHandlerMulti setInputTanks(int count, int... capacity) {
		inputTanks = new PropertyFluidTank[count];
		if (capacity.length < count) {
			throw new UnsupportedOperationException("The number of capacities does not match the number of input tanks");
		}
		for (int i = 0; i < count; i++) {
			inputTanks[i] = new PropertyFluidTank(capacity[i], holder, "input" + i);
		}
		return this;
	}

	public ComponentFluidHandlerMulti setOutputTanks(int count, int... capacity) {
		outputTanks = new PropertyFluidTank[count];
		if (capacity.length < count) {
			throw new UnsupportedOperationException("The number of capacities does not match the number of output tanks");
		}
		for (int i = 0; i < count; i++) {
			outputTanks[i] = new PropertyFluidTank(capacity[i], holder, "output" + i);
		}
		return this;
	}

	public ComponentFluidHandlerMulti setTanks(int inputCount, int outputCount, int[] inputCapacity, int[] outputCapacity) {
		return setInputTanks(inputCount, inputCapacity).setOutputTanks(outputCount, outputCapacity);
	}

	public ComponentFluidHandlerMulti setInputFluids(Fluid... fluids) {
		validInputFluids = fluids;
		return this;
	}

	public ComponentFluidHandlerMulti setInputFluidTags(TagKey<Fluid>... fluids) {
		validInputFluidTags = fluids;
		return this;
	}

	public ComponentFluidHandlerMulti setOutputFluids(Fluid... fluids) {
		validOutputFluids = fluids;
		return this;
	}

	public ComponentFluidHandlerMulti setOutputFluidTags(TagKey<Fluid>... fluids) {
		validOutputFluidTags = fluids;
		return this;
	}

	public ComponentFluidHandlerMulti setInputDirections(Direction... directions) {
		inputDirections = directions;
		return this;
	}

	public ComponentFluidHandlerMulti setOutputDirections(Direction... directions) {
		outputDirections = directions;
		return this;
	}

	public ComponentFluidHandlerMulti universalInput() {
		inputDirections = Direction.values();
		return this;
	}

	public ComponentFluidHandlerMulti universalOutput() {
		outputDirections = Direction.values();
		return this;
	}

	public ComponentFluidHandlerMulti setRecipeType(RecipeType<? extends AbstractMaterialRecipe> recipeType) {
		this.recipeType = recipeType;
		return this;
	}

	public int tankCount(boolean input) {
		if (input) {
			return inputTanks == null ? 0 : inputTanks.length;
		}
		return outputTanks == null ? 0 : outputTanks.length;
	}

	public FluidStack getFluidInTank(int tank, boolean input) {
		if (input) {
			return inputTanks[tank].getFluid();
		}
		return outputTanks[tank].getFluid();
	}

	@Nullable
	public PropertyFluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
		if (isInput) {
			for (PropertyFluidTank tank : inputTanks) {
				if (tank.getFluid().getFluid().isSame(fluid)) {
					return tank;
				}
			}
			for (PropertyFluidTank tank : inputTanks) {
				if (tank.isEmpty()) {
					return tank;
				}
			}
		}
		for (PropertyFluidTank tank : outputTanks) {
			if (tank.getFluid().getFluid().isSame(fluid)) {
				return tank;
			}
		}
		for (PropertyFluidTank tank : outputTanks) {
			if (tank.isEmpty()) {
				return tank;
			}
		}

		return null;
	}

	public int getTankCapacity(int tank, boolean input) {
		if (input) {
			return inputTanks[tank].getCapacity();
		}
		return outputTanks[tank].getCapacity();
	}

	public boolean isFluidValid(int tank, @NotNull FluidStack stack, boolean input) {
		if (input) {
			return inputTanks[tank].isFluidValid(stack);
		}
		return outputTanks[tank].isFluidValid(stack);
	}

	public int fill(int tank, FluidStack resource, FluidAction action, boolean input) {
		if (input) {
			return inputTanks[tank].fill(resource, action);
		}
		return outputTanks[tank].fill(resource, action);
	}

	public @NotNull FluidStack drain(int tank, FluidStack resource, FluidAction action, boolean input) {
		if (input) {
			return inputTanks[tank].drain(resource, action);
		}
		return outputTanks[tank].drain(resource, action);
	}

	public @NotNull FluidStack drain(int tank, int maxDrain, FluidAction action, boolean input) {
		if (input) {
			return inputTanks[tank].drain(maxDrain, action);
		}
		return outputTanks[tank].drain(maxDrain, action);
	}

	@Override
	public ComponentType getType() {
		return ComponentType.FluidHandler;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction side, CapabilityInputType inputType) {
		return capability == ForgeCapabilities.FLUID_HANDLER;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side, CapabilityInputType inputType) {
		if (!hasCapability(capability, side, inputType)) {
			return LazyOptional.empty();
		}
		if (hasInputDir(side)) {
			return LazyOptional.<IFluidHandler>of(() -> new InputTankDispatcher(inputTanks)).cast();
		} else if (hasOutputDir(side)) {
			return LazyOptional.<IFluidHandler>of(() -> new OutputTankDispatcher(outputTanks)).cast();
		} else {
			return LazyOptional.empty();
		}
	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	@Override
	public void onLoad() {
		if (recipeType != null) {
			List<ElectrodynamicsRecipe> recipes = ElectrodynamicsRecipe.findRecipesbyType(recipeType, holder.getLevel());

			List<Fluid> inputFluidHolder = new ArrayList<>();
			List<Fluid> outputFluidHohlder = new ArrayList<>();
			int maxFluidInput = 0;
			int maxFluidOutput = 0;
			int maxFluidBiproduct = 0;

			for (ElectrodynamicsRecipe iRecipe : recipes) {
				AbstractMaterialRecipe recipe = (AbstractMaterialRecipe) iRecipe;
				if (inputTanks != null) {
					for (FluidIngredient ing : recipe.getFluidIngredients()) {
						ing.getMatchingFluids().forEach(h -> inputFluidHolder.add(h.getFluid()));
						if (ing.getFluidStack().getAmount() > maxFluidInput) {
							maxFluidInput = ing.getFluidStack().getAmount();
						}
					}
				}

				if (outputTanks != null) {
					outputFluidHohlder.add(recipe.getFluidRecipeOutput().getFluid());
					if (recipe.getFluidRecipeOutput().getAmount() > maxFluidOutput) {
						maxFluidOutput = recipe.getFluidRecipeOutput().getAmount();
					}

					if (recipe.hasFluidBiproducts()) {
						for (FluidStack stack : recipe.getFullFluidBiStacks()) {
							outputFluidHohlder.add(stack.getFluid());
							if (stack.getAmount() > maxFluidBiproduct) {
								maxFluidBiproduct = stack.getAmount();
							}
						}
					}
				}
			}
			inputValidatorFluids.addAll(inputFluidHolder);
			outputValidatorFluids.addAll(outputFluidHohlder);
			if (maxFluidInput > 0) {

				maxFluidInput = (maxFluidInput / TANK_MULTIPLER) * TANK_MULTIPLER + TANK_MULTIPLER;

				for (PropertyFluidTank tank : inputTanks) {
					if (tank.getCapacity() < maxFluidInput) {
						tank.setCapacity(maxFluidInput);
					}
				}
			}
			int offset = 0;
			if (maxFluidOutput > 0) {

				maxFluidOutput = (maxFluidOutput / TANK_MULTIPLER) * TANK_MULTIPLER + TANK_MULTIPLER;

				if (outputTanks[0].getCapacity() < maxFluidOutput) {
					outputTanks[0].setCapacity(maxFluidOutput);
				}

				offset = 1;
			}
			if (maxFluidBiproduct > 0) {

				maxFluidBiproduct = (maxFluidBiproduct / TANK_MULTIPLER) * TANK_MULTIPLER + TANK_MULTIPLER;

				for (int i = 0; i < outputTanks.length - offset; i++) {

					if (outputTanks[i + offset].getCapacity() < maxFluidBiproduct) {
						outputTanks[i + offset].setCapacity(maxFluidBiproduct);
					}

				}
			}

		} else {
			if (validInputFluids != null) {
				for (Fluid fluid : validInputFluids) {
					inputValidatorFluids.add(fluid);
				}
			}
			if (validInputFluidTags != null) {
				for (TagKey<Fluid> tag : validInputFluidTags) {
					for (Fluid fluid : ForgeRegistries.FLUIDS.tags().getTag(tag).stream().toList()) {
						inputValidatorFluids.add(fluid);
					}
				}
			}
			if (validOutputFluids != null) {
				for (Fluid fluid : validOutputFluids) {
					outputValidatorFluids.add(fluid);
				}
			}
			if (validOutputFluidTags != null) {
				for (TagKey<Fluid> tag : validOutputFluidTags) {
					for (Fluid fluid : ForgeRegistries.FLUIDS.tags().getTag(tag).stream().toList()) {
						outputValidatorFluids.add(fluid);
					}
				}
			}
		}
		if (!inputValidatorFluids.isEmpty()) {
			for (PropertyFluidTank tank : inputTanks) {
				tank.setValidator(fluidStack -> inputValidatorFluids.contains(fluidStack.getFluid()));
			}
		}
		if (!outputValidatorFluids.isEmpty()) {
			for (PropertyFluidTank tank : outputTanks) {
				tank.setValidator(fluidStack -> outputValidatorFluids.contains(fluidStack.getFluid()));
			}
		}
	}

	@Override
	public PropertyFluidTank[] getInputTanks() {
		return inputTanks;
	}

	@Override
	public PropertyFluidTank[] getOutputTanks() {
		return outputTanks;
	}

	private boolean hasOutputDir(Direction dir) {
		if (outputDirections == null) {
			return false;
		}
		Direction facing = holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		return ArrayUtils.contains(outputDirections, BlockEntityUtils.getRelativeSide(facing, dir));
	}

	private boolean hasInputDir(Direction dir) {
		if (inputDirections == null) {
			return false;
		}
		Direction facing = holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		return ArrayUtils.contains(inputDirections, BlockEntityUtils.getRelativeSide(facing, dir));
	}

	private class InputTankDispatcher implements IFluidHandler {

		private PropertyFluidTank[] tanks;

		public InputTankDispatcher(PropertyFluidTank[] tanks) {
			this.tanks = tanks;
		}

		@Override
		public int getTanks() {
			return tanks.length;
		}

		@Override
		public @NotNull FluidStack getFluidInTank(int tank) {
			if (tank >= getTanks()) {
				return FluidStack.EMPTY;
			}
			return tanks[tank].getFluid();
		}

		@Override
		public int getTankCapacity(int tank) {
			if (tank >= getTanks()) {
				return 0;
			}
			return tanks[tank].getCapacity();
		}

		@Override
		public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
			if (tank >= getTanks()) {
				return false;
			}
			return tanks[tank].isFluidValid(stack);
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			for (PropertyFluidTank tank : tanks) {
				if (tank.getFluid().getFluid().isSame(resource.getFluid())) {
					return tank.fill(resource, action);
				}
			}
			for (PropertyFluidTank tank : tanks) {
				if (tank.isEmpty()) {
					return tank.fill(resource, action);
				}
			}
			return 0;
		}

		@Override
		public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
			return FluidStack.EMPTY;
		}

		@Override
		public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
			return FluidStack.EMPTY;
		}

	}

	private class OutputTankDispatcher implements IFluidHandler {

		private PropertyFluidTank[] tanks;

		public OutputTankDispatcher(PropertyFluidTank[] tanks) {
			this.tanks = tanks;
		}

		@Override
		public int getTanks() {
			return tanks.length;
		}

		@Override
		public @NotNull FluidStack getFluidInTank(int tank) {
			if (tank >= getTanks()) {
				return FluidStack.EMPTY;
			}
			return tanks[tank].getFluid();
		}

		@Override
		public int getTankCapacity(int tank) {
			if (tank >= getTanks()) {
				return 0;
			}
			return tanks[tank].getCapacity();
		}

		@Override
		public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
			return false;
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			return 0;
		}

		@Override
		public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
			for (PropertyFluidTank tank : tanks) {
				if (tank.getFluid().getFluid().isSame(resource.getFluid())) {
					return tank.drain(resource, action);
				}
			}
			return FluidStack.EMPTY;
		}

		@Override
		public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
			return FluidStack.EMPTY;
		}

	}

	/**
	 * A modified variant of the ComponentFluidHandlerMulti that allows for inputs and outputs to share the same side
	 * 
	 * Note, the calling tile is responsible for providing the non-null CapabilityInputType
	 * 
	 * @author skip999
	 *
	 */
	public static class ComponentFluidHandlerMultiBiDirec extends ComponentFluidHandlerMulti {

		public ComponentFluidHandlerMultiBiDirec(GenericTile holder) {
			super(holder);
		}

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side, CapabilityInputType inputType) {
			if (!hasCapability(capability, side, inputType)) {
				return LazyOptional.empty();
			}
			if (super.hasInputDir(side) && inputType == CapabilityInputType.INPUT) {
				return LazyOptional.<IFluidHandler>of(() -> new InputTankDispatcher(super.inputTanks)).cast();
			} else if (super.hasOutputDir(side) && inputType == CapabilityInputType.OUTPUT) {
				return LazyOptional.<IFluidHandler>of(() -> new OutputTankDispatcher(super.outputTanks)).cast();
			} else {
				return super.getCapability(capability, side, inputType);
			}
		}

	}

}
