package electrodynamics.prefab.tile.components.type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.CapabilityInputType;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentGasHandler;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Extension of the PropertyGasTank making it usable as a ComponentGasHandler
 * 
 * This ComponentGasHandler has only one tank with programmable inputs and outputs where as ComponentGasHandlerMulti has distinct input and output tanks
 * 
 * @author skip999
 *
 */
public class ComponentGasHandlerSimple extends PropertyGasTank implements IComponentGasHandler {

	@Nullable
	public Direction[] inputDirections;
	@Nullable
	public Direction[] outputDirections;

	private boolean isSided = false;

	@Nullable
	private TagKey<Gas>[] validGasTags;
	@Nullable
	private Gas[] validGases;

	private HashSet<Gas> validatorGases = new HashSet<>();

	private LazyOptional<IGasHandler>[] sidedOptionals = genArr(); // Down Up North South West East

	private static LazyOptional<IGasHandler>[] genArr() {

		LazyOptional<IGasHandler>[] arr = new LazyOptional[6];

		for (int i = 0; i < 6; i++) {
			arr[i] = LazyOptional.empty();
		}

		return arr;

	}

	private LazyOptional<IGasHandler> sidelessOptional = LazyOptional.empty();
	private LazyOptional<IGasHandler> inputOptional = LazyOptional.empty();
	private LazyOptional<IGasHandler> outputOptional = LazyOptional.empty();

	public ComponentGasHandlerSimple(GenericTile holder, String key, double capacity, double maxTemperature, int maxPressure) {
		super(holder, key, capacity, maxTemperature, maxPressure);
	}

	public ComponentGasHandlerSimple(GenericTile holder, String key, double capacity, double maxTemperature, int maxPressure, Predicate<GasStack> isGasValid) {
		super(holder, key, capacity, maxTemperature, maxPressure, isGasValid);
	}

	protected ComponentGasHandlerSimple(PropertyGasTank other) {
		super(other);
	}

	public ComponentGasHandlerSimple setInputDirections(Direction... directions) {
		isSided = true;
		inputDirections = directions;
		return this;
	}

	public ComponentGasHandlerSimple setOutputDirections(Direction... directions) {
		isSided = true;
		outputDirections = directions;
		return this;
	}

	public ComponentGasHandlerSimple universalInput() {
		inputDirections = Direction.values();
		return this;
	}

	public ComponentGasHandlerSimple universalOutput() {
		outputDirections = Direction.values();
		return this;
	}

	@Override
	public ComponentGasHandlerSimple setValidator(Predicate<GasStack> predicate) {
		return (ComponentGasHandlerSimple) super.setValidator(predicate);
	}

	@Override
	public ComponentGasHandlerSimple setOnGasCondensed(BiConsumer<GasTank, GenericTile> onGasCondensed) {
		return (ComponentGasHandlerSimple) super.setOnGasCondensed(onGasCondensed);
	}

	public ComponentGasHandlerSimple setValidFluids(Gas... fluids) {
		validGases = fluids;
		return this;
	}

	public ComponentGasHandlerSimple setValidFluidTags(TagKey<Gas>... fluids) {
		validGasTags = fluids;
		return this;
	}

	@Override
	public PropertyGasTank[] getInputTanks() {
		return asArray();
	}

	@Override
	public PropertyGasTank[] getOutputTanks() {
		return asArray();
	}

	@Override
	public IComponentType getType() {
		return IComponentType.GasHandler;
	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	@Override
	public GenericTile getHolder() {
		return holder;
	}

	@Override
	public void refreshIfUpdate(BlockState oldState, BlockState newState) {
		if (isSided && oldState.hasProperty(GenericEntityBlock.FACING) && newState.hasProperty(GenericEntityBlock.FACING) && oldState.getValue(GenericEntityBlock.FACING) != newState.getValue(GenericEntityBlock.FACING)) {
			defineOptionals(newState.getValue(GenericEntityBlock.FACING));
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side, CapabilityInputType inputType) {
		if (capability != ElectrodynamicsCapabilities.GAS_HANDLER) {
			return LazyOptional.empty();
		}
		if (!isSided) {
			return sidelessOptional.cast();
		}

		if (side == null) {
			return LazyOptional.empty();
		}

		return sidedOptionals[side.ordinal()].cast();
	}

	@Override
	public void refresh() {

		defineOptionals(holder.getFacing());

	}

	private void defineOptionals(Direction facing) {

		sidedOptionals = new LazyOptional[6];
		sidelessOptional = null;

		if (isSided) {

			if (inputOptional != null) {
				inputOptional.invalidate();
			}
			if (outputOptional != null) {
				outputOptional.invalidate();
			}

			Arrays.fill(sidedOptionals, LazyOptional.empty());

			// Input

			if (inputDirections != null) {
				inputOptional = LazyOptional.of(() -> new InputTank(this));

				for (Direction dir : inputDirections) {
					sidedOptionals[BlockEntityUtils.getRelativeSide(facing, dir).ordinal()] = inputOptional;
				}
			}

			if (outputDirections != null) {
				outputOptional = LazyOptional.of(() -> new OutputTank(this));

				for (Direction dir : outputDirections) {
					sidedOptionals[BlockEntityUtils.getRelativeSide(facing, dir).ordinal()] = outputOptional;
				}
			}

		} else {

			if (sidelessOptional != null) {
				sidelessOptional.invalidate();
			}

			sidelessOptional = LazyOptional.of(() -> this);

		}

	}

	@Override
	public void onLoad() {
		IComponentGasHandler.super.onLoad();
		if (validGases != null) {
			for (Gas gas : validGases) {
				validatorGases.add(gas);
			}
		}
		if (validGasTags != null) {
			for (TagKey<Gas> tag : validGasTags) {
				for (Gas gas : ElectrodynamicsRegistries.gasRegistry().tags().getTag(tag).stream().toList()) {
					validatorGases.add(gas);
				}
			}
		}
		if (!validatorGases.isEmpty()) {
			isGasValid = gasStack -> validatorGases.contains(gasStack.getGas());
		}
	}

	private class InputTank extends ComponentGasHandlerSimple {

		public InputTank(ComponentGasHandlerSimple property) {
			super(property);
		}

		@Override
		public GasStack drain(double amount, GasAction action) {
			return GasStack.EMPTY;
		}

		@Override
		public GasStack drain(GasStack resource, GasAction action) {
			return GasStack.EMPTY;
		}

	}

	private class OutputTank extends ComponentGasHandlerSimple {

		public OutputTank(ComponentGasHandlerSimple property) {
			super(property);
		}

		@Override
		public double fill(GasStack resource, GasAction action) {
			return 0;
		}

	}

}
