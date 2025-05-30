package electrodynamics.common.tile.pipelines.fluid;

import javax.annotation.Nonnull;

import electrodynamics.common.tile.pipelines.GenericTileValve;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;

public class TileFluidValve extends GenericTileValve {

    private boolean isLocked = false;

    public TileFluidValve() {
        super(ElectrodynamicsTiles.TILE_FLUIDVALVE.get());
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
    	if(cap != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || side == null || isLocked) {
    		return LazyOptional.empty();
    	}
    	
    	Direction facing = getFacing();

        if (BlockEntityUtils.getRelativeSide(facing, INPUT_DIR.mappedDir) == side || BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR.mappedDir) == side) {

            TileEntity relative = level.getBlockEntity(worldPosition.relative(side.getOpposite()));

            if (relative == null) {
                return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
            }

            isLocked = true;

            IFluidHandler fluid = relative.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side).orElse(CapabilityUtils.EMPTY_FLUID);

            isLocked = false;

            return fluid == CapabilityUtils.EMPTY_FLUID ? LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast() : LazyOptional.of(() -> new CapDispatcher(fluid)).cast();
        }
    	
    	return LazyOptional.empty();
    }

    private class CapDispatcher implements IFluidHandler {

        private final IFluidHandler parent;

        private CapDispatcher(IFluidHandler parent) {
            this.parent = parent;
        }

        @Override
        public int getTanks() {
            if (isClosed || isLocked) {
                return 1;
            }
            isLocked = true;
            int tanks = parent.getTanks();
            isLocked = false;
            return tanks;
        }

        @Override
        public @Nonnull FluidStack getFluidInTank(int tank) {
            if (isClosed || isLocked) {
                return FluidStack.EMPTY;
            }
            isLocked = true;
            FluidStack stack = parent.getFluidInTank(tank);
            isLocked = false;
            return stack;
        }

        @Override
        public int getTankCapacity(int tank) {
            if (isClosed || isLocked) {
                return 0;
            }
            isLocked = true;
            int cap = parent.getTankCapacity(tank);
            isLocked = false;
            return cap;
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            if (isClosed || isLocked) {
                return false;
            }
            isLocked = true;
            boolean valid = parent.isFluidValid(tank, stack);
            isLocked = false;
            return valid;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (isClosed || isLocked) {
                return 0;
            }
            isLocked = true;
            int fill = parent.fill(resource, action);
            isLocked = false;
            return fill;
        }

        @Override
        public @Nonnull FluidStack drain(FluidStack resource, FluidAction action) {
            if (isClosed || isLocked) {
                return FluidStack.EMPTY;
            }
            isLocked = true;
            FluidStack drain = parent.drain(resource, action);
            isLocked = false;
            return drain;
        }

        @Override
        public @Nonnull FluidStack drain(int maxDrain, FluidAction action) {
            if (isClosed || isLocked) {
                return FluidStack.EMPTY;
            }
            isLocked = true;
            FluidStack drain = parent.drain(maxDrain, action);
            isLocked = false;
            return drain;
        }

    }

}
