package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerLathe extends ContainerProcessorO2O {

    public ContainerLathe(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_LATHE.get(), id, playerinv);
    }

    public ContainerLathe(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_LATHE.get(), id, playerinv, inventory, inventorydata);
    }
}
