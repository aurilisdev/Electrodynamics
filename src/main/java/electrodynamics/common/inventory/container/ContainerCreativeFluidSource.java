package electrodynamics.common.inventory.container;

import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;

public class ContainerCreativeFluidSource extends GenericContainer<TileCreativeFluidSource> {

	protected ContainerCreativeFluidSource(MenuType<?> type, int id, Inventory playerinv, Container inventory,
			ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		
	}

}