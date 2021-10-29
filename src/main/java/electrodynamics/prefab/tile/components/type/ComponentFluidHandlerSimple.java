package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentFluidHandlerSimple extends AbstractFluidHandler<ComponentFluidHandlerSimple> {

    @Nullable
    private FluidTank fluidTank;
    private List<Fluid> validFluids;

    public ComponentFluidHandlerSimple(GenericTile source) {
	super(source);
    }

    @Override
    public void saveToNBT(CompoundTag nbt) {
	CompoundTag tag = new CompoundTag();
	tag.putString("FluidName", fluidTank.getFluid().getRawFluid().getRegistryName().toString());
	tag.putInt("Amount", fluidTank.getFluid().getAmount());
	if (fluidTank.getFluid().getTag() != null) {
	    tag.put("Tag", fluidTank.getFluid().getTag());
	}
	tag.putInt("cap", fluidTank.getCapacity());
	nbt.put("fluidtank", tag);
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
	return getValidInputFluids().contains(stack.getFluid())
		&& (fluidTank.getFluid().getFluid().isSame(Fluids.EMPTY) || fluidTank.getFluid().getFluid().isSame(stack.getFluid()));
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	return fluidTank.drain(maxDrain, action);
    }

    @Override
    public ComponentFluidHandlerSimple addFluidTank(Fluid fluid, int capacity, boolean isInput) {
	fluidTank = new FluidTank(capacity);
	fluidTank.setFluid(new FluidStack(fluid, 0));
	return this;
    }

    @Override
    public FluidStack getFluidInTank(int tank, boolean isInput) {
	return getFluidInTank(tank);
    }

    @Override
    public Collection<FluidTank> getInputFluidTanks() {
	Collection<FluidTank> tank = new ArrayList<>();
	tank.add(fluidTank);
	return tank;
    }

    @Override
    public Collection<FluidTank> getOutputFluidTanks() {
	return getInputFluidTanks();
    }

    @Override
    public int getInputTanks() {
	return getTanks();
    }

    @Override
    public int getOutputTanks() {
	return getInputTanks();
    }

    @Override
    public List<Fluid> getValidInputFluids() {
	return validFluids;
    }

    @Override
    public List<Fluid> getValidOutputFluids() {
	return getValidInputFluids();
    }

    @Override
    public FluidTank getTankFromFluid(Fluid fluid, boolean isInput) {
	return fluidTank;
    }

    @Override
    public FluidStack getStackFromFluid(Fluid fluid, boolean isInput) {
	return getTankFromFluid(fluid, isInput).getFluid();
    }

    @Override
    public void addFluidToTank(FluidStack fluid, boolean isInput) {
	if (isFluidValid(0, fluid)) {
	    fluidTank.fill(fluid, FluidAction.EXECUTE);
	}
    }

    @Override
    public void drainFluidFromTank(FluidStack fluid, boolean isInput) {
	fluidTank.getFluid().shrink(fluid.getAmount());
	if (fluidTank.getFluidAmount() == 0) {
	    fluidTank.setFluid(FluidStack.EMPTY);
	}
    }

    public ComponentFluidHandlerSimple setValidFluids(List<Fluid> fluids) {
	validFluids = fluids;
	return this;
    }

    @Override
    public ComponentFluidHandlerSimple setFluidInTank(FluidStack stack, int tank, boolean isInput) {
	fluidTank.setFluid(stack);
	return this;
    }

    @Override
    public void addFluids() {
	// not needed
    }

}
