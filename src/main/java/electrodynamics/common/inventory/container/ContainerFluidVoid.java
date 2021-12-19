package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.tile.TileFluidVoid;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerFluidVoid extends GenericContainer<TileFluidVoid> {

	public ContainerFluidVoid(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
	}

	public ContainerFluidVoid(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_FLUIDVOID.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerFluidVoid(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 80, 34, 0, CapabilityUtils.getFluidItemCap()));
	}

}
