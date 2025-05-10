package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.fluid.TileFluidPipePump;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerFluidPipePump extends GenericContainerBlockEntity<TileFluidPipePump> {

	public ContainerFluidPipePump(int id, PlayerInventory playerinv, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEPUMP.get(), id, playerinv, EMPTY, inventorydata);
	}

	public ContainerFluidPipePump(int id, PlayerInventory playerinv) {
		this(id, playerinv, new IntArray(3));
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {

	}
}
