package electrodynamics.common.tile.pipelines.gas;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.tile.pipelines.GenericTileValve;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileGasValve extends GenericTileValve {

	public TileGasValve(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_GASVALVE.get(), pos, state);
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {

		if (side == null || cap != ElectrodynamicsCapabilities.GAS_HANDLER) {
			return LazyOptional.empty();
		}

		Direction facing = getFacing();

		if (BlockEntityUtils.getRelativeSide(facing, INPUT_DIR) == side || BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR) == side) {

			BlockEntity relative = level.getBlockEntity(worldPosition.relative(side.getOpposite()));

			if (relative == null) {
				return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
			}

			LazyOptional<IGasHandler> handler = relative.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, side);

			if (handler.isPresent()) {
				return LazyOptional.of(() -> new CapDispatcher(handler.resolve().get())).cast();
			}
		}
		return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
	}

	private class CapDispatcher implements IGasHandler {

		private final IGasHandler parent;

		private CapDispatcher(IGasHandler parent) {
			this.parent = parent;
		}

		@Override
		public int getTanks() {
			if (isClosed || isLocked) {
				return 0;
			}
			isLocked = true;
			int tanks = parent.getTanks();
			isLocked = false;
			return tanks;
		}

		@Override
		public GasStack getGasInTank(int tank) {
			if (isClosed || isLocked) {
				return GasStack.EMPTY;
			}
			isLocked = true;
			GasStack stack = parent.getGasInTank(tank);
			isLocked = false;
			return stack;
		}

		@Override
		public double getTankCapacity(int tank) {
			if (isClosed || isLocked) {
				return 0;
			}
			isLocked = true;
			double cap = parent.getTankCapacity(tank);
			isLocked = false;
			return cap;
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			if (isClosed || isLocked) {
				return 0;
			}
			isLocked = true;
			double temp = parent.getTankMaxTemperature(tank);
			isLocked = false;
			return temp;
		}

		@Override
		public int getTankMaxPressure(int tank) {
			if (isClosed || isLocked) {
				return 0;
			}
			isLocked = true;
			int pres = parent.getTankMaxPressure(tank);
			isLocked = false;
			return pres;
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
			if (isClosed || isLocked) {
				return false;
			}
			isLocked = true;
			boolean valid = parent.isGasValid(tank, gas);
			isLocked = false;
			return valid;
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			if (isClosed || isLocked) {
				return 0;
			}
			isLocked = true;
			double fill = parent.fillTank(tank, gas, action);
			isLocked = false;
			return fill;
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			if (isClosed || isLocked) {
				return GasStack.EMPTY;
			}
			isLocked = true;
			GasStack drain = parent.drainTank(tank, tank, action);
			isLocked = false;
			return drain;
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			if (isClosed || isLocked) {
				return GasStack.EMPTY;
			}
			isLocked = true;
			GasStack drain = parent.drainTank(tank, maxFill, action);
			isLocked = false;
			return drain;
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			if (isClosed || isLocked) {
				return -1;
			}
			isLocked = true;
			double heat = parent.heat(tank, deltaTemperature, action);
			isLocked = false;
			return heat;
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			if (isClosed || isLocked) {
				return -1;
			}
			isLocked = true;
			double pres = parent.bringPressureTo(tank, atm, action);
			isLocked = false;
			return pres;
		}

	}

}
