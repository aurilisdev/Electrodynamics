package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.UtilitiesTiles;
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

    @Deprecated
    // we need to rework this to split it into input tanks and output tanks
    protected HashMap<Fluid, FluidTank> fluids = new HashMap<>();

    protected HashSet<Direction> relativeOutputDirections = new HashSet<>();
    protected HashSet<Direction> relativeInputDirections = new HashSet<>();
    protected HashSet<Direction> outputDirections = new HashSet<>();
    protected HashSet<Direction> inputDirections = new HashSet<>();
    protected Direction lastDirection = null;

    private ArrayList<Fluid> INPUT_FLUIDS = new ArrayList<>();
    private ArrayList<Fluid> OUTPUT_FLUIDS = new ArrayList<>();

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

    public ComponentFluidHandler relativeInput(Direction... dir) {
	relativeInputDirections.addAll(Arrays.asList(dir));
	return this;
    }

    public ComponentFluidHandler relativeOutput(Direction... dir) {
	relativeOutputDirections.addAll(Arrays.asList(dir));
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
	if (fluids.get(fluid).getFluid() == null) {
	    fluids.get(fluid).setFluid(new FluidStack(fluid, 0));
	}
	return fluids.get(fluid).getFluid();
    }

    public FluidTank getTankFromFluid(Fluid fluid) {
	return fluids.get(fluid);
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
	Direction relative = UtilitiesTiles.getRelativeSide(
		holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
			: Direction.UP,
		lastDirection);
	boolean canFill = inputDirections.contains(lastDirection)
		|| holder.hasComponent(ComponentType.Direction) && relativeInputDirections.contains(relative);
	return canFill ? fluids.get(resource.getFluid()).fill(resource, action) : 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	Direction relative = UtilitiesTiles.getRelativeSide(
		holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
			: Direction.UP,
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
	lastDirection = side;
	if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
	    return false;
	}
	if (side == null || inputDirections.contains(side) || outputDirections.contains(side)) {
	    return true;
	}
	Direction dir = holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()
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

    public GenericTile getHolder() {
	return holder;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.FluidHandler;
    }

    public Collection<FluidTank> getFluidTanks() {
	return fluids.values();
    }

    public Set<Fluid> getFluids() {
	return fluids.keySet();
    }

    public ArrayList<Fluid> getInputFluids() {
	return INPUT_FLUIDS;
    }

    public ArrayList<Fluid> getOutputFluids() {
	return OUTPUT_FLUIDS;
    }

    public void setInputFluid(Fluid fluid) {
	INPUT_FLUIDS.add(fluid);
    }

    public void setOutputFluid(Fluid fluid) {
	OUTPUT_FLUIDS.add(fluid);
    }

    public ComponentFluidHandler addMultipleFluidTanks(Fluid[] fluids, int capacity, boolean input) {
	for (Fluid fluid : fluids) {
	    if (input) {
		setInputFluid(fluid);
	    } else {
		setOutputFluid(fluid);
	    }
	    fluidTank(fluid, capacity);
	}
	return this;
    }

}
