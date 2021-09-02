package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.tile.generic.TileGenericTank;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerTankGeneric extends GenericContainer<TileGenericTank>{


	public ContainerTankGeneric(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(9), new IntArray(3));
    }
	
	public ContainerTankGeneric(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(DeferredRegisters.CONTAINER_TANK.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 27, 34, 0, CapabilityUtils.getFluidItemCap()));
		addSlot(new SlotRestricted(inv, nextIndex(), 133, 34, 0, CapabilityUtils.getFluidItemCap()));
		
	}

}
