package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ContainerChemicalCrystallizer extends GenericContainer<TileChemicalCrystallizer> {

    public ContainerChemicalCrystallizer(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(5), new IntArray(3));
    }

    public ContainerChemicalCrystallizer(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_CHEMICALCRYSTALLIZER.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerChemicalCrystallizer(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 82, 31));
	addSlot(new SlotRestricted(inv, nextIndex(), 82, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
	addSlot(new SlotRestricted(inv, nextIndex(), 150, 14,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 150, 34,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 150, 54,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    }
}
