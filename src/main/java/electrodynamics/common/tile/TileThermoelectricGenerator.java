package electrodynamics.common.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileThermoelectricGenerator extends GenericTileBase implements ITickableTileBase, IElectrodynamic {
    protected CachedTileOutput output;
    protected boolean hasHeat = false;

    public TileThermoelectricGenerator() {
	super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get());
    }

    @Override
    public void tickServer() {
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
	}
	if (world.getWorldInfo().getDayTime() % 20 == 0) {
	    hasHeat = world.getBlockState(new BlockPos(pos).offset(getFacing().getOpposite())).getFluidState()
		    .getFluid() == Fluids.LAVA;
	}
	if (hasHeat) {
	    ElectricityUtilities.receivePower(output.get(), Direction.UP,
		    TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE, getVoltage()), false);
	}
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC && facing == Direction.UP) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public void setJoulesStored(double joules) {
	// Cant set joules here.
    }

    @Override
    public double getJoulesStored() {
	return 0;
    }

    @Override
    public double getMaxJoulesStored() {
	return 0;
    }

}
