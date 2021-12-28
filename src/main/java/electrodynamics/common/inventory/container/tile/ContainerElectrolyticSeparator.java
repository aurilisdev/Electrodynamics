package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileElectrolyticSeparator;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ContainerElectrolyticSeparator extends GenericContainerBlockEntity<TileElectrolyticSeparator> {

	public ContainerElectrolyticSeparator(MenuType<?> type, int id, Inventory playerinv, Container inventory,
			ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}
	
	public ContainerElectrolyticSeparator(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}
	
	public ContainerElectrolyticSeparator(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_ELECTROLYTICSEPARATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 40, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
		addSlot(new SlotRestricted(inv, nextIndex(), 81, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
		addSlot(new SlotRestricted(inv, nextIndex(), 121, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
		addSlot(new UpgradeSlot(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
		addSlot(new UpgradeSlot(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
		addSlot(new UpgradeSlot(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed));
	}

}
