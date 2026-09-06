package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileSolarPanel extends GenericGeneratorTile {

    protected SingleProperty<Boolean> generating = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "generating", false));
    protected SingleProperty<Double> multiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "multiplier", 1.0));
    protected SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileSolarPanel(BlockPos worldPosition, BlockState blockState) {
	this(ElectrodynamicsTiles.TILE_SOLARPANEL.get(), worldPosition, blockState, 2.25,
		SubtypeItemUpgrade.improvedsolarcell);
    }

    public TileSolarPanel(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState, double multiplier,
	    SubtypeItemUpgrade... itemUpgrades) {
	super(type, worldPosition, blockState, multiplier, itemUpgrades);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().upgrades(1))
		.validUpgrades(ContainerSolarPanel.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.solarpanel.tag(), this)
		.createMenu((id, player) -> new ContainerSolarPanel(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue()) {
	    generating.setValue(false);
	    return;
	}
	if (tickable.getTicks() % 40 == 0)
	    generating.setValue(level.canSeeSky(worldPosition.above()));
	if (!level.isDay() || !generating.getValue())
	    return;
	BlockEntity target = level.getBlockEntity(worldPosition.below());
	if (target != null && !target.isRemoved())
	    ElectricityUtils.receivePower(target, Direction.UP, getProduced(), false);
    }

    @Override
    public TransferPack getProduced() {
	Level level = this.level;
	if (level == null)
	    return TransferPack.EMPTY;
	double sunlight = 1.0F - Mth
		.clamp(1.0F - (Mth.cos(level.getTimeOfDay(1.0F) * ((float) Math.PI * 2.0F)) * 2.0F + 0.2F), 0.0F, 1.0F);
	double temperature = level.getBiomeManager().getBiome(getBlockPos()).value().getBaseTemperature();
	double temperatureMultiplier = Mth.lerp((temperature + 1) / 3.0, 1.5, 3) / 3.0;
	double weatherMultiplier = level.isRaining() || level.isThundering() ? 0.8 : 1.0;
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	return TransferPack.ampsVoltage(getMultiplier() * ElectrodynamicsConfig.INSTANCE.SOLARPANEL_AMPERAGE.get()
		* temperatureMultiplier * sunlight * weatherMultiplier, electro.getVoltage());
    }

    @Override
    public double getMultiplier() {
	return multiplier.getValue();
    }

    @Override
    public void setMultiplier(double val) {
	multiplier.setValue(val);
    }

    @Override
    public int getComparatorSignal(Level level) {
	return generating.getValue() ? 15 : 0;
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }
}