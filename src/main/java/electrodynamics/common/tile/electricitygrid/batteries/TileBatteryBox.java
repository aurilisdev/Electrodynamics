package electrodynamics.common.tile.electricitygrid.batteries;

import javax.annotation.Nullable;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import voltaic.Voltaic;
import voltaic.common.item.ItemUpgrade;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.item.ItemElectric;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils.FEInputDispatcher;
import voltaic.prefab.utilities.CapabilityUtils.FEOutputDispatcher;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileBatteryBox extends GenericTile implements IEnergyStorage {

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.BACK;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.FRONT;

    private final FEInputDispatcher inputDispatcher = new FEInputDispatcher(this);
    private final FEOutputDispatcher outputDispatcher = new FEOutputDispatcher(this);

    public final SingleProperty<Double> powerOutput;
    public final SingleProperty<Double> maxJoules;
    public final SingleProperty<Double> currentCapacityMultiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "currentCapacityMultiplier", 1.0));
    public final SingleProperty<Double> currentVoltageMultiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "currentVoltageMultiplier", 1.0));
    protected final SingleProperty<Double> receiveLimitLeft;
    public final int baseVoltage;

    public TileBatteryBox(BlockPos worldPosition, BlockState blockState) {
	this(ElectrodynamicsTiles.TILE_BATTERYBOX.get(), SubtypeMachine.batterybox, 120,
		359.0 * VoltaicCapabilities.DEFAULT_VOLTAGE / 20.0, 10000000, worldPosition, blockState);
    }

    public TileBatteryBox(BlockEntityType<?> type, SubtypeMachine machine, int baseVoltage, double output, double max,
	    BlockPos worldPosition, BlockState blockState) {
	super(type, worldPosition, blockState);
	this.baseVoltage = baseVoltage;
	powerOutput = property(new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "powerOutput", output));
	maxJoules = property(new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "maxJoulesStored", max));
	receiveLimitLeft = property(new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "receiveLimitLeft",
		output * currentCapacityMultiplier.getValue()));
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1).upgrades(3))
		.validUpgrades(ContainerBatteryBox.VALID_UPGRADES)
		.valid((i, stack, component) -> i == 0 ? stack.getItem() instanceof ItemElectric
			: machineValidator().test(i, stack, component)));
	addComponent(new ComponentContainerProvider(machine.tag(), this)
		.createMenu((id, player) -> new ContainerBatteryBox(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentElectrodynamic(this, true, true).voltage(baseVoltage).maxJoules(max)
		.setInputDirections(INPUT).setOutputDirections(OUTPUT));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	Direction facing = getFacing();
	BlockEntity target = level.getBlockEntity(worldPosition.relative(facing.getOpposite()));
	if (electro.getJoulesStored() > 0 && target != null && !target.isRemoved()) {
	    TransferPack offered = TransferPack.joulesVoltage(
		    Math.min(electro.getJoulesStored(), powerOutput.getValue() * currentCapacityMultiplier.getValue()),
		    electro.getVoltage());
	    TransferPack accepted = ElectricityUtils.receivePower(target, facing, offered, false);
	    electro.joules(electro.getJoulesStored() - accepted.getJoules());
	}
	if (electro.getJoulesStored() > electro.getMaxJoulesStored())
	    electro.joules(electro.getMaxJoulesStored());
	electro.drainElectricItem(0);
    }

    @Nullable
    public IEnergyStorage getFECapability(@Nullable Direction side) {
	if (!Voltaic.isForgeEnergyEnabled())
	    return null;

	Direction facing = getFacing();
	if (side == facing)
	    return inputDispatcher;
	if (side == facing.getOpposite())
	    return outputDispatcher;
	return null;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	int receive = (int) Math.min(maxReceive, powerOutput.getValue() * currentCapacityMultiplier.getValue());
	int accepted = Math.min(receive, (int) (electro.getMaxJoulesStored() - electro.getJoulesStored()));
	if (!simulate)
	    electro.joules(electro.getJoulesStored() + accepted);
	return accepted;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	int extract = (int) Math.min(maxExtract, powerOutput.getValue() * currentCapacityMultiplier.getValue());
	int taken = Math.min(extract, (int) electro.getJoulesStored());
	if (!simulate) {
	    electro.joules(electro.getJoulesStored() - taken);
	    if (electro.getVoltage() > VoltaicCapabilities.DEFAULT_VOLTAGE)
		electro.overVoltage(TransferPack.joulesVoltage(taken, electro.getVoltage()));
	}
	return taken;
    }

    @Override
    public int getEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE,
		this.<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic).getJoulesStored());
    }

    @Override
    public int getMaxEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE,
		this.<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic).getMaxJoulesStored());
    }

    @Override
    public boolean canExtract() {
	return true;
    }

    @Override
    public boolean canReceive() {
	return true;
    }

    @Override
    public void onInventoryChange(ComponentInventory inv, int slot) {
	super.onInventoryChange(inv, slot);
	if (!inv.getUpgradeContents().isEmpty() && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
	    ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	    double capacityMultiplier = 1.0;
	    double voltageMultiplier = 1.0;
	    for (ItemStack stack : inv.getUpgradeContents()) {
		if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype.isEmpty) {
		    for (int i = 0; i < stack.getCount(); i++) {
			if (upgrade.subtype == SubtypeItemUpgrade.basiccapacity) {
			    capacityMultiplier = Math.min(capacityMultiplier * 1.5, Math.pow(1.5, 3));
			    voltageMultiplier = Math.min(voltageMultiplier * 2, 2);
			} else if (upgrade.subtype == SubtypeItemUpgrade.advancedcapacity) {
			    capacityMultiplier = Math.min(capacityMultiplier * 2.25, Math.pow(2.25, 3));
			    voltageMultiplier = Math.min(voltageMultiplier * 4, 4);
			}
		    }
		}
	    }
	    currentCapacityMultiplier.setValue(capacityMultiplier);
	    currentVoltageMultiplier.setValue(voltageMultiplier);
	    receiveLimitLeft.setValue(powerOutput.getValue() * currentCapacityMultiplier.getValue());
	    electro.maxJoules(maxJoules.getValue() * currentCapacityMultiplier.getValue());
	    electro.voltage(baseVoltage * currentVoltageMultiplier.getValue());
	}
    }

    @Override
    public int getComparatorSignal(Level level) {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	return (int) (electro.getJoulesStored() / Math.max(1, electro.getMaxJoulesStored()) * 15.0);
    }
}