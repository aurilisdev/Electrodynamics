package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerWireMillTriple extends ContainerProcessorO2OTriple {

    public ContainerWireMillTriple(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WIREMILLTRIPLE.get(), id, playerinv);
    }

    public ContainerWireMillTriple(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WIREMILLTRIPLE.get(), id, playerinv, inventory, inventorydata);
    }
}
