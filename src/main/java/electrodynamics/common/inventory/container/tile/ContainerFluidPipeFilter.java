package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.fluid.TileFluidPipeFilter;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerFluidPipeFilter extends GenericContainerBlockEntity<TileFluidPipeFilter> {

	public ContainerFluidPipeFilter(int id, PlayerInventory playerinv, IIntArray inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEFILTER.get(), id, playerinv, EMPTY, inventorydata);
	}

	public ContainerFluidPipeFilter(int id, PlayerInventory playerinv) {
		this(id, playerinv, new IntArray(5));
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		setPlayerInvOffset(20);
	}

}
