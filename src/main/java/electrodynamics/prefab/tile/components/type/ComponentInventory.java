package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.UtilitiesTiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class ComponentInventory implements Component, ISidedInventory {
    protected GenericTile holder = null;

    @Override
    public void holder(GenericTile holder) {
	this.holder = holder;
    }

    protected static final int[] SLOTS_EMPTY = new int[] {};
    protected NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
    protected BiPredicate<Integer, ItemStack> itemValidPredicate = (x, y) -> true;
    protected HashSet<PlayerEntity> viewing = new HashSet<>();
    protected EnumMap<Direction, ArrayList<Integer>> directionMappings = new EnumMap<>(Direction.class);
    protected EnumMap<Direction, ArrayList<Integer>> relativeDirectionMappings = new EnumMap<>(Direction.class);
    protected Direction lastDirection = null;
    protected int inventorySize;
    protected Function<Direction, Collection<Integer>> getSlotsFunction;
    protected boolean shouldSendInfo;

    public ComponentInventory(GenericTile holder) {
	holder(holder);
    }

    public ComponentInventory shouldSendInfo() {
	if (!shouldSendInfo && holder.hasComponent(ComponentType.PacketHandler)) {
	    holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).guiPacketReader(this::loadNBT).guiPacketWriter(this::saveNBT);
	}
	shouldSendInfo = true;
	return this;
    }

    public ComponentInventory getSlots(Function<Direction, Collection<Integer>> getSlotsFunction) {
	this.getSlotsFunction = getSlotsFunction;
	return this;
    }

    public ComponentInventory size(int inventorySize) {
	this.inventorySize = inventorySize;
	items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
	return this;
    }

    public ComponentInventory faceSlots(Direction face, Integer... slot) {
	if (!directionMappings.containsKey(face)) {
	    directionMappings.put(face, new ArrayList<>());
	}
	for (Integer sl : slot) {
	    directionMappings.get(face).add(sl);
	}
	return this;
    }

    public ComponentInventory relativeFaceSlots(Direction face, Integer... slot) {
	if (!relativeDirectionMappings.containsKey(face)) {
	    relativeDirectionMappings.put(face, new ArrayList<>());
	}
	for (Integer sl : slot) {
	    relativeDirectionMappings.get(face).add(sl);
	}
	return this;
    }

    public ComponentInventory slotFaces(Integer slot, Direction... faces) {
	for (Direction face : faces) {
	    faceSlots(face, slot);
	}
	return this;
    }

    public ComponentInventory relativeSlotFaces(Integer slot, Direction... faces) {
	for (Direction face : faces) {
	    relativeFaceSlots(face, slot);
	}
	return this;
    }

    public ComponentInventory valid(BiPredicate<Integer, ItemStack> itemValidPredicate) {
	this.itemValidPredicate = itemValidPredicate;
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

    protected void loadNBT(CompoundNBT nbt) {
	items.clear();
	loadFromNBT(holder.getBlockState(), nbt);
    }

    protected void saveNBT(CompoundNBT nbt) {
	saveToNBT(nbt);
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
	lastDirection = side;
	return (side == null || directionMappings.containsKey(side)
		|| holder.hasComponent(ComponentType.Direction) && relativeDirectionMappings.containsKey(
			UtilitiesTiles.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side)))
		&& capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
	return hasCapability(capability, side) ? (LazyOptional<T>) LazyOptional.of(() -> new SidedInvWrapper(this, side)) : LazyOptional.empty();
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
	if (stack.getCount() > getInventoryStackLimit()) {
	    stack.setCount(getInventoryStackLimit());
	}
	if (shouldSendInfo && stack.getCount() != items.get(index).getCount() || stack.getItem() != items.get(index).getItem()) {
	    items.set(index, stack);
	    if (holder.hasComponent(ComponentType.PacketHandler)) {
		holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	} else {
	    items.set(index, stack);
	}
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
	BlockPos pos = holder.getPos();
	return holder.getWorld().getTileEntity(pos) == holder && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void clear() {
	items.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	if (getSlotsFunction != null) {
	    return getSlotsFunction.apply(side).stream().mapToInt(i -> i).toArray();
	}
	if (holder.hasComponent(ComponentType.Direction)) {
	    Stream<Integer> st = directionMappings.containsKey(side) ? directionMappings.get(side).stream() : null;
	    Stream<Integer> stRel = relativeDirectionMappings.containsKey(
		    UtilitiesTiles.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side))
			    ? relativeDirectionMappings
				    .get(UtilitiesTiles.getRelativeSide(
					    holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side))
				    .stream()
			    : null;
	    return ArrayUtils.addAll(st == null ? new int[0] : st.mapToInt(i -> i).toArray(),
		    stRel == null ? new int[0] : stRel.mapToInt(i -> i).toArray());

	}
	return directionMappings.get(side) == null ? SLOTS_EMPTY : directionMappings.get(side).stream().mapToInt(i -> i).toArray();

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
	return itemValidPredicate.test(index, stack);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
	lastDirection = direction;
	ArrayList<Integer> test = new ArrayList<>();
	for (int i : getSlotsForFace(direction)) {
	    test.add(i);
	}
	return test.contains(index) && isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
	lastDirection = direction;
	ArrayList<Integer> test = new ArrayList<>();
	for (int i : getSlotsForFace(direction)) {
	    test.add(i);
	}
	return test.contains(index);
    }

    public NonNullList<ItemStack> getItems() {
	return items;
    }

    public HashSet<PlayerEntity> getViewing() {
	return viewing;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Inventory;
    }

    @Override
    public void remove() {
	// Not required
    }

    @Override
    public void markDirty() {
	holder.markDirty();
    }
}
