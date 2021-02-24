package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerElectricFurnace extends ContainerO2OProcessor {

    public ContainerElectricFurnace(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(5));
    }

    public ContainerElectricFurnace(int id, PlayerInventory playerinv, IInventory inventory) {
	this(id, playerinv, inventory, new IntArray(4));
    }

    public ContainerElectricFurnace(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), id, playerinv, inventory, inventorydata);
    }
}
