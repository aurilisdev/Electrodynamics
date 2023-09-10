package electrodynamics.common.tile.network.electric;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCircuitMonitor;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TileCircuitMonitor extends GenericTile {

	public final Property<Integer> networkProperty = property(new Property<>(PropertyType.Integer, "networkproperty", 0));
	public final Property<Integer> booleanOperator = property(new Property<>(PropertyType.Integer, "booleanoperator", 0));
	public final Property<Double> value = property(new Property<>(PropertyType.Double, "value", 0.0));
	public final Property<Boolean> redstoneSignal = property(new Property<>(PropertyType.Boolean, "redstonesignal", false)).setNoUpdateClient().onChange((prop, old) -> {
		if(level.isClientSide) {
			return;
		}
		
		if(old ^ prop.get()) {
			level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
		}
		
	});

	protected CachedTileOutput output;

	public TileCircuitMonitor(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CIRCUITMONITOR.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this).voltage(-1).receivePower((transfer, debug) -> TransferPack.EMPTY).getConnectedLoad(dir -> TransferPack.EMPTY).relativeInput(Direction.SOUTH));
		addComponent(new ComponentContainerProvider(SubtypeMachine.circuitmonitor, this).createMenu((id, inv) -> new ContainerCircuitMonitor(id, inv, getCoordsArray())));
	}

	/*
	 * Players will expect it to react instantly hence why there is no tick delay
	 * 
	 * It shouldn't be too back with the cached output though
	 */
	public void tickServer(ComponentTickable tickable) {

		double monitoredValue = getMonitoredValue(tickable.getTicks());
		if (monitoredValue < 0) {
			redstoneSignal.set(false);
			return;
		}

		redstoneSignal.set(performCheck(monitoredValue));
		
	}

	@Override
	public int getSignal(Direction dir) {
		return getDirectSignal(dir);
	}

	@Override
	public int getDirectSignal(Direction dir) {
		return redstoneSignal.get() ? 15 : 0;
	}

	public double getMonitoredValue(long ticks) {
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getOpposite()));
		}
		if (ticks % 40 == 0) {
			output.update(worldPosition.relative(this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection()));
		}
		if (output.valid() && output.getSafe() instanceof GenericTileWire wire) {

			ElectricNetwork network = wire.getNetwork();

			return switch (networkProperty.get()) {
			case 0 -> network.getActiveTransmitted() / 20.0; // Wattage in watts; network works in joules
			case 1 -> network.getActiveVoltage(); // Current network Voltage in volts
			case 2 -> network.getMinimumAmpacity(); // Maximum Current network can have before a wire is damaged in amps
			case 3 -> network.getMinimumVoltage(); // The lowest voltage a connected machine has in volts
			case 4 -> network.getResistance(); // The current resistance of the network in ohms
			case 5 -> network.getMaxJoulesStored() / 20.0; //The connected load on the network in watts
			default -> -1;
			};
		}

		return -1;

	}

	public boolean performCheck(double monitoredValue) {
		return switch (booleanOperator.get()) {
		case 0 -> monitoredValue == value.get(); // equals
		case 1 -> monitoredValue != value.get(); // does not equal
		case 2 -> monitoredValue < value.get(); // less than
		case 3 -> monitoredValue > value.get(); // greater than
		case 4 -> monitoredValue <= value.get(); // less than or equal to
		case 5 -> monitoredValue >= value.get(); // greater than or equal to

		default -> false;
		};

	}

}
