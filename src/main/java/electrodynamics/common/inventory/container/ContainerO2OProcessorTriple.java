package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerO2OProcessorTriple extends GenericContainer<GenericTile> {

    public ContainerO2OProcessorTriple(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(9), new IntArray(3));
    }

    public ContainerO2OProcessorTriple(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_O2OPROCESSORTRIPLE.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerO2OProcessorTriple(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	playerInvOffset = 20;
	addSlot(new GenericSlot(inv, nextIndex(), 56, 24));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 24));
	addSlot(new GenericSlot(inv, nextIndex(), 56, 44));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 44));
	addSlot(new GenericSlot(inv, nextIndex(), 56, 64));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 64));
	addSlot(new SlotRestricted(inv, nextIndex(), 153, 14, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 153, 34, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 153, 54, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    }
}