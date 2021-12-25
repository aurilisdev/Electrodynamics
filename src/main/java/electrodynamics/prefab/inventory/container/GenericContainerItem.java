package electrodynamics.prefab.inventory.container;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.IItemHandler;

public abstract class GenericContainerItem extends GenericContainer {

	private IItemHandler handler;
	
	protected GenericContainerItem(MenuType<?> type, int id, Inventory playerinv, IItemHandler handler) {
		//the items have to be stored in the handler, so the container is just for indexing purposes
		super(type, id, playerinv, new SimpleContainer(handler.getSlots()));
		this.handler = handler;
	}
	
	public IItemHandler getHandler() {
		return handler;
	}

}
