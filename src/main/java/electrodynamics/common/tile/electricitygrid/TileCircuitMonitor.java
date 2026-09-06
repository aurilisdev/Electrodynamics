package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCircuitMonitor;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileCircuitMonitor extends GenericTile {

    public final SingleProperty<Integer> networkProperty = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "networkproperty", 0)).setUpdateServer();
    public final SingleProperty<Integer> booleanOperator = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "booleanoperator", 0)).setUpdateServer();
    public final SingleProperty<Double> value = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "value", 0.0)).setUpdateServer();
    public final SingleProperty<Boolean> redstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false)
		    .onChange((prop, old) -> {
			Level level = this.level;

			if (level == null || level.isClientSide)
			    return;

			if (old ^ prop.getValue()) {
			    level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
			}

		    }).setNoUpdateClient());

    public TileCircuitMonitor(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CIRCUITMONITOR.get(), worldPos, blockState);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this, false, false).voltage(-1)
		.receivePower((transfer, debug) -> TransferPack.EMPTY)
		.getConnectedLoad((profile, dir) -> TransferPack.EMPTY)
		.setInputDirections(BlockEntityUtils.MachineDirection.FRONT));
	addComponent(new ComponentContainerProvider(SubtypeMachine.circuitmonitor.tag(), this)
		.createMenu((id, inv) -> new ContainerCircuitMonitor(id, inv, getCoordsArray())));
    }

    /*
     * Players will expect it to react instantly hence why there is no tick delay
     * 
     * It shouldn't be too back with the cached output though
     */
    public void tickServer(Level level, ComponentTickable tickable) {

	double monitoredValue = getMonitoredValue(level);
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

    public double getMonitoredValue(Level level) {
	if (!(level.getBlockEntity(worldPosition.relative(getFacing())) instanceof GenericTileWire wire))
	    return -1;
	ElectricNetwork network = wire.getNetwork();
	return switch (networkProperty.getValue()) {
	case 0 -> network.getActiveTransmitted() / 20.0;
	case 1 -> network.getActiveVoltage();
	case 2 -> network.getAmpacity();
	case 3 -> network.getMinimumVoltage();
	case 4 -> network.getResistance();
	case 5 -> network.getMaxJoulesStored() / 20.0;
	default -> -1;
	};
    }

    public boolean performCheck(double monitoredValue) {
	return switch (booleanOperator.getValue()) {
	case 0 -> monitoredValue == value.getValue(); // equals
	case 1 -> monitoredValue != value.getValue(); // does not equal
	case 2 -> monitoredValue < value.getValue(); // less than
	case 3 -> monitoredValue > value.getValue(); // greater than
	case 4 -> monitoredValue <= value.getValue(); // less than or equal to
	case 5 -> monitoredValue >= value.getValue(); // greater than or equal to

	default -> false;
	};

    }

}
