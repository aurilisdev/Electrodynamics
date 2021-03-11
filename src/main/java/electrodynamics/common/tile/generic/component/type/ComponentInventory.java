package electrodynamics.common.tile.generic.component.type;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;

import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentHolder;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ComponentInventory implements Component, ISidedInventory {
    protected ComponentHolder holder = null;

    @Override
    public void setHolder(ComponentHolder holder) {
	this.holder = holder;
    }

    protected static final int[] SLOTS_EMPTY = new int[] {};
    protected NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
    protected LazyOptional<?> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    protected HashSet<PlayerEntity> viewing = new HashSet<>();
    protected EnumMap<Direction, ArrayList<Integer>> directionMappings = new EnumMap<>(Direction.class);
    protected int inventorySize;

    public ComponentInventory setInventorySize(int inventorySize) {
	this.inventorySize = inventorySize;
	items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
	return this;
    }

    public ComponentInventory addSlotOnFace(Direction face, Integer slot) {
	if (!directionMappings.containsKey(face)) {
	    directionMappings.put(face, new ArrayList<>());
	}
	directionMappings.get(face).add(slot);
	return this;
    }

    @Override
    public void loadFromNBT(BlockState state, CompoundNBT nbt) {
	ItemStackHelper.loadAllItems(nbt, items);
    }

    @Override
    public void saveToNBT(CompoundNBT nbt) {
	ItemStackHelper.saveAllItems(nbt, items);
    }

    @Override
    public void openInventory(PlayerEntity player) {
	viewing.add(player);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
	viewing.remove(player);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction side) {
	return capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
	return itemHandler.cast();
    }

    @Override
    public int getSizeInventory() {
	return inventorySize;
    }

    @Override
    public boolean isEmpty() {
	for (ItemStack itemstack : items) {
	    if (!itemstack.isEmpty()) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
	return items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
	return ItemStackHelper.getAndSplit(items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
	return ItemStackHelper.getAndRemove(items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
	if (isItemValidForSlot(index, stack)) {
	    items.set(index, stack);
	    if (stack.getCount() > getInventoryStackLimit()) {
		stack.setCount(getInventoryStackLimit());
	    }
	}
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
	BlockPos pos = holder.getPos();
	return player.world.getTileEntity(pos) == holder
		&& player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void clear() {
	items.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return directionMappings.get(side).stream().mapToInt(i -> i).toArray();
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
	return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
	ArrayList<Integer> test = new ArrayList<>();
	for (int i : getSlotsForFace(direction)) {
	    test.add(i);
	}
	return test.contains(index);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Inventory;
    }

    @Override
    public void remove() {
	itemHandler.invalidate();
    }

    @Override
    public void markDirty() {
	((TileEntity) holder).markDirty();
    }

}
