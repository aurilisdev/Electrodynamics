package electrodynamics.common.inventory.container.item;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class ContainerGuidebook extends GenericContainer {

	public ContainerGuidebook(int id, Inventory playerinv) {
		super(DeferredRegisters.CONTAINER_GUIDEBOOK.get(), id, playerinv);
	} 

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		//DO NOT USE
	}

}
