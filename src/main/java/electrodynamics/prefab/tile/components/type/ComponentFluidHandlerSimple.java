package electrodynamics.prefab.tile.components.type;

import java.util.HashSet;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Extension of FluidTank allowing for simple fluid storage for one tank
 * 
 * This is a separate class because ComponentFluidHandlerMulti is geared towards
 * having distinct input and output tanks where as this only has one tank
 * 
 * This class also has no concept of a RecipeType tied to it since recipes have distinct 
 * inputs and outputs
 * 
 * @author skip999
 *
 */
public class ComponentFluidHandlerSimple extends FluidTank implements Component {

	@Nullable
	private GenericTile holder;
	@Nullable
	private Property<FluidTank> property;
	@Nullable
	public Direction[] inputDirections;
	@Nullable
	public Direction[] outputDirections;
	@Nullable
	private TagKey<Fluid>[] validFluidTags;
	@Nullable
	private Fluid[] validFluids;

	private HashSet<Fluid> validatorFluids = new HashSet<>();

	public ComponentFluidHandlerSimple(int capacity, Predicate<FluidStack> validator) {
		super(capacity, validator);
	}

	public ComponentFluidHandlerSimple(int capacity) {
		super(capacity);
	}

	public ComponentFluidHandlerSimple setProperty(@Nonnull GenericTile holder) {
		this.holder = holder;
		property = holder.property(
				new Property<FluidTank>(PropertyType.FluidTank, "componentfluidhandlersimple", new FluidTank(capacity)));
		return this;
	}

	public ComponentFluidHandlerSimple setInputDirections(Direction... directions) {
		this.inputDirections = directions;
		return this;
	}

	public ComponentFluidHandlerSimple setOutputDirections(Direction... directions) {
		this.outputDirections = directions;
		return this;
	}
	
	public ComponentFluidHandlerSimple universalInput() {
		this.inputDirections = Direction.values();
		return this;
	}
	
	public ComponentFluidHandlerSimple universalOutput() {
		this.outputDirections = Direction.values();
		return this;
	}

	@Override
	public ComponentFluidHandlerSimple setCapacity(int capacity) {
		return (ComponentFluidHandlerSimple) super.setCapacity(capacity);
	}

	@Override
	public ComponentFluidHandlerSimple setValidator(Predicate<FluidStack> validator) {
		return (ComponentFluidHandlerSimple) super.setValidator(validator);
	}
	
	public ComponentFluidHandlerSimple setValidFluids(Fluid...fluids) {
		validFluids = fluids;
		return this;
	}
	
	public ComponentFluidHandlerSimple setValidFluidTags(TagKey<Fluid>...fluids) {
		validFluidTags = fluids;
		return this;
	}

	@Override
	protected void onContentsChanged() {
		if (property != null) {
			property.set(this);
			property.forceDirty();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComponentFluidHandlerSimple tank) {
			return tank.getFluid().equals(getFluid()) && tank.getCapacity() == getCapacity();
		} else {
			return false;
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.FluidHandler;
	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction side) {
		return capability == ForgeCapabilities.FLUID_HANDLER;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (!hasCapability(capability, side)) {
			return LazyOptional.empty();
		}
		if (inputDirections == null && outputDirections == null) {
			return LazyOptional.<IFluidHandler>of(() -> this).cast();
		}
		if (hasInputDir(side)) {
			return LazyOptional.<IFluidHandler>of(() -> new InputTank(this)).cast();
		} else if (hasOutputDir(side)) {
			return LazyOptional.<IFluidHandler>of(() -> new OutputTank(this)).cast();
		} else {
			return LazyOptional.empty();
		}
	}

	@Override
	public void saveToNBT(CompoundTag nbt) {
		writeToNBT(nbt);
	}

	@Override
	public void loadFromNBT(CompoundTag nbt) {
		readFromNBT(nbt);
	}

	@Override
	public void onLoad() {
		if(validFluids != null) {
			for(Fluid fluid : validFluids) {
				validatorFluids.add(fluid);
			}
		}
		if(validFluidTags != null) {
			for(TagKey<Fluid> tag : validFluidTags) {
				for(Fluid fluid : ForgeRegistries.FLUIDS.tags().getTag(tag).stream().toList()) {
					validatorFluids.add(fluid);
				}
			}
		}
		if(!validatorFluids.isEmpty()) {
			validator = fluidStack -> validatorFluids.contains(fluidStack.getFluid());
		}
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
	
	public FluidTank[] toArray() {
		return new FluidTank[] {this};
	}

	private class InputTank extends ComponentFluidHandlerSimple {

		public InputTank(ComponentFluidHandlerSimple property) {
			super(property.capacity, property.validator);
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

	private class OutputTank extends ComponentFluidHandlerSimple {

		public OutputTank(ComponentFluidHandlerSimple property) {
			super(property.capacity, property.validator);
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			return 0;
		}

	}

}
