package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCircuitMonitor;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileCircuitMonitor extends GenericTile {

	public final SingleProperty<Integer> networkProperty = property(new SingleProperty<>(PropertyTypes.INTEGER, "networkproperty", 0));
	public final SingleProperty<Integer> booleanOperator = property(new SingleProperty<>(PropertyTypes.INTEGER, "booleanoperator", 0));
	public final SingleProperty<Double> value = property(new SingleProperty<>(PropertyTypes.DOUBLE, "value", 0.0));
	public final SingleProperty<Boolean> redstoneSignal = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "redstonesignal", false).onChange((prop, old) -> {
		if (level == null || level.isClientSide) {
			return;
		}

		if (old ^ prop.getValue()) {
			level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
		}

	}).setNoUpdateClient());

	protected CachedTileOutput output;

	public TileCircuitMonitor() {
		super(ElectrodynamicsTiles.TILE_CIRCUITMONITOR.get());
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this, false, false).voltage(-1).receivePower((transfer, debug) -> TransferPack.EMPTY).getConnectedLoad((profile, dir) -> TransferPack.EMPTY).setInputDirections(BlockEntityUtils.MachineDirection.FRONT));
		addComponent(new ComponentContainerProvider(SubtypeMachine.circuitmonitor.tag(), this).createMenu((id, inv) -> new ContainerCircuitMonitor(id, inv, getCoordsArray())));
	}

	/*
	 * Players will expect it to react instantly hence why there is no tick delay
	 * 
	 * It shouldn't be too back with the cached output though
	 */
	public void tickServer(ComponentTickable tickable) {

		double monitoredValue = getMonitoredValue(tickable.getTicks());
		if (monitoredValue < 0) {
			redstoneSignal.setValue(false);
			return;
		}

		redstoneSignal.setValue(performCheck(monitoredValue));

	}

	@Override
	public int getSignal(Direction dir) {
		return getDirectSignal(dir);
	}

	@Override
	public int getDirectSignal(Direction dir) {
		return redstoneSignal.getValue() ? 15 : 0;
	}

	public double getMonitoredValue(long ticks) {
		Direction facing = getFacing();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (ticks % 40 == 0) {
			output.update(worldPosition.relative(facing));
		}
		TileEntity blockentity = output.getSafe();
		if (output.valid() && blockentity instanceof GenericTileWire) {
			GenericTileWire wire = (GenericTileWire) blockentity;

			ElectricNetwork network = wire.getNetwork();
			
			if(network == null) {
				return 0;
			}

			switch (networkProperty.getValue()) {
			case 0:
				return network.getActiveTransmitted() / 20.0; // Wattage in watts; network works in joules
			case 1:
				return network.getActiveVoltage(); // Current network Voltage in volts
			case 2:
				return network.getAmpacity(); // Maximum Current network can have before a wire is damaged in amps
			case 3:
				return network.getMinimumVoltage(); // The lowest voltage a connected machine has in volts
			case 4:
				return network.getResistance(); // The current resistance of the network in ohms
			case 5:
				return network.getMaxJoulesStored() / 20.0; // The connected load on the network in watts
			// case 6 -> TransferPack.joulesVoltage(network.getActiveTransmitted(), network.getActiveVoltage()).getAmps();
			default:
				return -1;
			}
			
		}

		return -1;

	}

	public boolean performCheck(double monitoredValue) {
		switch (booleanOperator.getValue()) {
		case 0:
			return monitoredValue == value.getValue(); // equals
		case 1:
			return monitoredValue != value.getValue(); // does not equal
		case 2:
			return monitoredValue < value.getValue(); // less than
		case 3:
			return monitoredValue > value.getValue(); // greater than
		case 4:
			return monitoredValue <= value.getValue(); // less than or equal to
		case 5:
			return monitoredValue >= value.getValue(); // greater than or equal to

		default:
			return false;
		}

	}

}
