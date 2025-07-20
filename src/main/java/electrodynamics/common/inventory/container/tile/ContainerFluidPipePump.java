package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.fluid.TileFluidPipePump;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerFluidPipePump extends GenericContainerBlockEntity<TileFluidPipePump> {

	public ContainerFluidPipePump(int id, Inventory playerinv, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEPUMP.get(), id, playerinv, EMPTY, inventorydata);
	}

	public ContainerFluidPipePump(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainerData(5));
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {

	}
}
