package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerEnergizedAlloyer extends ContainerProcessorDO2O {

    public ContainerEnergizedAlloyer(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_ENERGIZEDALLOYER.get(), id, playerinv);
    }

    public ContainerEnergizedAlloyer(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_ENERGIZEDALLOYER.get(), id, playerinv, inventory, inventorydata);
    }
}
