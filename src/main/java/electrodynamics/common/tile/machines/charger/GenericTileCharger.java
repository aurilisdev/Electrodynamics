package electrodynamics.common.tile.machines.charger;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GenericTileCharger extends GenericTile {

	private static final int BATTERY_COUNT = 3;
	private static final double MAX_BATTERY_TRANSFER_JOULES = 1000.0;

	protected GenericTileCharger(BlockEntityType<?> typeIn, int voltageMultiplier, SubtypeMachine machine, BlockPos worldPosition, BlockState blockState) {
		super(typeIn, worldPosition, blockState);
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickCommon(this::tickCommon));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * voltageMultiplier).maxJoules(2000.0 * voltageMultiplier));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(BATTERY_COUNT + 1).outputs(1)).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(machine, this).createMenu((id, player) -> new ContainerChargerGeneric(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

	public void tickCommon(ComponentTickable tickable) {
		ComponentInventory inventory = getComponent(IComponentType.Inventory);
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ItemStack itemInput = inventory.getItem(0);
		if (!itemInput.isEmpty() && itemInput.getItem() instanceof IItemElectric electricItem) {
			boolean hasOvervolted = false;
			if (inventory.inputs() > 1) {
				hasOvervolted = drainBatterySlots(inventory, electro);
			}
			double room = electricItem.getElectricProperties().capacity - electricItem.getJoulesStored(itemInput);
			if (electro.getJoulesStored() > 0 && !hasOvervolted && room > 0) {
				double recieveVoltage = electricItem.getElectricProperties().receive.getVoltage();
				double machineVoltage = electro.getVoltage();
				if (machineVoltage > recieveVoltage) {
					electricItem.overVoltage(TransferPack.joulesVoltage(electricItem.getElectricProperties().receive.getJoules(), machineVoltage));
					level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
					level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 2f, ExplosionInteraction.BLOCK);
				} else if (machineVoltage == recieveVoltage) {
					electro.joules(electro.getJoulesStored() - electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored(), machineVoltage), false).getJoules());
				} else {
					float underVoltRatio = (float) ((float) machineVoltage / recieveVoltage);
					float itemStoredRatio = (float) ((float) electricItem.getJoulesStored(itemInput) / electricItem.getElectricProperties().capacity);

					float x = Math.abs(itemStoredRatio / (itemStoredRatio - underVoltRatio + 0.00000001F/* ensures it's never zero */));
					float reductionCoef = getRationalFunctionValue(x);
					if (itemStoredRatio >= underVoltRatio) {
						electricItem.extractPower(itemInput, electro.getJoulesStored() * reductionCoef, false);
					} else {
						electro.joules(electro.getJoulesStored() - electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored() * reductionCoef, recieveVoltage), false).getJoules());
					}
				}
				if (electricItem.getJoulesStored(itemInput) == electricItem.getElectricProperties().capacity && inventory.getItem(4).isEmpty()) {
					inventory.setItem(4, inventory.getItem(0).copy());
					inventory.getItem(0).shrink(1);
				}
				inventory.setChanged();
			}
		}

	}

	// to simulate undervolting a chargeable object
	private static float getRationalFunctionValue(float x) {
		if (x >= 100.0F) {
			return 0.0F;
		}
		if (x <= 1.0F) {
			return 1.0F;
		}
		return 1 / x;
	}

	private boolean drainBatterySlots(ComponentInventory inv, ComponentElectrodynamic electro) {
		double machineVoltage = electro.getVoltage();
		double battVoltage = 0;
		for (int i = 0; i < BATTERY_COUNT; i++) {
			ItemStack battery = inv.getItem(i + 1);
			if (!battery.isEmpty() && battery.getItem() instanceof IItemElectric electricItem) {
				battVoltage = electricItem.getElectricProperties().receive.getVoltage();
				if (battVoltage < machineVoltage) {
					inv.setItem(i + 1, new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()).copy());
					getLevel().playSound(null, getBlockPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1F, 1F);
				} else if (battVoltage > machineVoltage) {
					electro.overVoltage(TransferPack.joulesVoltage(electro.getJoulesStored(), battVoltage));
					return true;
				} else if (electro.getMaxJoulesStored() - electro.getJoulesStored() > 0) {
					electro.joules(electro.getJoulesStored() + electricItem.extractPower(battery, MAX_BATTERY_TRANSFER_JOULES, false).getJoules());
				}
			}
		}
		return false;
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.1875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0.25, 0.125, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0.375, 0.625, 0.5625, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 0.9375, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.9375, 0.25, 0.6875, 1, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.875, 0.3125, 0.53125, 0.9375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.875, 0.5625, 0.53125, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.375, 0.1875, 0.6875, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.46875, 0.25, 0.625, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 1, 0.4375, 0.4375, 1.125, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.875, 0.46875, 0.375, 1.0625, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.59375, 0.46875, 0.3125, 0.90625, 0.53125), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.chargerlv, shape, Direction.EAST);
		shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.1875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0.25, 0.125, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.09375, 0.34375, 0.5, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.71875, 0.34375, 0.5, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.45, 0.75, 0.3125, 0.5125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.84375, 0.28125, 0.5625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.71875, 0.28125, 0.5625, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.45, 0.125, 0.3125, 0.5125, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.21875, 0.28125, 0.5625, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.09375, 0.28125, 0.5625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0.375, 0.625, 0.5625, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.4375, 0.5625, 0.9375, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.9375, 0.25, 0.6875, 1, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.875, 0.3125, 0.53125, 0.9375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.875, 0.5625, 0.53125, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 1, 0.4375, 0.4375, 1.125, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.375, 0.1875, 0.6875, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.53125, 0.25, 0.625, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.40625, 0.25, 0.625, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.21875, 0.28125, 0.6265625, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.53125, 0.28125, 0.6265625, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.1875, 0.84375, 0.46875, 0.5625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.84375, 0.46875, 0.625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.09375, 0.46875, 0.625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.1875, 0.09375, 0.46875, 0.5625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.09375, 0.84375, 0.25, 0.34375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.65625, 0.84375, 0.25, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.25, 0.59375, 0.84375, 0.3125, 0.71875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.25, 0.28125, 0.84375, 0.3125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 0.09375, 0.78125, 0.25, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 0.84375, 0.78125, 0.25, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.1875, 0.40625, 0.78125, 0.25, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.3125, 0.40625, 0.78125, 0.375, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.1875, 0.40625, 0.90625, 0.25, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.3125, 0.40625, 0.90625, 0.375, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.40625, 0.84375, 0.25, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.3125, 0.40625, 0.84375, 0.375, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.53125, 0.84375, 0.25, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.3125, 0.53125, 0.84375, 0.375, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.25, 0.53125, 0.78125, 0.3125, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.25, 0.53125, 0.90625, 0.3125, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.25, 0.40625, 0.78125, 0.3125, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.25, 0.40625, 0.90625, 0.3125, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.375, 0.46875, 0.84375, 0.5, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.46875, 0.5, 0.8125, 0.53125, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.5, 0.53125, 0.78125, 0.5625, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.53125, 0.5625, 0.75, 0.59375, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.5625, 0.59375, 0.71875, 0.625, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.59375, 0.5625, 0.4375, 0.65625, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.65625, 0.5, 0.375, 0.71875, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.34375, 0.625, 0.53125, 0.40625, 0.6875, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.6875, 0.46875, 0.375, 1.0625, 0.53125), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.chargermv, shape, Direction.EAST);
		shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.1875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0.25, 0.125, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.45, 0.75, 0.3125, 0.5125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.84375, 0.28125, 0.5625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.71875, 0.28125, 0.5625, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.45, 0.75, 0.625, 0.5125, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.53125, 0.5, 0.84375, 0.59375, 0.5625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.53125, 0.5, 0.71875, 0.59375, 0.5625, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.09375, 0.34375, 0.5, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 0.09375, 0.65625, 0.5, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 0.71875, 0.65625, 0.5, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.1875, 0.71875, 0.34375, 0.5, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.45, 0.125, 0.3125, 0.5125, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.21875, 0.28125, 0.5625, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5, 0.09375, 0.28125, 0.5625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.45, 0.125, 0.625, 0.5125, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.53125, 0.5, 0.21875, 0.59375, 0.5625, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.53125, 0.5, 0.09375, 0.59375, 0.5625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0.375, 0.625, 0.5625, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.40625, 0.5625, 0.9375, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.53125, 0.5625, 0.9375, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.9375, 0.25, 0.6875, 1, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.875, 0.3125, 0.53125, 0.9375, 0.4375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.875, 0.5625, 0.53125, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 1, 0.4375, 0.4375, 1.125, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.375, 0.3125, 0.6875, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.21875, 0.25, 0.6265625, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.21718749999999998, 0.59375, 0.625, 0.2796875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.7203125, 0.59375, 0.625, 0.7828125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.5625, 0.59375, 0.25, 0.6265625, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.84375, 0.78125, 0.625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.1875, 0.84375, 0.78125, 0.5625, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.71875, 0.84375, 0.25, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.25, 0.65625, 0.84375, 0.3125, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.5625, 0.09375, 0.78125, 0.625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.1875, 0.09375, 0.78125, 0.5625, 0.15625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.09375, 0.84375, 0.25, 0.28125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.25, 0.21875, 0.84375, 0.3125, 0.34375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.1875, 0.34375, 0.78125, 0.25, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.3125, 0.34375, 0.78125, 0.375, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.1875, 0.34375, 0.90625, 0.25, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.3125, 0.34375, 0.90625, 0.375, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.34375, 0.84375, 0.25, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.3125, 0.34375, 0.84375, 0.375, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.1875, 0.59375, 0.84375, 0.25, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.3125, 0.53125, 0.84375, 0.375, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.25, 0.59375, 0.78125, 0.3125, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.25, 0.53125, 0.90625, 0.3125, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.25, 0.34375, 0.78125, 0.3125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.25, 0.34375, 0.90625, 0.3125, 0.46875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.78125, 0.375, 0.46875, 0.84375, 0.5625, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.53125, 0.46875, 0.8125, 0.59375, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.5625, 0.46875, 0.78125, 0.625, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.625, 0.46875, 0.71875, 0.6875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.59375, 0.46875, 0.75, 0.65625, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.46875, 0.6875, 0.6875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.65625, 0.46875, 0.46875, 0.71875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.34375, 0.71875, 0.46875, 0.40625, 0.78125, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.6875, 0.46875, 0.4375, 0.75, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.75, 0.46875, 0.375, 1.0625, 0.53125), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.chargerhv, shape, Direction.EAST);
	}
}
