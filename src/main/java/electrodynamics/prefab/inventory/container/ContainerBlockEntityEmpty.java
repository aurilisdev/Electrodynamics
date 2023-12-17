package electrodynamics.prefab.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;

public class ContainerBlockEntityEmpty<T extends TileEntity> extends GenericContainerBlockEntity<T> {

	private static final IInventory EMPTY = new Inventory(0);

	public ContainerBlockEntityEmpty(ContainerType<?> type, int id, PlayerInventory playerinv, IIntArray inventorydata) {
		super(type, id, playerinv, EMPTY, inventorydata);
	}

	@Override
	public final void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		playerInvOffset += getPlayerInvOffset();
	}

	public int getPlayerInvOffset() {
		return 0;
	}

}
