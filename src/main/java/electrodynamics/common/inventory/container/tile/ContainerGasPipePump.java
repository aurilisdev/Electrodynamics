package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.gas.TileGasPipePump;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerGasPipePump extends GenericContainerBlockEntity<TileGasPipePump> {

    public ContainerGasPipePump(int id, Inventory playerinv, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_GASPIPEPUMP.get(), id, playerinv, EMPTY, inventorydata);
    }

    public ContainerGasPipePump(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainerData(3));
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {

    }
}
