package electrodynamics.common.tile.pipelines.fluid;

import javax.annotation.Nullable;

import electrodynamics.common.tile.pipelines.GenericTileValve;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;

public class TileFluidValve extends GenericTileValve {

    private boolean isLocked = false;

    public TileFluidValve(BlockPos pos, BlockState state) {
	super(ElectrodynamicsTiles.TILE_FLUIDVALVE.get(), pos, state);
    }

    @Override
    @Nullable
    public IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
	if (side == null || isLocked)
	    return null;

	Level level = this.level;
	if (level == null)
	    return null;

	Direction facing = getFacing();
	if (BlockEntityUtils.getRelativeSide(facing, INPUT_DIR.mappedDir) == side
		|| BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR.mappedDir) == side) {

	    BlockEntity relative = level.getBlockEntity(worldPosition.relative(side.getOpposite()));

	    if (relative == null)
		return CapabilityUtils.EMPTY_FLUID;

	    isLocked = true;

	    IFluidHandler fluid = level.getCapability(Capabilities.FluidHandler.BLOCK, relative.getBlockPos(),
		    relative.getBlockState(), relative, side);

	    isLocked = false;

	    return fluid == null ? CapabilityUtils.EMPTY_FLUID : new CapDispatcher(fluid);
	}

	return null;
    }

    private class CapDispatcher implements IFluidHandler {

	private final IFluidHandler parent;

	private CapDispatcher(IFluidHandler parent) {
	    this.parent = parent;
	}

	@Override
	public int getTanks() {
	    if (isClosed || isLocked)
		return 1;
	    isLocked = true;
	    int tanks = parent.getTanks();
	    isLocked = false;
	    return tanks;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
	    if (isClosed || isLocked)
		return FluidStack.EMPTY;
	    isLocked = true;
	    FluidStack stack = parent.getFluidInTank(tank);
	    isLocked = false;
	    return stack;
	}

	@Override
	public int getTankCapacity(int tank) {
	    if (isClosed || isLocked)
		return 0;
	    isLocked = true;
	    int cap = parent.getTankCapacity(tank);
	    isLocked = false;
	    return cap;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
	    if (isClosed || isLocked)
		return false;
	    isLocked = true;
	    boolean valid = parent.isFluidValid(tank, stack);
	    isLocked = false;
	    return valid;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
	    if (isClosed || isLocked)
		return 0;
	    isLocked = true;
	    int fill = parent.fill(resource, action);
	    isLocked = false;
	    return fill;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
	    if (isClosed || isLocked)
		return FluidStack.EMPTY;
	    isLocked = true;
	    FluidStack drain = parent.drain(resource, action);
	    isLocked = false;
	    return drain;
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
	    if (isClosed || isLocked)
		return FluidStack.EMPTY;
	    isLocked = true;
	    FluidStack drain = parent.drain(maxDrain, action);
	    isLocked = false;
	    return drain;
	}

    }

}
