package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerOxidationFurnace extends ContainerProcessorDO2O {

    public ContainerOxidationFurnace(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_OXIDATIONFURNACE.get(), id, playerinv);
    }

    public ContainerOxidationFurnace(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_OXIDATIONFURNACE.get(), id, playerinv, inventory, inventorydata);
    }
}
