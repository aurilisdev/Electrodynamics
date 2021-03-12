package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentContainerProvider;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentInventory;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileBatteryBox extends GenericTileTicking implements IEnergyStorage {
    public static final double DEFAULT_OUTPUT_JOULES_PER_TICK = 359.0 * CapabilityElectrodynamic.DEFAULT_VOLTAGE / 20.0;
    public static final double DEFAULT_MAX_JOULES = 5000000;
    public double clientMaxJoulesStored = DEFAULT_MAX_JOULES;
    public double currentCapacityMultiplier = 1;
    public double clientVoltage = 120.0;
    public double clientJoules = 0;
    protected double receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
    private CachedTileOutput output;

    public TileBatteryBox() {
	super(DeferredRegisters.TILE_BATTERYBOX.get());
	addComponent(new ComponentElectrodynamic().setMaxJoules(DEFAULT_MAX_JOULES)
		.addRelativeInputDirection(Direction.SOUTH).addRelativeOutputDirection(Direction.NORTH));
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().setTickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().setCustomPacketSupplier(this::createPacket)
		.setGuiPacketSupplier(this::createPacket).setCustomPacketConsumer(this::readPacket)
		.setGuiPacketConsumer(this::readPacket));
	addComponent(new ComponentInventory().setInventorySize(3));
	addComponent(new ComponentContainerProvider("container.batterybox")
		.setCreateMenuFunction((id, player) -> new ContainerBatteryBox(id, player,
			this.<ComponentInventory>getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer() {
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(facing.getOpposite()));
	}
	receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
	if (electro.getJoulesStored() > 0) {
	    electro.setJoules(
		    electro.getJoulesStored()
			    - ElectricityUtilities
				    .receivePower(output.get(), facing,
					    TransferPack
						    .joulesVoltage(
							    Math.min(electro.getJoulesStored(),
								    DEFAULT_OUTPUT_JOULES_PER_TICK
									    * currentCapacityMultiplier),
							    electro.getVoltage()),
					    false)
				    .getJoules());
	}
	currentCapacityMultiplier = 1;
	for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
	    if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		ItemProcessorUpgrade upgrade = (ItemProcessorUpgrade) stack.getItem();
		currentCapacityMultiplier *= upgrade.subtype.capacityMultiplier;
	    }
	}
	electro.setMaxJoules(DEFAULT_MAX_JOULES * currentCapacityMultiplier);
	if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
	    electro.setJoules(electro.getMaxJoulesStored());
	}
	if (world.getWorldInfo().getDayTime() % 50 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    public CompoundNBT createPacket() {
	CompoundNBT nbt = new CompoundNBT();
	nbt.putDouble("clientMaxJoulesStored",
		this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getMaxJoulesStored());
	nbt.putDouble("clientJoules",
		this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored());
	nbt.putDouble("clientVoltage",
		this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	nbt.putDouble("currentCapacityMultiplier", currentCapacityMultiplier);
	return nbt;
    }

    public void readPacket(CompoundNBT nbt) {
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
	TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).receivePower(
		TransferPack.joulesVoltage(maxReceive, CapabilityElectrodynamic.DEFAULT_VOLTAGE), simulate);
	return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
	TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).extractPower(
		TransferPack.joulesVoltage(maxExtract, CapabilityElectrodynamic.DEFAULT_VOLTAGE), simulate);
	return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
    }

    @Override
    public int getEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE,
		this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored());
    }

    @Override
    public int getMaxEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE,
		this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getMaxJoulesStored());
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
