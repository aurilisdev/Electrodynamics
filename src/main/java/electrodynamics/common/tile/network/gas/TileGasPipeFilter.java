package electrodynamics.common.tile.network.gas;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.inventory.container.ContainerGasPipeFilter;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
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

	public final Property[] filteredGases = {
			//
			property(new Property<>(PropertyType.Gasstack, "gasone", GasStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Gasstack, "gastwo", GasStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Gasstack, "gasthree", GasStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Gasstack, "gasfour", GasStack.EMPTY)) };

	public TileGasPipeFilter(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_GASPIPEFILTER.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentInventory(this));
		addComponent(new ComponentContainerProvider("container.gaspipefilter", this).createMenu((id, inv) -> new ContainerGasPipeFilter(id, inv, getComponent(ComponentType.Inventory), getCoordsArray())));
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

			return LazyOptional.of(() -> new FilteredGasCap(lazy.resolve().get(), getFilteredGases())).cast();

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

		private FilteredGasCap(IGasHandler outputCap, List<Gas> validGases) {
			this.outputCap = outputCap;
			this.validGases = validGases;
		}

		@Override
		public int getTanks() {
			return outputCap.getTanks();
		}

		@Override
		public GasStack getGasInTank(int tank) {
			return outputCap.getGasInTank(tank);
		}

		@Override
		public double getTankCapacity(int tank) {
			return outputCap.getTankCapacity(tank);
		}

		@Override
		public double getTankMaxTemperature(int tank) {
			return outputCap.getTankMaxTemperature(tank);
		}

		@Override
		public int getTankMaxPressure(int tank) {
			return outputCap.getTankMaxPressure(tank);
		}

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
			if (validGases.isEmpty() || validGases.contains(gas.getGas())) {
				return outputCap.isGasValid(tank, gas);
			}
			return false;
		}

		@Override
		public double fillTank(int tank, GasStack gas, GasAction action) {
			if (isGasValid(tank, gas)) {
				return outputCap.fillTank(tank, gas, action);
			}
			return 0;
		}

		@Override
		public GasStack drainTank(int tank, GasStack gas, GasAction action) {
			return outputCap.drainTank(tank, tank, action);
		}

		@Override
		public GasStack drainTank(int tank, double maxFill, GasAction action) {
			return outputCap.drainTank(tank, maxFill, action);
		}

		@Override
		public double heat(int tank, double deltaTemperature, GasAction action) {
			return outputCap.heat(tank, deltaTemperature, action);
		}

		@Override
		public double bringPressureTo(int tank, int atm, GasAction action) {
			return outputCap.bringPressureTo(tank, atm, action);
		}

	}

}
