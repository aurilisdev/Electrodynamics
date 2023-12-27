package electrodynamics.prefab.inventory.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;

public abstract class GenericContainerBlockEntity<T extends TileEntity> extends GenericContainer {

	protected final TileEntity tile;
	protected final IIntArray inventorydata;

	public GenericContainerBlockEntity(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(type, id, playerinv, inventory);
		checkContainerDataCount(inventorydata, inventorydata.getCount());
		this.inventorydata = inventorydata;
		addDataSlots(inventorydata);
		if (inventory instanceof TileEntity) {
		    tile = (TileEntity) inventory;
		} else {
		    tile = null;
		}
		
	}

	@Nullable
	public T getHostFromIntArray() {
		try {
			return (T) world.getBlockEntity(new BlockPos(inventorydata.get(0), inventorydata.get(1), inventorydata.get(2)));
		} catch (Exception e) {
			return null;
		}
	}

	@Nullable
	public TileEntity getUnsafeHost() {
		return world.getBlockEntity(new BlockPos(inventorydata.get(0), inventorydata.get(1), inventorydata.get(2)));
	}

	@Override
	public void broadcastChanges() {
		// TODO Auto-generated method stub
		super.broadcastChanges();
	}
}