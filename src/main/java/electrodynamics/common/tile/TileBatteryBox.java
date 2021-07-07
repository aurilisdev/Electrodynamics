package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileBatteryBox extends GenericTileTicking implements IEnergyStorage {
    public static final double DEFAULT_OUTPUT_JOULES_PER_TICK = 359.0 * CapabilityElectrodynamic.DEFAULT_VOLTAGE / 20.0;
    public static final double DEFAULT_MAX_JOULES = 10000000;
    public double clientMaxJoulesStored = DEFAULT_MAX_JOULES;
    public double currentCapacityMultiplier = 1;
    public double clientVoltage = 120.0;
    public double clientJoules = 0;
    protected double receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
    private CachedTileOutput output;

    public TileBatteryBox() {
	super(DeferredRegisters.TILE_BATTERYBOX.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket)
		.customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
	addComponent(new ComponentInventory(this).size(3));
	addComponent(new ComponentContainerProvider("container.batterybox")
		.createMenu((id, player) -> new ContainerBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentElectrodynamic(this).maxJoules(DEFAULT_MAX_JOULES).relativeInput(Direction.SOUTH).relativeOutput(Direction.NORTH));
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing.getOpposite()));
	}
	if (tickable.getTicks() % 40 == 0) {
	    output.update();
	}
	receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
	if (electro.getJoulesStored() > 0 && output.valid()) {
	    electro.joules(electro.getJoulesStored() - ElectricityUtilities.receivePower(output.getSafe(), facing, TransferPack.joulesVoltage(
		    Math.min(electro.getJoulesStored(), DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier), electro.getVoltage()), false)
		    .getJoules());
	}
	currentCapacityMultiplier = 1;
	int currentVoltageMultiplier = 1;
	for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
	    if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		ItemProcessorUpgrade upgrade = (ItemProcessorUpgrade) stack.getItem();
		currentCapacityMultiplier *= upgrade.subtype.capacityMultiplier;
		currentVoltageMultiplier = Math.max(currentVoltageMultiplier, upgrade.subtype.capacityMultiplier == 2.25 ? 4 : 2);
	    }
	}
	electro.maxJoules(DEFAULT_MAX_JOULES * currentCapacityMultiplier);
	electro.voltage(120.0 * currentVoltageMultiplier);
	if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
	    electro.joules(electro.getMaxJoulesStored());
	}
	if (tickable.getTicks() % 50 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}

	// Power loss
	electro.extractPower(TransferPack.joulesVoltage(SubtypeWire.copper.resistance, electro.getVoltage()), false);
    }

    protected void createPacket(CompoundNBT nbt) {
	nbt.putDouble("clientMaxJoulesStored", this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getMaxJoulesStored());
	nbt.putDouble("clientJoules", this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored());
	nbt.putDouble("clientVoltage", this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	nbt.putDouble("currentCapacityMultiplier", currentCapacityMultiplier);
    }

    protected void readPacket(CompoundNBT nbt) {
	clientJoules = nbt.getDouble("clientJoules");
	clientVoltage = nbt.getDouble("clientVoltage");
	clientMaxJoulesStored = nbt.getDouble("clientMaxJoulesStored");
	currentCapacityMultiplier = nbt.getDouble("currentCapacityMultiplier");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction face) {
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	ComponentElectrodynamic electro = this.getComponent(ComponentType.Electrodynamic);
	if (electro.hasCapability(capability, face)) {
	    return electro.getCapability(capability, face);
	}
	if (capability == CapabilityEnergy.ENERGY && (face == facing || face == facing.getOpposite())) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, face);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
	TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic)
		.receivePower(TransferPack.joulesVoltage(maxReceive, CapabilityElectrodynamic.DEFAULT_VOLTAGE), simulate);
	return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
	TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic)
		.extractPower(TransferPack.joulesVoltage(maxExtract, CapabilityElectrodynamic.DEFAULT_VOLTAGE), simulate);
	return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
    }

    @Override
    public int getEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE, this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored());
    }

    @Override
    public int getMaxEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE, this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getMaxJoulesStored());
    }

    @Override
    public boolean canExtract() {
	return true;
    }

    @Override
    public boolean canReceive() {
	return true;
    }

}
