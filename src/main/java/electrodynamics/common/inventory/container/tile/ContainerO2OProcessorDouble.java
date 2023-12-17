package electrodynamics.common.inventory.container.tile;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerO2OProcessorDouble extends GenericContainerBlockEntity<GenericTile> {

	public ContainerO2OProcessorDouble(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(9), new IntArray(3));
	}

	public ContainerO2OProcessorDouble(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSORDOUBLE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 24).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerO2OProcessor.startXOffset, 44).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 24).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset, 44).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 24).setIOColor(new Color(255, 255, 0, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerO2OProcessor.startXOffset + 20, 44).setIOColor(new Color(255, 255, 0, 255)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ContainerO2OProcessor.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ContainerO2OProcessor.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ContainerO2OProcessor.VALID_UPGRADES));
	}
}