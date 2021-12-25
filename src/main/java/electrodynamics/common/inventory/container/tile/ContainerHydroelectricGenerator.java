package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerHydroelectricGenerator extends GenericContainerBlockEntity<TileHydroelectricGenerator> {

	public ContainerHydroelectricGenerator(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
	}

	public ContainerHydroelectricGenerator(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_HYDROELECTRICGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new UpgradeSlot(inv, nextIndex(), 25, 42, SubtypeItemUpgrade.stator));
	}
}
