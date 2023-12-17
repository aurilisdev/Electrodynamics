package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.machines.charger.GenericTileCharger;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotCharging;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerChargerGeneric extends GenericContainerBlockEntity<GenericTileCharger> {

	public ContainerChargerGeneric(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(5), new IntArray(3));
	}

	public ContainerChargerGeneric(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_CHARGER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotCharging(inv, nextIndex(), 95, 34).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotCharging(inv, nextIndex(), 95, 54));
		addSlot(new SlotCharging(inv, nextIndex(), 115, 54));
		addSlot(new SlotCharging(inv, nextIndex(), 135, 54));
		addSlot(new SlotRestricted(inv, nextIndex(), 145, 34).setIOColor(new Color(255, 0, 0, 255)));
	}

}