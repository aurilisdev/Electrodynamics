package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileBatteryBox;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerBatteryBox extends GenericContainerInventory {

	public ContainerBatteryBox(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(3));
	}

	public ContainerBatteryBox(int id, PlayerInventory playerinv, IInventory inventory) {
		this(id, playerinv, inventory, new IntArray(2));
	}

	public ContainerBatteryBox(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(DeferredRegisters.CONTAINER_BATTERYBOX.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 145, 14, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basiccapacity),
				DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedcapacity)));
		addSlot(new SlotRestricted(inv, nextIndex(), 145, 34, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basiccapacity),
				DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedcapacity)));
		addSlot(new SlotRestricted(inv, nextIndex(), 145, 54, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basiccapacity),
				DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedcapacity)));
	}

	@OnlyIn(Dist.CLIENT)
	public double getJoules() {
		return inventorydata.get(0) / 10000.0 * (TileBatteryBox.DEFAULT_MAX_JOULES * getCapacityMultiplier());
	}

	public double getCapacityMultiplier() {
		return inventorydata.get(1) / 10000.0;
	}
}
