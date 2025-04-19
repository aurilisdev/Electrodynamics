package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.machines.TileFermentationPlant;
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
import voltaic.prefab.inventory.container.slot.item.type.SlotFluid;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.utilities.math.Color;

public class ContainerFermentationPlant extends GenericContainerBlockEntity<TileFermentationPlant> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience };

	public ContainerFermentationPlant(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerFermentationPlant(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_FERMENTATIONPLANT.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 73, 31).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotFluid(inv, nextIndex(), 38, 51));
		addSlot(new SlotFluid(inv, nextIndex(), 108, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 14, ElectrodynamicsItems.ITEMS_UPGRADE.getSpecificValuesArray(new ItemUpgrade[0], VALID_UPGRADES)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 34, ElectrodynamicsItems.ITEMS_UPGRADE.getSpecificValuesArray(new ItemUpgrade[0], VALID_UPGRADES)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 54, ElectrodynamicsItems.ITEMS_UPGRADE.getSpecificValuesArray(new ItemUpgrade[0], VALID_UPGRADES)));
	}
}
