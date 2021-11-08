package electrodynamics.prefab.inventory.container;

import javax.annotation.Nullable;

import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.utilities.UtilitiesContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class GenericContainer<T extends BlockEntity> extends AbstractContainerMenu {

    protected final Container inventory;
    protected final ContainerData inventorydata;
    protected final Level world;
    protected final int slotCount;
    protected final BlockEntity tile;
    protected int playerInvOffset = 0;
    private int nextIndex = 0;

    public int nextIndex() {
	return nextIndex++;
    }

    protected GenericContainer(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(type, id);
	checkContainerSize(inventory, inventory.getContainerSize());
	checkContainerDataCount(inventorydata, inventorydata.getCount());
	this.inventory = inventory;
	this.inventorydata = inventorydata;
	world = playerinv.player.level;
	addInventorySlots(inventory, playerinv);
	slotCount = slots.size();
	for (int i = 0; i < 3; ++i) {
	    for (int j = 0; j < 9; ++j) {
		addSlot(new GenericSlot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + playerInvOffset));
	    }
	}

	for (int k = 0; k < 9; ++k) {
	    addSlot(new GenericSlot(playerinv, k, 8 + k * 18, 142 + playerInvOffset));
	}

	tile = inventory instanceof BlockEntity bl ? bl : null;

	addDataSlots(inventorydata);
    }

    public abstract void addInventorySlots(Container inv, Inventory playerinv);

    public void clear() {
	inventory.clearContent();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSize() {
	return inventory.getContainerSize();
    }

    public Container getIInventory() {
	return inventory;
    }

    @Override
    public boolean stillValid(Player player) {
	return inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
	return UtilitiesContainers.handleShiftClick(slots, player, index);
    }

    @Override
    public void removed(Player player) {
	super.removed(player);
	inventory.stopOpen(player);
    }

    @Nullable
    public T getHostFromIntArray() {
	BlockPos block = new BlockPos(inventorydata.get(0), inventorydata.get(1), inventorydata.get(2));
	try {
	    return (T) world.getBlockEntity(block);
	} catch (Exception e) {
	    return null;
	}
    }
}