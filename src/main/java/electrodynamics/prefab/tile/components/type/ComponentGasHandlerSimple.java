package electrodynamics.prefab.tile.components.type;

import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentGasHandler;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Extension of the PropertyGasTank making it usable as a ComponentGasHandler
 * 
 * This ComponentGasHandler has only one tank with programmable inputs and
 * outputs where as ComponentGasHandlerMulti has distinct input and output tanks
 * 
 * @author skip999
 *
 */
public class ComponentGasHandlerSimple extends PropertyGasTank implements IComponentGasHandler {

	@Nullable
	public Direction[] inputDirections;
	@Nullable
	public Direction[] outputDirections;
	@Nullable
	private TagKey<Gas>[] validGasTags;
	@Nullable
	private Gas[] validGases;

	private HashSet<Gas> validatorGases = new HashSet<>();

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
		inputDirections = directions;
		return this;
	}

	public ComponentGasHandlerSimple setOutputDirections(Direction... directions) {
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
	public PropertyGasTank[] getOUtputTanks() {
		return asArray();
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GasHandler;
	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
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
		if (inputDirections == null && outputDirections == null) {
			return LazyOptional.<IGasHandler>of(() -> this).cast();
		}
		if (hasInputDir(side)) {
			return LazyOptional.<IGasHandler>of(() -> new InputTank(this)).cast();
		} else if (hasOutputDir(side)) {
			return LazyOptional.<IGasHandler>of(() -> new OutputTank(this)).cast();
		} else {
			return LazyOptional.empty();
		}
	}

	@Override
	public void onLoad() {
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

	private boolean hasInputDir(Direction dir) {
		if (inputDirections == null) {
			return false;
		}
		Direction facing = holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		return ArrayUtils.contains(inputDirections, BlockEntityUtils.getRelativeSide(facing, dir));
	}

	private boolean hasOutputDir(Direction dir) {
		if (outputDirections == null) {
			return false;
		}
		Direction facing = holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		return ArrayUtils.contains(outputDirections, BlockEntityUtils.getRelativeSide(facing, dir));
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
