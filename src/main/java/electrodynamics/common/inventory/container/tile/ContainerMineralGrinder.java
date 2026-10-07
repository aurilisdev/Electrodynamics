package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerMineralGrinder extends ContainerProcessorO2O {

    public ContainerMineralGrinder(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALGRINDER.get(), id, playerinv);
    }

    public ContainerMineralGrinder(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALGRINDER.get(), id, playerinv, inventory, inventorydata);
    }
}
