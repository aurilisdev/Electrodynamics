package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerCombustionChamber extends GenericContainer<TileCombustionChamber> {

	public ContainerCombustionChamber(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
	}

	public ContainerCombustionChamber(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_COMBUSTION_CHAMBER.get(), id, playerinv, new SimpleContainer(), inventorydata);
	}

	public ContainerCombustionChamber(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		// TODO doesnt have slots
	}
}
