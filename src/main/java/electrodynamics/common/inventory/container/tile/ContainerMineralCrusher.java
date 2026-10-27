package electrodynamics.common.inventory.container.tile;

import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class ContainerMineralCrusher extends ContainerProcessorO2O {

    public ContainerMineralCrusher(int id, Inventory playerinv) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALCRUSHER.get(), id, playerinv);
    }

    public ContainerMineralCrusher(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_MINERALCRUSHER.get(), id, playerinv, inventory, inventorydata);
    }
}
