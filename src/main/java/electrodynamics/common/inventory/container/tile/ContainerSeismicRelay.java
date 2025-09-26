package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerSeismicRelay extends GenericContainerBlockEntity<TileSeismicRelay> {

	public ContainerSeismicRelay(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_SEISMICRELAY.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerSeismicRelay(int id, PlayerInventory playerinv) {
		super(ElectrodynamicsMenuTypes.CONTAINER_SEISMICRELAY.get(), id, playerinv, new Inventory(1), new IntArray(5));
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 20, 34));
	}

}
