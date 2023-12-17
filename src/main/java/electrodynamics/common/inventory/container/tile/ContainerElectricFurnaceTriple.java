package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.machines.furnace.TileElectricFurnaceTriple;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.utilities.math.Color;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerElectricFurnaceTriple extends GenericContainerBlockEntity<TileElectricFurnaceTriple> {

	public ContainerElectricFurnaceTriple(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(9), new IntArray(3));
	}

	public ContainerElectricFurnaceTriple(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACETRIPLE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		playerInvOffset = 20;
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 24).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 44).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotGeneric(inv, nextIndex(), 56, 64).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 24).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 44).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 64).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ContainerElectricFurnace.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ContainerElectricFurnace.VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ContainerElectricFurnace.VALID_UPGRADES));
	}
}