package electrodynamics.common.tile.pipelines.fluids;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.tile.pipelines.GenericTileValve;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileFluidValve extends GenericTileValve {

	public TileFluidValve(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_FLUIDVALVE.get(), pos, state);
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (side == null || cap != ForgeCapabilities.FLUID_HANDLER) {
			return LazyOptional.empty();
		}

		Direction facing = getFacing();

		if (BlockEntityUtils.getRelativeSide(facing, INPUT_DIR) == side || BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR) == side) {

			BlockEntity relative = level.getBlockEntity(worldPosition.relative(side.getOpposite()));

			if (relative == null) {
				return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
			}

			LazyOptional<IFluidHandler> handler = relative.getCapability(ForgeCapabilities.FLUID_HANDLER, side);

			if (handler.isPresent()) {
				return LazyOptional.of(() -> new CapDispatcher(handler.resolve().get())).cast();
			}
		}
		return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
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
		public @NotNull FluidStack getFluidInTank(int tank) {
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
		public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
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
		public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
			if (isClosed || isLocked) {
				return FluidStack.EMPTY;
			}
			isLocked = true;
			FluidStack drain = parent.drain(resource, action);
			isLocked = false;
			return drain;
		}

		@Override
		public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
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
