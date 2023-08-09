package electrodynamics.common.tile.network.gas;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.inventory.container.tile.ContainerGasPipeFilter;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileGasPipeFilter extends GenericTile {

	public static final Direction INPUT_DIR = Direction.SOUTH;
	public static final Direction OUTPUT_DIR = Direction.NORTH;

	private boolean isLocked = false;

	@SuppressWarnings("rawtypes")
	public final Property[] filteredGases = {
			//
			property(new Property<>(PropertyType.Gasstack, "gasone", GasStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Gasstack, "gastwo", GasStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Gasstack, "gasthree", GasStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Gasstack, "gasfour", GasStack.EMPTY)) };

	public final Property<Boolean> isWhitelist = property(new Property<>(PropertyType.Boolean, "iswhitelist", false));

	public TileGasPipeFilter(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_GASPIPEFILTER.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentContainerProvider("container.gaspipefilter", this).createMenu((id, inv) -> new ContainerGasPipeFilter(id, inv, getCoordsArray())));
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (cap != ElectrodynamicsCapabilities.GAS_HANDLER || side == null) {
			return LazyOptional.empty();
		}

		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();

		if (side == BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR)) {
			return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
		}

		if (side == BlockEntityUtils.getRelativeSide(facing, INPUT_DIR)) {

			BlockEntity output = level.getBlockEntity(getBlockPos().relative(side.getOpposite()));

			if (output == null) {
				return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
			}

			LazyOptional<IGasHandler> lazy = output.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER, side);

			if (!lazy.isPresent()) {
				return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
			}

			return LazyOptional.of(() -> new FilteredGasCap(lazy.resolve().get(), getFilteredGases(), isWhitelist.get())).cast();

		}

		return LazyOptional.empty();
	}

	private List<Gas> getFilteredGases() {
		List<Gas> gases = new ArrayList<>();

		for (Property<GasStack> prop : filteredGases) {
			if (!prop.get().isEmpty()) {
				gases.add(prop.get().getGas());
			}
		}

		return gases;
	}

	private class FilteredGasCap implements IGasHandler {

		private final IGasHandler outputCap;
		private final List<Gas> validGases;
		private final boolean whitelist;

		private FilteredGasCap(IGasHandler outputCap, List<Gas> validGases, boolean whitelist) {
			this.outputCap = outputCap;
			this.validGases = validGases;
			this.whitelist = whitelist;
		}

		@Override
		public int getTanks() {
			if (isLocked) {
				return 0;
			}
			isLocked = true;
			int count = outputCap.getTanks();
			isLocked = false;
			return count;
		}

		@Override
		public GasStack getGasInTank(int tank) {
			if (isLocked) {
				return GasStack.EMPTY;
			}
			isLocked = true;
			GasStack stack = outputCap.getGasInTank(tank);
			isLocked = false;
			return stack;
		}

		@Override
		public double getTankCapacity(int tank) {
			if (isLocked) {
				return 0;
			}
			isLocked = true;
			double cap = outputCap.getTankCapacity(tank);
			isLocked = false;
			return cap;
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			if (isLocked) {
				return 0;
			}
			isLocked = true;
			double temp = outputCap.getTankMaxTemperature(tank);
			;
			isLocked = false;
			return temp;
		}

		@Override
		public int getTankMaxPressure(int tank) {
			if (isLocked) {
				return 0;
			}
			isLocked = true;
			int pres = outputCap.getTankMaxPressure(tank);
			isLocked = false;
			return pres;
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {

			if (isLocked) {
				return false;
			}

			if (whitelist) {

				if (validGases.isEmpty()) {
					return false;
				}

				if (validGases.contains(gas.getGas())) {
					isLocked = true;
					boolean valid = outputCap.isGasValid(tank, gas);
					;
					isLocked = false;
					return valid;
				}

				return false;

			}

			if (validGases.isEmpty() || !validGases.contains(gas.getGas())) {

				isLocked = true;
				boolean valid = outputCap.isGasValid(tank, gas);
				;
				isLocked = false;

				return valid;
			}

			return false;
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			if (isLocked) {
				return 0;
			}
			if (isGasValid(tank, gas)) {
				isLocked = true;
				double fill = outputCap.fillTank(tank, gas, action);
				;
				isLocked = false;
				return fill;
			}
			return 0;
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			if (isLocked) {
				return GasStack.EMPTY;
			}
			isLocked = true;
			GasStack drain = outputCap.drainTank(tank, tank, action);
			;
			isLocked = false;
			return drain;
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			if (isLocked) {
				return GasStack.EMPTY;
			}
			isLocked = true;
			GasStack drain = outputCap.drainTank(tank, maxFill, action);
			isLocked = false;
			return drain;
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			if (isLocked) {
				return -1;
			}
			isLocked = true;
			double heat = outputCap.heat(tank, deltaTemperature, action);
			;
			isLocked = false;
			return heat;
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			if (isLocked) {
				return -1;
			}
			isLocked = true;
			double pres = outputCap.bringPressureTo(tank, atm, action);
			;
			isLocked = false;
			return pres;
		}

	}

}
