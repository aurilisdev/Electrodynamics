package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.tile.TileCreativeFluidSource;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerCreativeFluidSource extends GenericContainer<TileCreativeFluidSource> {

	public ContainerCreativeFluidSource(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(2), new SimpleContainerData(3));
	}

	public ContainerCreativeFluidSource(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_CREATIVEFLUIDSOURCE.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerCreativeFluidSource(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 58, 34, 0, CapabilityUtils.getFluidItemCap()));
		addSlot(new SlotRestricted(inv, nextIndex(), 133, 34, 0, CapabilityUtils.getFluidItemCap()));
	}

}
