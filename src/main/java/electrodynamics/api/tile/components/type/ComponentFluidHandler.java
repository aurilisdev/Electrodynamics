package electrodynamics.api.tile.components.type;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.api.tile.GenericTile;
import electrodynamics.api.tile.components.Component;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.utilities.UtilitiesTiles;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
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

    public ComponentFluidHandler relativeInput(Direction dir) {
	relativeInputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler relativeOutput(Direction dir) {
	relativeOutputDirections.add(dir);
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
	return fluids.get(fluid).getFluid();
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
	Direction relative = UtilitiesTiles.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(),
		lastDirection);
	boolean canFill = inputDirections.contains(lastDirection)
		|| holder.hasComponent(ComponentType.Direction) && relativeInputDirections.contains(relative);
	return canFill ? fluids.get(resource.getFluid()).fill(resource, action) : 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	Direction relative = UtilitiesTiles.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(),
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
	if (side == null) {
	    return false;
	}
	lastDirection = side;
	Direction relative = UtilitiesTiles.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side);
	return (inputDirections.contains(side) || outputDirections.contains(side)
		|| holder.hasComponent(ComponentType.Direction)
			&& (relativeInputDirections.contains(relative) || relativeOutputDirections.contains(relative)))
		&& capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	return (LazyOptional<T>) LazyOptional.of(() -> this);
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
	nbt.put("list", list);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.FluidHandler;
    }
}