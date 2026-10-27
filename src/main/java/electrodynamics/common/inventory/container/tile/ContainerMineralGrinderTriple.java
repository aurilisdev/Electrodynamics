package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerMineralGrinderTriple extends ContainerProcessorO2OTriple {

    public ContainerMineralGrinderTriple(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALGRINDERTRIPLE.get(), id, playerinv);
    }

    public ContainerMineralGrinderTriple(int id, Inventory playerinv, Container inventory,
	    ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALGRINDERTRIPLE.get(), id, playerinv, inventory, inventorydata);
    }
}
