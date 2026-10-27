package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerWireMillDouble extends ContainerProcessorO2ODouble {

    public ContainerWireMillDouble(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WIREMILLDOUBLE.get(), id, playerinv);
    }

    public ContainerWireMillDouble(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WIREMILLDOUBLE.get(), id, playerinv, inventory, inventorydata);
    }
}
