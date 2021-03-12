package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.tile.TileCoalGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerCoalGenerator extends GenericContainer<TileCoalGenerator> {

    public ContainerCoalGenerator(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(1));
    }

    public ContainerCoalGenerator(int id, PlayerInventory playerinv, IInventory inventory) {
	this(id, playerinv, inventory, new IntArray(3));
    }

    public ContainerCoalGenerator(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_COALGENERATOR.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new SlotRestricted(inv, nextIndex(), 25, 42, Items.CHARCOAL, Items.COAL));
    }

}
