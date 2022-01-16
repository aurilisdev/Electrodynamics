package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotFluid;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerChemicalCrystallizer extends GenericContainerBlockEntity<TileChemicalCrystallizer> {

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
		addSlot(new SlotFluid(inv, nextIndex(), 82, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience));
	}
}
