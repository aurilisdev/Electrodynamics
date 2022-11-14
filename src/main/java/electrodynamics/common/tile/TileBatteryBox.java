package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
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
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class TileBatteryBox extends GenericTile implements IEnergyStorage {

	public final Property<Double> powerOutput;
	public final Property<Double> maxJoules;
	public Property<Double> clientMaxJoulesStored;
	public Property<Double> currentCapacityMultiplier = property(new Property<Double>(PropertyType.Double)).set(1.0);
	public Property<Double> currentVoltageMultiplier = property(new Property<Double>(PropertyType.Double)).set(1.0);
	public Property<Double> clientVoltage = property(new Property<Double>(PropertyType.Double)).set(120.0);
	public Property<Double> clientJoules = property(new Property<Double>(PropertyType.Double)).set(0.0);
	protected Property<Double> receiveLimitLeft;
	protected CachedTileOutput output;

	public TileBatteryBox(BlockPos worldPosition, BlockState blockState) {
		this(ElectrodynamicsBlockTypes.TILE_BATTERYBOX.get(), 359.0 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE / 20.0, 10000000, worldPosition, blockState);
	}

	public TileBatteryBox(BlockEntityType<?> type, double output, double max, BlockPos worldPosition, BlockState blockState) {
		super(type, worldPosition, blockState);
		powerOutput = property(new Property<Double>(PropertyType.Double)).set(output);
		maxJoules = property(new Property<Double>(PropertyType.Double)).set(max);
		clientMaxJoulesStored = property(new Property<Double>(PropertyType.Double)).set(max);
		receiveLimitLeft = property(new Property<Double>(PropertyType.Double)).set(output * currentCapacityMultiplier.getValue());
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentInventory(this).size(4).inputs(1).upgrades(3).validUpgrades(ContainerBatteryBox.VALID_UPGRADES).valid((i, s, c) -> i == 3 ? s.getItem() instanceof ItemElectric : machineValidator().test(i, s, c)));
		addComponent(new ComponentContainerProvider("container.batterybox").createMenu((id, player) -> new ContainerBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentElectrodynamic(this).maxJoules(max).relativeInput(Direction.SOUTH).relativeOutput(Direction.NORTH));
	}

	protected void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (tickable.getTicks() % 40 == 0) {
			output.update(worldPosition.relative(facing.getOpposite()));
		}
		receiveLimitLeft.set(powerOutput.getValue() * currentCapacityMultiplier.getValue());
		if (electro.getJoulesStored() > 0 && output.valid()) {
			electro.joules(electro.getJoulesStored() - ElectricityUtils.receivePower(output.getSafe(), facing, TransferPack.joulesVoltage(Math.min(electro.getJoulesStored(), powerOutput.getValue() * currentCapacityMultiplier.getValue()), electro.getVoltage()), false).getJoules());
		}
		currentCapacityMultiplier.set(1.0, true);
		currentVoltageMultiplier.set(1.0, true);
		for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade) {
				for (int i = 0; i < stack.getCount(); i++) {
					upgrade.subtype.applyUpgrade.accept(this, null, null);
				}
			}
		}
		electro.maxJoules(maxJoules.getValue() * currentCapacityMultiplier.getValue());
		electro.voltage(120.0 * currentVoltageMultiplier.getValue());
		if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
			electro.joules(electro.getMaxJoulesStored());
		}
		electro.drainElectricItem(3);
		clientMaxJoulesStored.set(this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getMaxJoulesStored());
		clientJoules.set(this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored());
		clientVoltage.set(this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction face) {
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		ComponentElectrodynamic electro = this.getComponent(ComponentType.Electrodynamic);
		if (electro.hasCapability(capability, face)) {
			return electro.getCapability(capability, face);
		}
		if (capability == ForgeCapabilities.ENERGY && (face == facing || face == facing.getOpposite())) {
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