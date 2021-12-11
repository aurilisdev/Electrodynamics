package electrodynamics.common.inventory.container;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ContainerChemicalCrystallizer extends GenericContainer<TileChemicalCrystallizer> {

    public ContainerChemicalCrystallizer(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(5), new SimpleContainerData(3));
    }

    public ContainerChemicalCrystallizer(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_CHEMICALCRYSTALLIZER.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerChemicalCrystallizer(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 82, 31));
	addSlot(new SlotRestricted(inv, nextIndex(), 82, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
	addSlot(new UpgradeSlot(inv, nextIndex(), 150, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput));
	addSlot(new UpgradeSlot(inv, nextIndex(), 150, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput));
	addSlot(new UpgradeSlot(inv, nextIndex(), 150, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput));
    }
}
