package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.tile.generic.GenericTileTank;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerTankGeneric extends GenericContainerBlockEntity<GenericTileTank> {

	public ContainerTankGeneric(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(2), new SimpleContainerData(3));
	}

	public ContainerTankGeneric(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_TANK.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 27, 34, 0, CapabilityUtils.getFluidItemCap()));
		addSlot(new SlotRestricted(inv, nextIndex(), 133, 34, 0, CapabilityUtils.getFluidItemCap()));

	}

}
