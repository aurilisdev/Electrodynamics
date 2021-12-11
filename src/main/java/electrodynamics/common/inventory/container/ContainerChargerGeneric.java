package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.GenericTileCharger;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerChargerGeneric extends GenericContainer<GenericTileCharger> {

    public ContainerChargerGeneric(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(9), new SimpleContainerData(3));
    }

    public ContainerChargerGeneric(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_CHARGER.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new GenericSlot(inv, nextIndex(), 95, 34));
	addSlot(new SlotRestricted(inv, nextIndex(), 145, 34));
    }

}
