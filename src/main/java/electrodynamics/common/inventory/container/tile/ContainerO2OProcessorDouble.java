package electrodynamics.common.inventory.container.tile;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerO2OProcessorDouble extends GenericContainerBlockEntity<GenericTile> {

	public ContainerO2OProcessorDouble(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(9), new SimpleContainerData(3));
	}

	public ContainerO2OProcessorDouble(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSORDOUBLE.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerO2OProcessorDouble(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 24));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 24));
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 44));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 44));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 24));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 44));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ContainerO2OProcessor.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ContainerO2OProcessor.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ContainerO2OProcessor.VALID_UPGRADES));
	}
}