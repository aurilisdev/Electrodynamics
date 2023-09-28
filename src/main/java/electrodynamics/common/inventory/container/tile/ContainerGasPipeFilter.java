package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.gas.TileGasPipeFilter;
import electrodynamics.prefab.inventory.container.ContainerBlockEntityEmpty;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerGasPipeFilter extends ContainerBlockEntityEmpty<TileGasPipeFilter> {

	public ContainerGasPipeFilter(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_GASPIPEFILTER.get(), id, playerinv, inventorydata);
	}

	public ContainerGasPipeFilter(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(3));
	}

	@Override
	public int getPlayerInvOffset() {
		return 20;
	}

}
