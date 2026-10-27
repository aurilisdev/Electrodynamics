package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerWireMill extends ContainerProcessorO2O {

    public ContainerWireMill(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WIREMILL.get(), id, playerinv);
    }

    public ContainerWireMill(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_WIREMILL.get(), id, playerinv, inventory, inventorydata);
    }
}
