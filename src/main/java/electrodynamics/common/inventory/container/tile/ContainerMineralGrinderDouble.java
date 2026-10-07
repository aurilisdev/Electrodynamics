package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerMineralGrinderDouble extends ContainerProcessorO2ODouble {

    public ContainerMineralGrinderDouble(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALGRINDERDOUBLE.get(), id, playerinv);
    }

    public ContainerMineralGrinderDouble(int id, Inventory playerinv, Container inventory,
	    ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALGRINDERDOUBLE.get(), id, playerinv, inventory, inventorydata);
    }
}
