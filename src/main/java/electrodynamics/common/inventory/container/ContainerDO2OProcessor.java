package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerDO2OProcessor extends GenericContainer<GenericTile> {

    public ContainerDO2OProcessor(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
    }

    public ContainerDO2OProcessor(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerDO2OProcessor(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new GenericSlot(inv, nextIndex(), 56, 19));
	addSlot(new GenericSlot(inv, nextIndex(), 56, 49));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116, 34));
	addSlot(new UpgradeSlot(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
		SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput));
	addSlot(new UpgradeSlot(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
		SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput));
	addSlot(new UpgradeSlot(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
		SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput));
    }
}
