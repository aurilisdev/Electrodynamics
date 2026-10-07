package electrodynamics.common.inventory.container.tile;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.utilities.math.Color;

public abstract class ContainerProcessorO2ODouble extends GenericContainerBlockEntity<GenericTile> {

    protected ContainerProcessorO2ODouble(MenuType<?> type, int id, Inventory playerinv) {
	this(type, id, playerinv, new SimpleContainer(9), new SimpleContainerData(3));
    }

    protected ContainerProcessorO2ODouble(MenuType<?> type, int id, Inventory playerinv, Container inventory,
	    ContainerData inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerProcessorO2O.START_X_OFFSET, 24)
		.setIOColor(new Color(0, 240, 255, 255)));
	addSlot(new SlotGeneric(inv, nextIndex(), 56 - ContainerProcessorO2O.START_X_OFFSET, 44)
		.setIOColor(new Color(0, 240, 255, 255)));
	addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerProcessorO2O.START_X_OFFSET, 24)
		.setIOColor(new Color(255, 0, 0, 255)));
	addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerProcessorO2O.START_X_OFFSET, 44)
		.setIOColor(new Color(255, 0, 0, 255)));
	addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerProcessorO2O.START_X_OFFSET + 20, 24)
		.setIOColor(new Color(255, 255, 0, 255)));
	addSlot(new SlotRestricted(inv, nextIndex(), 116 - ContainerProcessorO2O.START_X_OFFSET + 20, 44)
		.setIOColor(new Color(255, 255, 0, 255)));
	addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, ContainerProcessorO2O.VALID_UPGRADES));
	addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, ContainerProcessorO2O.VALID_UPGRADES));
	addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, ContainerProcessorO2O.VALID_UPGRADES));

    }
}
