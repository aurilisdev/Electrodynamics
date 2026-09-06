package electrodynamics.common.tile.electricitygrid.generators;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCoalGenerator;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TargetValue;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileCoalGenerator extends GenericGeneratorTile {

    protected TransferPack currentOutput = TransferPack.EMPTY;

    public TargetValue.PropertyTargetValue heat = new TargetValue.PropertyTargetValue(
	    property(new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "heat", 27.0)));
    public SingleProperty<Integer> burnTime = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "burnTime", 0));
    public SingleProperty<Integer> maxBurnTime = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "maxBurnTime", 1));

    private final SingleProperty<Double> multiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "multiplier", 1.0));
    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileCoalGenerator(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_COALGENERATOR.get(), worldPosition, blockState, 1.0);
	addComponent(new ComponentTickable(this).tickClient(this::tickClient).tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BACK));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1))
		.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.BOTTOM,
			BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.LEFT,
			BlockEntityUtils.MachineDirection.RIGHT)
		.valid((index, stack, inventory) -> getValidItems().contains(stack.getItem())));
	addComponent(new ComponentContainerProvider(SubtypeMachine.coalgenerator.tag(), this)
		.createMenu((id, player) -> new ContainerCoalGenerator(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    @SuppressWarnings("null")
    protected void tickServer(Level level, ComponentTickable tickable) {
	if (burnTime.getValue() > 0)
	    burnTime.setValue(burnTime.getValue() - 1);
	if (hasRedstoneSignal.getValue())
	    return;
	ComponentInventory inventory = requireComponent(IComponentType.Inventory);
	ItemStack fuel = inventory.getItem(0);
	if (burnTime.getValue() <= 0 && !fuel.isEmpty()) {
	    burnTime.setValue(fuel.getBurnTime(null));
	    fuel.shrink(1);
	    maxBurnTime.setValue(Math.max(burnTime.getValue(), 1));
	}
	boolean burning = burnTime.getValue() > 0;
	if (BlockEntityUtils.isLit(this) ^ burning)
	    BlockEntityUtils.updateLit(this, burning);
	if (heat.getValue() > 27) {
	    Direction facing = getFacing();
	    BlockEntity target = level.getBlockEntity(worldPosition.relative(facing.getOpposite()));
	    if (target != null && !target.isRemoved())
		ElectricityUtils.receivePower(target, facing, currentOutput, false);
	}
	heat.rangeParameterize(27, 3000, burning ? 3000 : 27, heat.getValue(), 600).flush();
	currentOutput = getProduced();
    }

    protected void tickClient(Level level, ComponentTickable tickable) {
	if (!getBlockState().getValue(VoltaicBlockStates.LIT))
	    return;
	Direction direction = getFacing();
	if (level.random.nextInt(10) == 0)
	    level.playLocalSound(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D,
		    SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + level.random.nextFloat(),
		    level.random.nextFloat() * 0.7F + 0.6F, false);
	if (level.random.nextInt(10) == 0)
	    level.addParticle(ParticleTypes.LAVA, worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D,
		    worldPosition.getZ() + 0.5D, direction.getStepX(), 0.0, direction.getStepZ());
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
    public TransferPack getProduced() {
	return TransferPack
		.ampsVoltage(multiplier.getValue() * ElectrodynamicsConfig.INSTANCE.COALGENERATOR_AMPERAGE.get()
			* ((heat.getValue() - 27.0) / (3000.0 - 27.0)), VoltaicCapabilities.DEFAULT_VOLTAGE);
    }

    public static List<Item> getValidItems() {
	return new ArrayList<>(CoalGeneratorFuelRegister.INSTANCE.getFuels());
    }

    @Override
    public int getComparatorSignal(Level level) {
	return (int) (heat.getValue() / 3000.0 * 15.0);
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }
}