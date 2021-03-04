package electrodynamics.common.inventory.container;

import electrodynamics.common.inventory.container.slot.GenericSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class GenericContainerInventory extends Container {
    protected final IInventory inventory;
    protected final IIntArray inventorydata;
    protected final World world;
    protected final int slotCount;
    protected final TileEntity tile;
    private int nextIndex = 0;

    public int nextIndex() {
	return nextIndex++;
    }

    protected GenericContainerInventory(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory,
	    IIntArray inventorydata) {
	super(type, id);
	assertInventorySize(inventory, inventory.getSizeInventory());
	assertIntArraySize(inventorydata, inventorydata.size());
	this.inventory = inventory;
	this.inventorydata = inventorydata;
	world = playerinv.player.world;
	addInventorySlots(inventory, playerinv);
	slotCount = inventorySlots.size();
	for (int i = 0; i < 3; ++i) {
	    for (int j = 0; j < 9; ++j) {
		addSlot(new GenericSlot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	    }
	}

	for (int k = 0; k < 9; ++k) {
	    addSlot(new GenericSlot(playerinv, k, 8 + k * 18, 142));
	}

	if (inventory instanceof TileEntity) {
	    tile = (TileEntity) inventory;
	} else {
	    tile = null;
	}

	trackIntArray(inventorydata);
    }

    public abstract void addInventorySlots(IInventory inv, PlayerInventory playerinv);

    public void clear() {
	inventory.clear();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSize() {
	return inventory.getSizeInventory();
    }

    public IInventory getIInventory() {
	return inventory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
	return inventory.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
	return Containers.handleShiftClick(inventorySlots, player, index);
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
	super.onContainerClosed(player);
	inventory.closeInventory(player);
    }

    public <T extends TileEntity> T getHostFromIntArray(World world) {
	BlockPos block = new BlockPos(inventorydata.get(0), inventorydata.get(1), inventorydata.get(2));
	try {
	    return (T) world.getTileEntity(block);
	} catch (Exception e) {
	    return null;
	}
    }
}