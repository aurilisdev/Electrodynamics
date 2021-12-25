package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileCobblestoneGenerator;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerCobblestoneGenerator extends GenericContainerBlockEntity<TileCobblestoneGenerator> {

	public ContainerCobblestoneGenerator(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(4), new SimpleContainerData(3));
	}

	public ContainerCobblestoneGenerator(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_COBBLESTONEGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerCobblestoneGenerator(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 68, 34));
		addSlot(new UpgradeSlot(inv, nextIndex(), 150, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput));
		addSlot(new UpgradeSlot(inv, nextIndex(), 150, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput));
		addSlot(new UpgradeSlot(inv, nextIndex(), 150, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput));

	}

}