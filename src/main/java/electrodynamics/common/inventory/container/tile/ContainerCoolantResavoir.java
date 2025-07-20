package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import voltaic.prefab.inventory.container.slot.item.type.SlotFluid;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerCoolantResavoir extends GenericContainerBlockEntity<TileCoolantResavoir> {

	public ContainerCoolantResavoir(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_COOLANTRESAVOIR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerCoolantResavoir(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(1), new IntArray(5));
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotFluid(inv, nextIndex(), 47, 34));
	}

}
