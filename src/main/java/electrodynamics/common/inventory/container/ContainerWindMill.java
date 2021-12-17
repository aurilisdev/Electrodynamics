package electrodynamics.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileWindmill;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerWindMill extends GenericContainer<TileWindmill> {

	public ContainerWindMill(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
	}

	public ContainerWindMill(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(electrodynamics.DeferredRegisters.CONTAINER_WINDMILL.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new UpgradeSlot(inv, nextIndex(), 25, 42, SubtypeItemUpgrade.stator));
	}
}
