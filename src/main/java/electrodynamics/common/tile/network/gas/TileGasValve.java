package electrodynamics.common.tile.network.gas;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.tile.network.GenericTileValve;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileGasValve extends GenericTileValve {
	
	private static final IGasHandler EMPTY = new IGasHandler() {

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
	
	public TileGasValve(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_GASVALVE.get(), pos, state);
		addComponent(new ComponentDirection(this));
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		
		if(side == null || cap != ElectrodynamicsCapabilities.GAS_HANDLER) {
			return LazyOptional.empty();
		}
		
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		
		if(BlockEntityUtils.getRelativeSide(facing, INPUT_DIR) == side || BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR) == side) {
			
			BlockEntity relative = level.getBlockEntity(worldPosition.relative(side.getOpposite()));
			
			if(relative == null) {
				return LazyOptional.of(() -> EMPTY).cast();
			}
			
			LazyOptional<IGasHandler> handler = relative.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, side);
			
			if(handler.isPresent()) {
				return LazyOptional.of(() -> new CapDispatcher(handler.resolve().get())).cast();
			}
		}
		return LazyOptional.of(() -> EMPTY).cast();
	}
	
	private class CapDispatcher implements IGasHandler {

		private final IGasHandler parent;
		
		private CapDispatcher(IGasHandler parent) {
			this.parent = parent;
		}
		
		@Override
		public int getTanks() {
			if(isClosed) {
				return 1;
			}
			return parent.getTanks();
		}

		@Override
		public GasStack getGasInTank(int tank) {
			if(isClosed) {
				return GasStack.EMPTY;
			}
			return parent.getGasInTank(tank);
		}

		@Override
		public double getTankCapacity(int tank) {
			if(isClosed) {
				return 0;
			}
			return parent.getTankCapacity(tank);
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			if(isClosed) {
				return 0;
			}
			return parent.getTankMaxTemperature(tank);
		}

		@Override
		public int getTankMaxPressure(int tank) {
			if(isClosed) {
				return 0;
			}
			return parent.getTankMaxPressure(tank);
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
			if(isClosed) {
				return false;
			}
			return parent.isGasValid(tank, gas);
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			if(isClosed) {
				return 0;
			}
			return parent.fillTank(tank, gas, action);
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			if(isClosed) {
				return GasStack.EMPTY;
			}
			return parent.drainTank(tank, tank, action);
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			if(isClosed) {
				return GasStack.EMPTY;
			}
			return parent.drainTank(tank, maxFill, action);
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			if(isClosed) {
				return -1;
			}
			return parent.heat(tank, deltaTemperature, action);
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			if(isClosed) {
				return -1;
			}
			return parent.bringPressureTo(tank, atm, action);
		}
		
	}

}
