package electrodynamics.common.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.processing.IElectricProcessor;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileElectricPump extends GenericTileBase implements IElectricProcessor, ITickableTileBase, IFluidHandler {
    private double joules;
    private boolean hasWater;

    public TileElectricPump() {
	super(DeferredRegisters.TILE_ELECTRICPUMP.get());
    }

    @Override
    public double getJoulesStored() {
	return joules;
    }

    @Override
    public double getMaxJoulesStored() {
	return Constants.ELECTRICPUMP_USAGE_PER_TICK * 20;
    }

    protected CachedTileOutput output;

    @Override
    public void tickServer() {
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(getFacing().rotateY()));
	}
	if (world.getWorldInfo().getGameTime() % 20 == 0) {
	    FluidState state = world.getBlockState(pos.offset(Direction.DOWN)).getFluidState();
	    hasWater = state.isSource() && state.getFluid() == Fluids.WATER;
	}
	if (hasWater && joules > Constants.ELECTRICPUMP_USAGE_PER_TICK) {
	    joules -= Constants.ELECTRICPUMP_USAGE_PER_TICK;
	    FluidUtilities.receiveFluid(output.get(), getFacing().rotateY().getOpposite(),
		    new FluidStack(Fluids.WATER, 50), false);
	}
    }

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> this);

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
	if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && getFacing().rotateY() == facing) {
	    return holder.cast();
	}
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC && facing == Direction.UP) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public void setJoulesStored(double joules) {
	this.joules = joules;
    }

    @Override
    public double getJoulesPerTick() {
	return Constants.ELECTRICPUMP_USAGE_PER_TICK;
    }

    @Override
    public int getTanks() {
	return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
	return new FluidStack(Fluids.WATER, 0);
    }

    @Override
    public int getTankCapacity(int tank) {
	return 1000;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	return stack != null && stack.getFluid() == Fluids.WATER;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
	return action == FluidAction.SIMULATE ? 1 : 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	return new FluidStack(Fluids.WATER, 0);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	return new FluidStack(Fluids.WATER, 0);
    }
}
