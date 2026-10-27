package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerMineralCrusherTriple extends ContainerProcessorO2OTriple {

    public ContainerMineralCrusherTriple(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALCRUSHERTRIPLE.get(), id, playerinv);
    }

    public ContainerMineralCrusherTriple(int id, Inventory playerinv, Container inventory,
	    ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALCRUSHERTRIPLE.get(), id, playerinv, inventory, inventorydata);
    }
}
