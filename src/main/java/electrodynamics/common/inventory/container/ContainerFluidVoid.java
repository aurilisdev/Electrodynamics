package electrodynamics.common.inventory.container;

import electrodynamics.common.tile.TileFluidVoid;
import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;

public class ContainerFluidVoid extends GenericContainer<TileFluidVoid> {

	protected ContainerFluidVoid(MenuType<?> type, int id, Inventory playerinv, Container inventory,
			ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		
	}

}
