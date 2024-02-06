package electrodynamics.prefab.utilities;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class CapabilityUtils {

    public static final IGasHandler EMPTY_GAS = new IGasHandler() {

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public GasStack getGasInTank(int tank) {
            return GasStack.EMPTY;
        }

        @Override
        public double getTankCapacity(int tank) {
            return 0;
        }

        @Override
        public double getTankMaxTemperature(int tank) {
            return 0;
        }

        @Override
        public int getTankMaxPressure(int tank) {
            return 0;
        }

        @Override
        public boolean isGasValid(int tank, GasStack gas) {
            return false;
        }

        @Override
        public double fillTank(int tank, GasStack gas, GasAction action) {
            return 0;
        }

        @Override
        public GasStack drainTank(int tank, GasStack gas, GasAction action) {
            return GasStack.EMPTY;
        }

        @Override
        public GasStack drainTank(int tank, double maxFill, GasAction action) {
            return GasStack.EMPTY;
        }

        @Override
        public double heat(int tank, double deltaTemperature, GasAction action) {
            return -1;
        }

        @Override
        public double bringPressureTo(int tank, int atm, GasAction action) {
            return -1;
        }

    };

    public static final IFluidHandler EMPTY_FLUID = new IFluidHandler() {

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int tank) {
            return FluidStack.EMPTY;
        }

        @Override
        public int getTankCapacity(int tank) {
            return 0;
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }

    };

    public static class FEInputDispatcher implements IEnergyStorage {

        private final IEnergyStorage parent;

        public FEInputDispatcher(IEnergyStorage parent) {
            this.parent = parent;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return parent.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return parent.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return parent.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return true;
        }

    }

    public static class FEOutputDispatcher implements IEnergyStorage {

        private final IEnergyStorage parent;

        public FEOutputDispatcher(IEnergyStorage parent) {
            this.parent = parent;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return parent.extractEnergy(maxExtract, simulate);
        }

        @Override
        public int getEnergyStored() {
            return parent.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return parent.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return false;
        }

    }

}
