package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileCreativePowerSource extends GenericTile {

    private static final int POWER_MULTIPLIER = 1000000;

    public final SingleProperty<Integer> voltage = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "setvoltage", 0)).setUpdateServer();
    public final SingleProperty<Double> power = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "setpower", 0.0)).setUpdateServer();

    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileCreativePowerSource(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CREATIVEPOWERSOURCE.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.values()).voltage(-1));
	addComponent(new ComponentContainerProvider(SubtypeMachine.creativepowersource.tag(), this)
		.createMenu((id, player) -> new ContainerCreativePowerSource(id, player, getCoordsArray())));
    }

    private void tickServer(Level level, ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue() || voltage.getValue() <= 0)
	    return;
	TransferPack output = TransferPack.joulesVoltage(power.getValue() * POWER_MULTIPLIER / 20.0,
		voltage.getValue());
	for (Direction direction : Direction.values()) {
	    BlockEntity target = level.getBlockEntity(worldPosition.relative(direction));
	    if (target != null && !target.isRemoved())
		ElectricityUtils.receivePower(target, direction.getOpposite(), output, false);
	}
    }

    @Override
    public int getComparatorSignal(Level level) {
	return power.getValue() > 0 ? 15 : 0;
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }
}