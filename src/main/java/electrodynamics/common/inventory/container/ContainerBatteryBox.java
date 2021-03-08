package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.TileBatteryBox;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerBatteryBox extends GenericContainerInventory<TileBatteryBox> {

    public ContainerBatteryBox(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(9));
    }

    public ContainerBatteryBox(int id, PlayerInventory playerinv, IInventory inventory) {
	this(id, playerinv, inventory, new IntArray(3));
    }

    public ContainerBatteryBox(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_BATTERYBOX.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
    }

}
