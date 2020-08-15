package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.slot.GenericSlot;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.common.tile.TileElectricFurnace;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerElectricFurnace extends GenericContainerInventory {

	public ContainerElectricFurnace(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(5));
	}

	public ContainerElectricFurnace(int id, PlayerInventory playerinv, IInventory inventory) {
		this(id, playerinv, inventory, new IntArray(1));
	}

	public ContainerElectricFurnace(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new GenericSlot(inv, nextIndex(), 56, 34));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 35));
		addSlot(new SlotRestricted(inv, nextIndex(), 186, 14, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed)));
		addSlot(new SlotRestricted(inv, nextIndex(), 186, 34, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed)));
		addSlot(new SlotRestricted(inv, nextIndex(), 186, 54, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed)));
	}

	@OnlyIn(Dist.CLIENT)
	public int getBurnLeftScaled() {
		return inventorydata.get(0) * 24 / TileElectricFurnace.REQUIRED_TICKS;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return inventorydata.get(0) > 0;
	}

}
