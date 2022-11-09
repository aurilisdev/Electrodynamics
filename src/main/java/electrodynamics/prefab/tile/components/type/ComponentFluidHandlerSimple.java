package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.generic.AbstractFluidHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

public class ComponentFluidHandlerSimple extends AbstractFluidHandler<ComponentFluidHandlerSimple> {

	@Nullable
	private FluidTank fluidTank;
	private List<Fluid> validFluids = new ArrayList<>();

	public ComponentFluidHandlerSimple(GenericTile source) {
		super(source);
	}

	@Override
	public void loadFromNBT(CompoundTag nbt) {
		CompoundTag compound = nbt.getCompound("fluidtank");
		int cap = compound.getInt("cap");
		FluidStack stack = FluidStack.loadFluidStackFromNBT(compound);
		FluidTank tank = new FluidTank(cap);
		tank.setFluid(stack);
		fluidTank = tank;
	}

	@Override
	public void saveToNBT(CompoundTag nbt) {
		CompoundTag tag = new CompoundTag();
		tag.putString("FluidName", fluidTank.getFluid().getRawFluid().builtInRegistryHolder().key().location().toString());
		tag.putInt("Amount", fluidTank.getFluid().getAmount());
		if (fluidTank.getFluid().getTag() != null) {
			tag.put("Tag", fluidTank.getFluid().getTag());
		}
		tag.putInt("cap", fluidTank.getCapacity());
		nbt.put("fluidtank", tag);
	}

	@Override
	public int getTanks() {
		return 1;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return fluidTank.getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		return fluidTank.getCapacity();
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		if (validFluids.contains(stack.getFluid())) {
			return fluidTank.isFluidValid(stack);
		}
		return false;
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return fluidTank.drain(maxDrain, action);
	}

	@Override
	protected void addFluidTank(Fluid fluid, boolean isInput) {
		if (fluidTank == null) {
			fluidTank = new FluidTank(tankCapacity);
			fluidTank.setFluid(new FluidStack(fluid, 0));
		}
	}

	@Override
	protected void setFluidInTank(FluidStack stack, int tank, boolean isInput) {
		fluidTank.setFluid(stack);
	}

	@Override
	public ComponentFluidHandlerSimple setManualFluids(int tankCount, boolean isInput, int capacity, Fluid... fluids) {
		tankCapacity = capacity;
		for (Fluid fluid : fluids) {
			addValidFluid(fluid, isInput);
		}
		if (fluidTank == null) {
			fluidTank = new FluidTank(capacity);
		}
		return this;
	}

	@Override
	public ComponentFluidHandlerSimple setInputTags(int count, int capacity, TagKey<Fluid>... tags) {
		inTankCount = count;
		inKeys = tags;
		inCapacity = capacity;
		return this;
	}

	@Override
	// we only need the one so we can ignore this one
	public ComponentFluidHandlerSimple setOutputTags(int count, int capacity, TagKey<Fluid>... tags) {
		return this;
	}

	@Override
	public FluidStack getFluidInTank(int tank, boolean isInput) {
		return fluidTank.getFluid();
	}

	@Override
	public FluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
		return fluidTank;
	}

	@Override
	public FluidTank[] getInputTanks() {
		return new FluidTank[] { fluidTank };
	}

	@Override
	public FluidTank[] getOutputTanks() {
		return new FluidTank[] { fluidTank };
	}

	@Override
	public List<Fluid> getValidInputFluids() {
		return validFluids;
	}

	@Override
	public List<Fluid> getValidOutputFluids() {
		return validFluids;
	}

	@Override
	protected void addValidFluid(Fluid fluid, boolean isInput) {
		if (!validFluids.contains(fluid)) {
			validFluids.add(fluid);
		}
	}

	@Override
	public int getInputTankCount() {
		return getTanks();
	}

	@Override
	public int getOutputTankCount() {
		return getTanks();
	}

	@Override
	public void addFluidToTank(FluidStack fluid, boolean isInput) {
		fluidTank.fill(fluid, FluidAction.EXECUTE);
	}

	@Override
	public void drainFluidFromTank(FluidStack fluid, boolean isInput) {
		fluidTank.drain(fluid, FluidAction.EXECUTE);
	}

	@Override
	public void addFluids() {
		if (inKeys != null) {
			List<Fluid> inputFluidHolder = new ArrayList<>();
			for (TagKey<Fluid> key : inKeys) {
				inputFluidHolder.addAll(ForgeRegistries.FLUIDS.tags().getTag(key).stream().toList());
			}
			setManualFluids(inTankCount, true, inCapacity, inputFluidHolder.toArray(new Fluid[inputFluidHolder.size()]));
		}
		if (outKeys != null) {
			List<Fluid> outputFluidHolder = new ArrayList<>();
			for (TagKey<Fluid> key : outKeys) {
				outputFluidHolder.addAll(ForgeRegistries.FLUIDS.tags().getTag(key).stream().toList());
			}
			setManualFluids(outTankCount, false, outCapacity, outputFluidHolder.toArray(new Fluid[outputFluidHolder.size()]));
		}
	}
}
