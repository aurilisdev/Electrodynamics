package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.common.item.ItemUpgrade;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.utilities.math.Color;

public class ContainerDO2OProcessor extends GenericContainerBlockEntity<GenericTile> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience };

	public static final int startXOffset = 36;

	public ContainerDO2OProcessor(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(7), new SimpleContainerData(3));
	}

	public ContainerDO2OProcessor(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_DO2OPROCESSOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - startXOffset, 19).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotGeneric(inv, nextIndex(), 56 - startXOffset, 49).setIOColor(new Color(144, 0, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116 - startXOffset, 34).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116 - startXOffset + 20, 34).setIOColor(new Color(255, 255, 0, 255)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ElectrodynamicsItems.ITEMS_UPGRADE.getSpecificValuesArray(new ItemUpgrade[0], VALID_UPGRADES)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ElectrodynamicsItems.ITEMS_UPGRADE.getSpecificValuesArray(new ItemUpgrade[0], VALID_UPGRADES)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ElectrodynamicsItems.ITEMS_UPGRADE.getSpecificValuesArray(new ItemUpgrade[0], VALID_UPGRADES)));
	}
}
