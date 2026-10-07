package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerReinforcedAlloyer extends ContainerProcessorDO2O {

    public ContainerReinforcedAlloyer(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_REINFORCEDALLOYER.get(), id, playerinv);
    }

    public ContainerReinforcedAlloyer(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_REINFORCEDALLOYER.get(), id, playerinv, inventory, inventorydata);
    }
}
