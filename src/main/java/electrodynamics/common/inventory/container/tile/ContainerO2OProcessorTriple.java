package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerO2OProcessorTriple extends GenericContainerBlockEntity<GenericTile> {

	public ContainerO2OProcessorTriple(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(12), new SimpleContainerData(3));
	}

	public ContainerO2OProcessorTriple(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_O2OPROCESSORTRIPLE.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerO2OProcessorTriple(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset = 20;
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 24));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 24));
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 44));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 44));
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 64));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 64));

		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 24));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 44));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 64));

		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 24, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 44, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 64, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience));
	}
}