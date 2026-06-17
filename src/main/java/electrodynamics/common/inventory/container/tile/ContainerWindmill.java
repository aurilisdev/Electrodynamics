package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.electricitygrid.generators.TileWindmill;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerWindmill extends GenericContainerBlockEntity<TileWindmill> {

    public static final SubtypeItemUpgrade[] VALID_UPGRADES = { SubtypeItemUpgrade.stator };

    public ContainerWindmill(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
    }

    public ContainerWindmill(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WINDMILL.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new SlotUpgrade(inv, nextIndex(), 25, 42, VALID_UPGRADES));
    }
}
