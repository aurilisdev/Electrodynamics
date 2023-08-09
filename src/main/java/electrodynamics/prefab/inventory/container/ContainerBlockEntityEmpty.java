package electrodynamics.prefab.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ContainerBlockEntityEmpty<T extends BlockEntity> extends GenericContainerBlockEntity<T> {

	private static final Container EMPTY = new SimpleContainer(0);
	
	public ContainerBlockEntityEmpty(MenuType<?> type, int id, Inventory playerinv, ContainerData inventorydata) {
		super(type, id, playerinv, EMPTY, inventorydata);
	}

	@Override
	public final void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset += getPlayerInvOffset();
	}
	
	public int getPlayerInvOffset() {
		return 0;
	}

}
