package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import electrodynamics.prefab.item.ItemElectric;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerLithiumBatteryBox extends GenericContainer<TileLithiumBatteryBox> {

    public ContainerLithiumBatteryBox(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(5), new SimpleContainerData(3));
    }

    public ContainerLithiumBatteryBox(int id, Inventory pinv, Container inv, ContainerData data) {
	super(DeferredRegisters.CONTAINER_LITHIUMBATTERYBOX.get(), id, pinv, inv, data);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new UpgradeSlot(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity));
	addSlot(new UpgradeSlot(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity));
	addSlot(new UpgradeSlot(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.advancedcapacity, SubtypeItemUpgrade.basiccapacity));
	addSlot(new SlotRestricted(inv, nextIndex(), 133, 14, false, ItemElectric.class));
    }

}