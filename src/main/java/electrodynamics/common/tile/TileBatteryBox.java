package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileBatteryBox extends GenericTile implements IEnergyStorage {

	public final double powerOutput;
	public final double maxJoules;
	public double clientMaxJoulesStored;
	public double currentCapacityMultiplier = 1;
	public double currentVoltageMultiplier = 1;
	public double clientVoltage = 120.0;
	public double clientJoules = 0;
	protected double receiveLimitLeft;
	protected CachedTileOutput output;

	public TileBatteryBox(BlockPos worldPosition, BlockState blockState) {
		this(DeferredRegisters.TILE_BATTERYBOX.get(), 359.0 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE / 20.0, 10000000, worldPosition, blockState);
	}

	public TileBatteryBox(BlockEntityType<?> type, double output, double max, BlockPos worldPosition, BlockState blockState) {
		super(type, worldPosition, blockState);
		powerOutput = output;
		maxJoules = max;
		clientMaxJoulesStored = max;
		receiveLimitLeft = output * currentCapacityMultiplier;
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket).customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
		addComponent(new ComponentInventory(this).size(4).inputs(1).upgrades(3).validUpgrades(ContainerBatteryBox.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.batterybox").createMenu((id, player) -> new ContainerBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentElectrodynamic(this).maxJoules(maxJoules).relativeInput(Direction.SOUTH).relativeOutput(Direction.NORTH));
	}

	protected void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (tickable.getTicks() % 40 == 0) {
			output.update();
		}
		receiveLimitLeft = powerOutput * currentCapacityMultiplier;
		if (electro.getJoulesStored() > 0 && output.valid()) {
			electro.joules(electro.getJoulesStored() - ElectricityUtils.receivePower(output.getSafe(), facing, TransferPack.joulesVoltage(Math.min(electro.getJoulesStored(), powerOutput * currentCapacityMultiplier), electro.getVoltage()), false).getJoules());
		}
		currentCapacityMultiplier = 1;
		currentVoltageMultiplier = 1;
		for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade) {
				for (int i = 0; i < stack.getCount(); i++) {
					upgrade.subtype.applyUpgrade.accept(this, null, null);
				}
			}
		}
		electro.maxJoules(maxJoules * currentCapacityMultiplier);
		electro.voltage(120.0 * currentVoltageMultiplier);
		if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
			electro.joules(electro.getMaxJoulesStored());
		}
		if (tickable.getTicks() % 50 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		}
		electro.drainElectricItem(3);

		// Power loss
		electro.extractPower(TransferPack.joulesVoltage(SubtypeWire.copper.resistance, electro.getVoltage()), false);
	}

	protected void createPacket(CompoundTag nbt) {
		nbt.putDouble("clientMaxJoulesStored", this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getMaxJoulesStored());
		nbt.putDouble("clientJoules", this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored());
		nbt.putDouble("clientVoltage", this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
		nbt.putDouble("currentCapacityMultiplier", currentCapacityMultiplier);
	}

	protected void readPacket(CompoundTag nbt) {
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
		TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).receivePower(TransferPack.joulesVoltage(maxReceive, ElectrodynamicsCapabilities.DEFAULT_VOLTAGE), simulate);
		return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).extractPower(TransferPack.joulesVoltage(maxExtract, ElectrodynamicsCapabilities.DEFAULT_VOLTAGE), simulate);
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