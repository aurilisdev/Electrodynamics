package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
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
	public Property<Double> currentCapacityMultiplier = property(new Property<>(PropertyType.Double, "currentCapacityMultiplier", 1.0));
	public Property<Double> currentVoltageMultiplier = property(new Property<>(PropertyType.Double, "currentVoltageMultiplier", 1.0));
	protected Property<Double> receiveLimitLeft;
	protected CachedTileOutput output;

	public final int baseVoltage;

	public TileBatteryBox(BlockPos worldPosition, BlockState blockState) {
		this(ElectrodynamicsBlockTypes.TILE_BATTERYBOX.get(), SubtypeMachine.batterybox, 120, 359.0 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE / 20.0, 10000000, worldPosition, blockState);
	}

	public TileBatteryBox(BlockEntityType<?> type, SubtypeMachine machine, int baseVoltage, double output, double max, BlockPos worldPosition, BlockState blockState) {
		super(type, worldPosition, blockState);
		this.baseVoltage = baseVoltage;
		powerOutput = property(new Property<>(PropertyType.Double, "powerOutput", output));
		maxJoules = property(new Property<>(PropertyType.Double, "maxJoulesStored", max));
		receiveLimitLeft = property(new Property<>(PropertyType.Double, "receiveLimitLeft", output * currentCapacityMultiplier.get()));
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentInventory(this).size(4).inputs(1).upgrades(3).validUpgrades(ContainerBatteryBox.VALID_UPGRADES).valid((i, s, c) -> i == 3 ? s.getItem() instanceof ItemElectric : machineValidator().test(i, s, c)));
		addComponent(new ComponentContainerProvider(machine).createMenu((id, player) -> new ContainerBatteryBox(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentElectrodynamic(this).voltage(baseVoltage).maxJoules(max).relativeInput(Direction.SOUTH).relativeOutput(Direction.NORTH));

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
		if (electro.getJoulesStored() > 0 && output.valid()) {
			electro.joules(electro.getJoulesStored() - ElectricityUtils.receivePower(output.getSafe(), facing, TransferPack.joulesVoltage(Math.min(electro.getJoulesStored(), powerOutput.get() * currentCapacityMultiplier.get()), electro.getVoltage()), false).getJoules());
		}
		if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
			electro.joules(electro.getMaxJoulesStored());
		}
		electro.drainElectricItem(3);
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

	//this is changed so all the battery boxes can convert FE to joules, regardless of voltage
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		
		int accepted = Math.min(maxReceive, (int) (electro.getMaxJoulesStored() - electro.getJoulesStored()));
		
		if(!simulate) {
			electro.joules(electro.getJoulesStored() + accepted);
		}
		
		return accepted;
	}

	//we still mandate 120V for all FE cables here though
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		
		int taken = Math.min(maxExtract, (int) electro.getJoulesStored());
		
		if(!simulate) {
		
			electro.joules(electro.getJoulesStored() - taken);
			
			if(electro.getVoltage() > ElectrodynamicsCapabilities.DEFAULT_VOLTAGE) {
				electro.overVoltage(TransferPack.joulesVoltage(taken, electro.getVoltage()));
			}
			
		}
		
		return taken;
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

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);
		if (inv.getUpgradeContents().size() > 0 && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
			ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

			currentCapacityMultiplier.set(1.0);
			currentVoltageMultiplier.set(1.0);

			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype.isEmpty) {
					for (int i = 0; i < stack.getCount(); i++) {
						if (upgrade.subtype == SubtypeItemUpgrade.basiccapacity) {
							currentCapacityMultiplier.set(Math.min(currentCapacityMultiplier.get() * 1.5, Math.pow(1.5, 3)));
							currentVoltageMultiplier.set(Math.min(currentVoltageMultiplier.get() * 2, 2));
						} else if (upgrade.subtype == SubtypeItemUpgrade.advancedcapacity) {
							currentCapacityMultiplier.set(Math.min(currentCapacityMultiplier.get() * 2.25, Math.pow(2.25, 3)));
							currentVoltageMultiplier.set(Math.min(currentVoltageMultiplier.get() * 4, 4));
						}
					}
				}
			}

			receiveLimitLeft.set(powerOutput.get() * currentCapacityMultiplier.get());
			electro.maxJoules(maxJoules.get() * currentCapacityMultiplier.get());
			electro.voltage(baseVoltage * currentVoltageMultiplier.get());
		}
	}

}