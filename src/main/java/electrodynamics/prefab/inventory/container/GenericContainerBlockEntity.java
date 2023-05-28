package electrodynamics.prefab.inventory.container;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class GenericContainerBlockEntity<T extends BlockEntity> extends GenericContainer {

	protected final BlockEntity tile;
	protected final ContainerData inventorydata;

	public GenericContainerBlockEntity(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory);
		checkContainerDataCount(inventorydata, inventorydata.getCount());
		this.inventorydata = inventorydata;
		addDataSlots(inventorydata);
		tile = inventory instanceof BlockEntity bl ? bl : null;
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
	public BlockEntity getUnsafeHost() {
		return world.getBlockEntity(new BlockPos(inventorydata.get(0), inventorydata.get(1), inventorydata.get(2)));
	}
	
	@Override
	public void broadcastChanges() {
		// TODO Auto-generated method stub
		super.broadcastChanges();
	}
}