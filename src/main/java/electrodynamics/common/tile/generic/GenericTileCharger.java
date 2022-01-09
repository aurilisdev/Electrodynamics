package electrodynamics.common.tile.generic;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class GenericTileCharger extends GenericTile {

	protected GenericTileCharger(BlockEntityType<?> typeIn, int voltageMultiplier, String containerName, BlockPos worldPosition,
			BlockState blockState) {
		super(typeIn, worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler().guiPacketReader(this::loadFromNBT).guiPacketWriter(this::saveToNBT));
		addComponent(new ComponentTickable().tickCommon(this::tickCommon));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
				.voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * voltageMultiplier).maxJoules(1000.0 * voltageMultiplier));
		addComponent(new ComponentInventory(this).size(2).valid((slot, stack, i) -> slot < 1));
		addComponent(new ComponentContainerProvider("container.charger" + containerName)
				.createMenu((id, player) -> new ContainerChargerGeneric(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	public void tickCommon(ComponentTickable tickable) {
		ComponentInventory inventory = getComponent(ComponentType.Inventory);
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		ItemStack itemInput = inventory.getItem(0);
		if (!itemInput.isEmpty() && electro.getJoulesStored() == electro.getMaxJoulesStored()
				&& itemInput.getItem() instanceof IItemElectric electricItem) {
			double recieveVoltage = electricItem.getElectricProperties().receive.getVoltage();
			double machineVoltage = electro.getVoltage();

			if (machineVoltage > recieveVoltage) {
				electricItem.overVoltage(TransferPack.joulesVoltage(electricItem.getElectricProperties().receive.getJoules(), machineVoltage));
				level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
				level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 2f, BlockInteraction.DESTROY);
			} else if (machineVoltage == recieveVoltage) {
				electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored(), machineVoltage), false);
				electro.extractPower(
						electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored(), machineVoltage), false), false);
			} else {
				float underVoltRatio = (float) ((float) machineVoltage / recieveVoltage);
				float itemStoredRatio = (float) ((float) electricItem.getJoulesStored(itemInput) / electricItem.getElectricProperties().capacity);

				float x = Math.abs(itemStoredRatio / (itemStoredRatio - underVoltRatio + 0.00000001F/* ensures it's never zero */));
				float reductionCoef = getRationalFunctionValue(x);
				if (itemStoredRatio >= underVoltRatio) {
					electricItem.extractPower(itemInput, electro.getJoulesStored() * reductionCoef, false);
				} else {
					electricItem.receivePower(itemInput, TransferPack.joulesVoltage(electro.getJoulesStored() * reductionCoef, recieveVoltage),
							false);
					electro.extractPower(TransferPack.joulesVoltage(electro.getMaxJoulesStored() * reductionCoef, recieveVoltage), false);
				}
			}
			if (electricItem.getJoulesStored(itemInput) == electricItem.getElectricProperties().capacity && inventory.getItem(1).isEmpty()) {
				inventory.setItem(1, inventory.getItem(0).copy());
				inventory.getItem(0).shrink(1);
			}
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}

	}

	// to simulate undervolting a chargeable object
	private static float getRationalFunctionValue(float x) {
		if (x >= 100.0F) {
			return 0.0F;
		} else if (x <= 1.0F) {
			return 1.0F;
		} else {
			return 1 / x;
		}
	}

	protected void loadFromNBT(CompoundTag nbt) {
		NonNullList<ItemStack> obj = this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems();
		obj.clear();
		ContainerHelper.loadAllItems(nbt, obj);
	}

	protected void saveToNBT(CompoundTag nbt) {
		ContainerHelper.saveAllItems(nbt, this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems());
	}

}
