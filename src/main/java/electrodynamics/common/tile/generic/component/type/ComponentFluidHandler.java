package electrodynamics.common.tile.generic.component.type;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.api.utilities.TileUtilities;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
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
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected HashMap<Fluid, FluidTank> fluids = new HashMap<>();
    protected HashSet<Direction> relativeOutputDirections = new HashSet<>();
    protected HashSet<Direction> relativeInputDirections = new HashSet<>();
    protected HashSet<Direction> outputDirections = new HashSet<>();
    protected HashSet<Direction> inputDirections = new HashSet<>();

    public ComponentFluidHandler(GenericTile source) {
	setHolder(source);
	if (holder.hasComponent(ComponentType.PacketHandler)) {
	    setHolder(source);
	    ComponentPacketHandler handler = holder.getComponent(ComponentType.PacketHandler);
	    handler.addGuiPacketWriter(this::writeGuiPacket);
	    handler.addGuiPacketReader(this::readGuiPacket);
	}
    }

    private void writeGuiPacket(CompoundNBT nbt) {
	saveToNBT(nbt);
    }

    private void readGuiPacket(CompoundNBT nbt) {
	loadFromNBT(null, nbt);
    }

    public ComponentFluidHandler addInputDirection(Direction dir) {
	inputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler addOutputDirection(Direction dir) {
	outputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler addRelativeInputDirection(Direction dir) {
	relativeInputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler addRelativeOutputDirection(Direction dir) {
	relativeOutputDirections.add(dir);
	return this;
    }

    public ComponentFluidHandler addFluidTank(Fluid fluid, int capacity) {
	fluids.put(fluid, new FluidTank(capacity, test -> test.getFluid() == fluid));
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
	return fluids.get(resource.getFluid()).fill(resource, action);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	return fluids.get(resource.getFluid()).drain(resource, action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	return FluidStack.EMPTY;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction side) {
	Direction relative = TileUtilities
		.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side);
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
	    fluids.put(tank.getFluid().getFluid(), tank);
	}
    }

    @Override
    public void saveToNBT(CompoundNBT nbt) {
	ListNBT list = new ListNBT();
	for (FluidTank stack : fluids.values()) {
	    CompoundNBT tag = new CompoundNBT();
	    stack.writeToNBT(tag);
	    list.add(tag);
	}
	nbt.put("list", list);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.FluidHandler;
    }
}
