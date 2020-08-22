package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.slot.GenericSlot;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerDO2OProcessor extends GenericContainerInventory {

	public ContainerDO2OProcessor(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(6));
	}

	public ContainerDO2OProcessor(int id, PlayerInventory playerinv, IInventory inventory) {
		this(id, playerinv, inventory, new IntArray(4));
	}

	public ContainerDO2OProcessor(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerDO2OProcessor(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new GenericSlot(inv, nextIndex(), 56, 19));
		addSlot(new GenericSlot(inv, nextIndex(), 56, 49));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 35));
		addSlot(new SlotRestricted(inv, nextIndex(), 186, 14, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
				DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
		addSlot(new SlotRestricted(inv, nextIndex(), 186, 34, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
				DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
		addSlot(new SlotRestricted(inv, nextIndex(), 186, 54, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
				DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	}

	@OnlyIn(Dist.CLIENT)
	public int getBurnLeftScaled() {
		return inventorydata.get(0) * 24 / (inventorydata.get(3) == 0 ? 1 : inventorydata.get(3));
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isProcessing() {
		return inventorydata.get(0) > 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getVoltage() {
		return inventorydata.get(1);
	}

	@OnlyIn(Dist.CLIENT)
	public int getJoulesPerTick() {
		return inventorydata.get(2);
	}

}
