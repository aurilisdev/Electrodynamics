package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.network.gas.TileGasPipePump;
import electrodynamics.prefab.inventory.container.ContainerBlockEntityEmpty;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerGasPipePump extends ContainerBlockEntityEmpty<TileGasPipePump> {

	public ContainerGasPipePump(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_GASPIPEPUMP.get(), id, playerinv, inventorydata);
	}

	public ContainerGasPipePump(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(3));
	}

}
