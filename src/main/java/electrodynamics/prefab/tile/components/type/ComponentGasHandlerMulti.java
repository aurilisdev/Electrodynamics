package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.GasIngredient;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentGasHandler;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ComponentGasHandlerMulti implements IComponentGasHandler {

	private GenericTile holder;

	@Nullable
	public Direction[] inputDirections;
	@Nullable
	public Direction[] outputDirections;

	private PropertyGasTank[] inputTanks;
	private PropertyGasTank[] outputTanks;

	@Nullable
	private RecipeType<? extends AbstractMaterialRecipe> recipeType;

	@Nullable
	private TagKey<Gas>[] validInputGasTags;
	@Nullable
	private Gas[] validInputGases;
	private HashSet<Gas> inputValidatorGases = new HashSet<>();

	@Nullable
	private TagKey<Gas>[] validOutputGasTags;
	@Nullable
	private Gas[] validOutputGases;
	private HashSet<Gas> outputValidatorGases = new HashSet<>();

	public ComponentGasHandlerMulti(GenericTile holder) {
		this.holder = holder;
	}

	public ComponentGasHandlerMulti setInputTanks(int count, double[] capacity, double[] maxTemperature, int[] maxPressure) {
		inputTanks = new PropertyGasTank[count];
		if (capacity.length < count) {
			throw new UnsupportedOperationException("The number of capacities does not match the number of input tanks");
		}
		if (maxPressure.length < count) {
			throw new UnsupportedOperationException("The number of max temperatures does not match the number of input tanks");
		}
		if (maxTemperature.length < count) {
			throw new UnsupportedOperationException("The number of max pressures does not match the number of input tanks");
		}
		for (int i = 0; i < count; i++) {
			inputTanks[i] = new PropertyGasTank(holder, "input" + i, capacity[i], maxTemperature[i], maxPressure[i]);
		}
		return this;
	}

	public ComponentGasHandlerMulti setOutputTanks(int count, double[] capacity, double[] maxTemperature, int[] maxPressure) {
		outputTanks = new PropertyGasTank[count];
		if (capacity.length < count) {
			throw new UnsupportedOperationException("The number of capacities does not match the number of output tanks");
		}
		if (maxPressure.length < count) {
			throw new UnsupportedOperationException("The number of max temperatures does not match the number of output tanks");
		}
		if (maxTemperature.length < count) {
			throw new UnsupportedOperationException("The number of max pressures does not match the number of output tanks");
		}
		for (int i = 0; i < count; i++) {
			outputTanks[i] = new PropertyGasTank(holder, "output" + i, capacity[i], maxTemperature[i], maxPressure[i]);
		}
		return this;
	}

	public ComponentGasHandlerMulti setTanks(int inputCount, double[] inputCapacity, double[] inputMaxTemperature, int[] inputMaxPressure, int outputCount, double[] outputCapacity, double[] outputMaxTemperature, int[] outputMaxPressure) {
		return setInputTanks(inputCount, inputCapacity, inputMaxTemperature, inputMaxPressure).setOutputTanks(outputCount, outputCapacity, outputMaxTemperature, outputMaxPressure);
	}

	public ComponentGasHandlerMulti setInputFluids(Gas... gases) {
		validInputGases = gases;
		return this;
	}

	public ComponentGasHandlerMulti setInputFluidTags(TagKey<Gas>... gases) {
		validInputGasTags = gases;
		return this;
	}

	public ComponentGasHandlerMulti setOutputFluids(Gas... gases) {
		validOutputGases = gases;
		return this;
	}

	public ComponentGasHandlerMulti setOutputFluidTags(TagKey<Gas>... gases) {
		validOutputGasTags = gases;
		return this;
	}

	public ComponentGasHandlerMulti setInputDirections(Direction... directions) {
		inputDirections = directions;
		return this;
	}

	public ComponentGasHandlerMulti setOutputDirections(Direction... directions) {
		outputDirections = directions;
		return this;
	}

	public ComponentGasHandlerMulti universalInput() {
		inputDirections = Direction.values();
		return this;
	}

	public ComponentGasHandlerMulti universalOutput() {
		outputDirections = Direction.values();
		return this;
	}

	public ComponentGasHandlerMulti setRecipeType(RecipeType<? extends AbstractMaterialRecipe> recipeType) {
		this.recipeType = recipeType;
		return this;
	}

	public int tankCount(boolean input) {
		if (input) {
			return inputTanks == null ? 0 : inputTanks.length;
		}
		return outputTanks == null ? 0 : outputTanks.length;
	}

	public GasStack getGasInTank(int tank, boolean input) {
		if (input) {
			return inputTanks[tank].getGas();
		}
		return outputTanks[tank].getGas();
	}

	@Nullable
	public PropertyGasTank getTankFromFluid(Gas gas, boolean isInput) {
		if (isInput) {
			for (PropertyGasTank tank : inputTanks) {
				if (tank.getGas().getGas().equals(gas)) {
					return tank;
				}
			}
			for (PropertyGasTank tank : inputTanks) {
				if (tank.isEmpty()) {
					return tank;
				}
			}
		}
		for (PropertyGasTank tank : outputTanks) {
			if ((tank.getGas().getGas().equals(gas))) {
				return tank;
			}
		}
		for (PropertyGasTank tank : outputTanks) {
			if (tank.isEmpty()) {
				return tank;
			}
		}

		return null;
	}

	public double getTankCapacity(int tank, boolean input) {
		if (input) {
			return inputTanks[tank].getCapacity();
		}
		return outputTanks[tank].getCapacity();
	}

	public boolean isGasValid(int tank, @NotNull GasStack stack, boolean input) {
		if (input) {
			return inputTanks[tank].isGasValid(stack);
		}
		return outputTanks[tank].isGasValid(stack);
	}

	public double fill(int tank, GasStack resource, GasAction action, boolean input) {
		if (input) {
			return inputTanks[tank].fill(resource, action);
		}
		return outputTanks[tank].fill(resource, action);
	}

	public @NotNull GasStack drain(int tank, GasStack resource, GasAction action, boolean input) {
		if (input) {
			return inputTanks[tank].drain(resource, action);
		}
		return outputTanks[tank].drain(resource, action);
	}

	public @NotNull GasStack drain(int tank, double maxDrain, GasAction action, boolean input) {
		if (input) {
			return inputTanks[tank].drain(maxDrain, action);
		}
		return outputTanks[tank].drain(maxDrain, action);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction side) {
		return capability == ElectrodynamicsCapabilities.GAS_HANDLER;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (!hasCapability(capability, side)) {
			return LazyOptional.empty();
		}
		if (hasInputDir(side)) {
			return LazyOptional.<IGasHandler>of(() -> new InputTankDispatcher(inputTanks)).cast();
		} else if (hasOutputDir(side)) {
			return LazyOptional.<IGasHandler>of(() -> new OutputTankDispatcher(outputTanks)).cast();
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

			int inTankCount = 0;
			int outTankCount = 0;
			List<Gas> inputGasHolder = new ArrayList<>();
			List<Gas> outputGasHohlder = new ArrayList<>();

			for (ElectrodynamicsRecipe iRecipe : recipes) {
				AbstractMaterialRecipe recipe = (AbstractMaterialRecipe) iRecipe;
				if (inputTanks != null) {
					int ingCount = recipe.getGasIngredients().size();
					if (ingCount > inTankCount) {
						inTankCount = ingCount;
					}
					for (GasIngredient ing : recipe.getGasIngredients()) {
						ing.getMatchingGases().forEach(h -> inputGasHolder.add(h.getGas()));
					}
				}

				int length = 0;
				if (outputTanks != null) {
					outputGasHohlder.add(recipe.getGasRecipeOutput().getGas());
					length++;
				}
				if (recipe.hasGasBiproducts()) {
					for (GasStack stack : recipe.getFullGasBiStacks()) {
						outputGasHohlder.add(stack.getGas());
					}
					length += recipe.getGasBiproductCount();
				}
				if (length > outTankCount) {
					outTankCount = length;
				}
			}
			inputValidatorGases.addAll(inputGasHolder);
			outputValidatorGases.addAll(outputGasHohlder);
		} else {
			if (validInputGases != null) {
				for (Gas gas : validInputGases) {
					inputValidatorGases.add(gas);
				}
			}
			if (validInputGasTags != null) {
				for (TagKey<Gas> tag : validInputGasTags) {
					for (Gas fluid : ElectrodynamicsRegistries.gasRegistry().tags().getTag(tag).stream().toList()) {
						inputValidatorGases.add(fluid);
					}
				}
			}
			if (validOutputGases != null) {
				for (Gas fluid : validOutputGases) {
					outputValidatorGases.add(fluid);
				}
			}
			if (validOutputGasTags != null) {
				for (TagKey<Gas> tag : validOutputGasTags) {
					for (Gas fluid : ElectrodynamicsRegistries.gasRegistry().tags().getTag(tag).stream().toList()) {
						outputValidatorGases.add(fluid);
					}
				}
			}
		}
		if (!inputValidatorGases.isEmpty()) {
			for (PropertyGasTank tank : inputTanks) {
				tank.setValidator(gasStack -> inputValidatorGases.contains(gasStack.getGas()));
			}
		}
		if (!outputValidatorGases.isEmpty()) {
			for (PropertyGasTank tank : outputTanks) {
				tank.setValidator(gasStack -> outputValidatorGases.contains(gasStack.getGas()));
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GasHandler;
	}

	@Override
	public PropertyGasTank[] getInputTanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyGasTank[] getOUtputTanks() {
		// TODO Auto-generated method stub
		return null;
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

	private class InputTankDispatcher implements IGasHandler {

		private PropertyGasTank[] tanks;

		public InputTankDispatcher(PropertyGasTank[] tanks) {
			this.tanks = tanks;
		}

		@Override
		public int getTanks() {
			return tanks.length;
		}

		@Override
		public GasStack getGasInTank(int tank) {
			return tanks[tank].getGas();
		}

		@Override
		public double getTankCapacity(int tank) {
			return tanks[tank].getCapacity();
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			return tanks[tank].getMaxTemperature();
		}

		@Override
		public int getTankMaxPressure(int tank) {
			return tanks[tank].getMaxPressure();
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
			return tanks[tank].isGasValid(gas);
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			return tanks[tank].fill(gas, action);
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			return GasStack.EMPTY;
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			return GasStack.EMPTY;
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			return tanks[tank].heat(deltaTemperature, action);
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			return tanks[tank].bringPressureTo(atm, action);
		}

	}

	private class OutputTankDispatcher implements IGasHandler {

		private PropertyGasTank[] tanks;

		public OutputTankDispatcher(PropertyGasTank[] tanks) {
			this.tanks = tanks;
		}

		@Override
		public int getTanks() {
			return tanks.length;
		}

		@Override
		public GasStack getGasInTank(int tank) {
			return tanks[tank].getGas();
		}

		@Override
		public double getTankCapacity(int tank) {
			return tanks[tank].getCapacity();
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			return tanks[tank].getMaxTemperature();
		}

		@Override
		public int getTankMaxPressure(int tank) {
			return tanks[tank].getMaxPressure();
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
			return false;
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			return 0;
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			return tanks[tank].drain(tank, action);
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			return tanks[tank].drain(maxFill, action);
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			return tanks[tank].heat(deltaTemperature, action);
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			return tanks[tank].bringPressureTo(atm, action);
		}

	}

}
