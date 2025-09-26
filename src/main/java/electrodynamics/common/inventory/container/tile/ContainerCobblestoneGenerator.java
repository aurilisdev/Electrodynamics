package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.machines.TileCobblestoneGenerator;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.utilities.math.Color;

public class ContainerCobblestoneGenerator extends GenericContainerBlockEntity<TileCobblestoneGenerator> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput };

	public ContainerCobblestoneGenerator(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(4), new IntArray(5));
	}

	public ContainerCobblestoneGenerator(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_COBBLESTONEGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 68, 34).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 54, VALID_UPGRADES));

	}

}
