package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileThermoelectricGenerator extends GenericTile {

    public SingleProperty<Boolean> hasHeat = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "hasheat", false));
    public SingleProperty<Double> heatMultipler = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "multiplier", 0.0));

    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileThermoelectricGenerator(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_THERMOELECTRICGENERATOR.get(), worldPosition, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.TOP));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue())
	    return;
	Direction facing = getFacing();
	BlockPos heatSourcePos = worldPosition.relative(facing.getOpposite());
	if (tickable.getTicks() % 60 == 0) {
	    Fluid fluid = level.getFluidState(heatSourcePos).getType();
	    hasHeat.setValue(ThermoelectricGeneratorHeatRegister.INSTANCE.isHeatSource(fluid));
	    heatMultipler.setValue(ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatMultiplier(fluid));
	}
	if (!hasHeat.getValue())
	    return;
	BlockEntity target = level.getBlockEntity(worldPosition.above());
	if (target == null || target.isRemoved())
	    return;
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	double amperage = ElectrodynamicsConfig.INSTANCE.THERMOELECTRICGENERATOR_AMPERAGE.get()
		* level.getFluidState(heatSourcePos).getAmount() / 8.0 * heatMultipler.getValue();
	ElectricityUtils.receivePower(target, Direction.DOWN, TransferPack.ampsVoltage(amperage, electro.getVoltage()),
		false);
    }

    @Override
    public int getComparatorSignal(Level level) {
	return hasHeat.getValue() ? 15 : 0;
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }
}