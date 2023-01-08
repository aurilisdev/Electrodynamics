package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import electrodynamics.api.capability.types.itemhandler.IndexedSidedInvWrapper;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.items.IItemHandlerModifiable;


public class ComponentInventory implements Component, WorldlyContainer {
	protected GenericTile holder = null;

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	protected static final int[] SLOTS_EMPTY = new int[] {};
	protected Property<NonNullList<ItemStack>> items;
	protected TriPredicate<Integer, ItemStack, ComponentInventory> itemValidTest = (x, y, i) -> true;
	protected HashSet<Player> viewing = new HashSet<>();
	protected EnumMap<Direction, ArrayList<Integer>> directionMappings = new EnumMap<>(Direction.class);
	protected EnumMap<Direction, ArrayList<Integer>> relativeDirectionMappings = new EnumMap<>(Direction.class);
	protected int inventorySize;
	protected Function<Direction, Collection<Integer>> getSlotsFunction;
	protected LazyOptional<IItemHandlerModifiable>[] sideWrappers = IndexedSidedInvWrapper.create(this, Direction.values());

	/*
	 * IMPORTANT DEFINITIONS:
	 * 
	 * SLOT ORDER: 1. Item Input Slots 2. Item Output Slot 3. Item Biproduct Slots 4. Bucket Input Slots 5. Bucket Output Slots 6. Upgrade Slots
	 * 
	 */

	private int inputs = 0;
	private int outputs = 0;
	private int upgrades = 0;
	private int biproducts = 0;
	private int bucketInputs = 0;
	private int bucketOutputs = 0;

	private int processors = 0;
	private int processorInputs = 0;
	private BiConsumer<ComponentInventory, Integer> onChanged = (componentInventory, slot) -> {
		if(holder != null) {
			holder.onInventoryChange(componentInventory, slot);
		}
	};

	protected SubtypeItemUpgrade[] validUpgrades = SubtypeItemUpgrade.values();

	public ComponentInventory(GenericTile holder) {
		holder(holder);
	}

	public ComponentInventory onChanged(BiConsumer<ComponentInventory, Integer> onChanged) {
		this.onChanged = onChanged;
		return this;
	}

	public ComponentInventory getSlots(Function<Direction, Collection<Integer>> getSlotsFunction) {
		this.getSlotsFunction = getSlotsFunction;
		return this;
	}

	public ComponentInventory size(int inventorySize) {
		this.inventorySize = inventorySize;
		items = holder.property(new Property<NonNullList<ItemStack>>(PropertyType.InventoryItems, "itemproperty", NonNullList.<ItemStack>withSize(getContainerSize(), ItemStack.EMPTY)));
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

	public ComponentInventory universalSlots(Integer... slots) {
		for (Direction faceDirection : Direction.values()) {
			faceSlots(faceDirection, slots);
		}
		return this;
	}

	public ComponentInventory setMachineSlots(int extra) {
		if (biproducts > 0) {
			// extra outputs
			for (int i = getItemBiproductStartIndex(); i < getItemBiproductStartIndex() + biproducts; i++) {
				relativeFaceSlots(Direction.WEST, i);
				relativeFaceSlots(Direction.DOWN, i);
			}
		}
		// inputs
		return relativeFaceSlots(Direction.EAST, 0, extra == 1 ? 2 : 0, extra == 2 ? 4 : 0).relativeFaceSlots(Direction.UP, extra == 1 ? 2 : 0, extra == 2 ? 4 : 0)
				// outputs
				.relativeFaceSlots(Direction.WEST, 1, extra == 1 || extra == 2 ? 3 : 1, extra == 2 ? 5 : 1).relativeFaceSlots(Direction.DOWN, 1, extra == 1 || extra == 2 ? 3 : 1, extra == 2 ? 5 : 1);
	}

	public ComponentInventory valid(TriPredicate<Integer, ItemStack, ComponentInventory> itemValidPredicate) {
		itemValidTest = itemValidPredicate;
		return this;
	}

	@Override
	public void startOpen(Player player) {
		viewing.add(player);
	}

	@Override
	public void stopOpen(Player player) {
		viewing.remove(player);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction side) {
		return (side == null || directionMappings.containsKey(side)
				|| holder.hasComponent(ComponentType.Direction)
						&& relativeDirectionMappings.containsKey(BlockEntityUtils.getRelativeSide(
								holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side)))
				&& capability == ForgeCapabilities.ITEM_HANDLER;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		return hasCapability(capability, side) ? (LazyOptional<T>) sideWrappers[side.ordinal()] : LazyOptional.empty();
	}

	@Override
	public int getContainerSize() {
		return inventorySize;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : items.get()) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(int index) {
		return items.get().get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		ItemStack stack = ContainerHelper.removeItem(items.get(), index, count);
		if(!stack.isEmpty()) {
			setChanged(index);
		}
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(items.get(), index);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}
		items.get().set(index, stack);
		setChanged(index);
	}

	@Override
	public boolean stillValid(Player player) {
		BlockPos pos = holder.getBlockPos();
		return holder.getLevel().getBlockEntity(pos) == holder && player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
	}

	@Override
	public void clearContent() {
		items.get().clear();
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (getSlotsFunction != null) {
			return getSlotsFunction.apply(side).stream().mapToInt(i -> i).toArray();
		}
		if (holder.hasComponent(ComponentType.Direction)) {
			Stream<Integer> st = directionMappings.containsKey(side) ? directionMappings.get(side).stream() : null;
			Direction relativeDirection = BlockEntityUtils.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side);
			Stream<Integer> stRel = relativeDirectionMappings.containsKey(relativeDirection) ? relativeDirectionMappings.get(relativeDirection).stream() : null;
			return ArrayUtils.addAll(st == null ? new int[0] : st.mapToInt(i -> i).toArray(), stRel == null ? new int[0] : stRel.mapToInt(i -> i).toArray());

		}
		return directionMappings.get(side) == null ? SLOTS_EMPTY : directionMappings.get(side).stream().mapToInt(i -> i).toArray();
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		// Electrodynamics.LOGGER.info(itemValidTest.test(index, stack, this));
		return itemValidTest.test(index, stack, this);
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, Direction direction) {
		ArrayList<Integer> test = new ArrayList<>();
		for (int i : getSlotsForFace(direction)) {
			test.add(i);
		}
		return test.contains(index) && canPlaceItem(index, itemStackIn);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		ArrayList<Integer> test = new ArrayList<>();
		for (int i : getSlotsForFace(direction)) {
			test.add(i);
		}
		return test.contains(index);
	}

	@Deprecated(forRemoval = false, since = "Only call this if absolutely nessisary. You want to call getItem() so NBT can be saved")
	public NonNullList<ItemStack> getItems() {
		return items.get();
	}

	public HashSet<Player> getViewing() {
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
	//this is only called through someone instance checking of this class....
	public void setChanged() {
		setChanged(-1);
	}
	
	public void setChanged(int slot) {
		items.forceDirty();
		if (onChanged != null) {
			onChanged.accept(this, slot);
		}
	}

	public ComponentInventory inputs(int par) {
		inputs = par;
		return this;
	}

	public int inputs() {
		return inputs;
	}

	public ComponentInventory outputs(int par) {
		outputs = par;
		return this;
	}

	public int outputs() {
		return outputs;
	}

	public ComponentInventory upgrades(int par) {
		upgrades = par;
		return this;
	}

	public int upgrades() {
		return upgrades;
	}

	public ComponentInventory validUpgrades(SubtypeItemUpgrade... upgrades) {
		validUpgrades = upgrades;
		return this;
	}

	public SubtypeItemUpgrade[] validUpgrades() {
		return validUpgrades;
	}

	public ComponentInventory biproducts(int par) {
		biproducts = par;
		return this;
	}

	public int biproducts() {
		return biproducts;
	}

	public ComponentInventory bucketInputs(int par) {
		bucketInputs = par;
		return this;
	}

	public int bucketInputs() {
		return bucketInputs;
	}

	public ComponentInventory bucketOutputs(int par) {
		bucketOutputs = par;
		return this;
	}

	public int bucketOutputs() {
		return bucketOutputs;
	}

	public ComponentInventory processors(int par) {
		processors = par;
		return this;
	}

	public int processors() {
		return processors;
	}

	public ComponentInventory processorInputs(int par) {
		processorInputs = par;
		return this;
	}

	public int processorInputs() {
		return processorInputs;
	}

	/*
	 * Utility methods so you don't have to think as much
	 */

	public int getInputStartIndex() {
		return 0;
	}

	public int getOutputStartIndex() {
		return inputs;
	}

	public int getItemBiproductStartIndex() {
		return getOutputStartIndex() + outputs;
	}

	public int getInputBucketStartIndex() {
		return getItemBiproductStartIndex() + biproducts;
	}

	public int getOutputBucketStartIndex() {
		return getInputBucketStartIndex() + bucketInputs;
	}

	public int getUpgradeSlotStartIndex() {
		return getOutputBucketStartIndex() + bucketOutputs;
	}

	public List<List<ItemStack>> getInputContents() {
		List<List<ItemStack>> combinedList = new ArrayList<>();
		if (processors == 0) {
			List<ItemStack> newList = new ArrayList<>();
			for (int i = 0; i < inputs; i++) {
				newList.add(getItem(i));
			}
			combinedList.add(newList);

			return combinedList;
		}
		for (int i = 0; i < processors; i++) {
			List<ItemStack> newList = new ArrayList<>();
			for (int j = 0; j < processorInputs; j++) {
				newList.add(getItem(j + i * (processorInputs + 1)));
			}
			combinedList.add(newList);
		}
		return combinedList;
	}

	public List<ItemStack> getOutputContents() {
		if (processors == 0) {
			List<ItemStack> list = new ArrayList<>();
			for (int i = 0; i < outputs; i++) {
				list.add(getItem(getOutputStartIndex() + i));
			}
			return list;
		}
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < processors; i++) {
			list.add(getItem((processorInputs + 1) * (i + 1) - 1));
		}
		return list;
	}

	public List<ItemStack> getItemBiContents() {
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < biproducts; i++) {
			list.add(getItem(getItemBiproductStartIndex() + i));
		}
		return list;
	}

	public List<ItemStack> getInputBucketContents() {
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < bucketInputs; i++) {
			list.add(getItem(getInputBucketStartIndex() + i));
		}
		return list;
	}

	public List<ItemStack> getOutputBucketContents() {
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < bucketOutputs; i++) {
			list.add(getItem(getOutputBucketStartIndex() + i));
		}
		return list;
	}

	public List<ItemStack> getUpgradeContents() {
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < upgrades; i++) {
			list.add(getItem(getUpgradeSlotStartIndex() + i));
		}
		return list;
	}

	public List<List<Integer>> getInputSlots() {
		List<List<Integer>> combined = new ArrayList<>();
		if (processors == 0) {
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < inputs; i++) {
				list.add(getInputStartIndex() + i);
			}
			combined.add(list);
			return combined;
		}
		for (int i = 0; i < processors; i++) {
			List<Integer> list = new ArrayList<>();
			for (int j = 0; j < processorInputs; j++) {
				list.add(j + i * (processorInputs + 1));
			}
			combined.add(list);
		}
		return combined;
	}

	// you're making me break out a sheet of paper for this!
	public List<Integer> getOutputSlots() {
		if (processors == 0) {
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < outputs; i++) {
				list.add(getOutputStartIndex() + i);
			}
			return list;
		}
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < processors; i++) {
			list.add((processorInputs + 1) * (i + 1) - 1);
		}
		return list;
	}

	public boolean areOutputsEmpty() {
		boolean output = false;
		boolean biproduct = false;
		for (ItemStack stack : getOutputContents()) {
			if (stack.isEmpty()) {
				output = true;
				break;
			}
		}
		if (!getItemBiContents().isEmpty()) {
			for (ItemStack stack : getItemBiContents()) {
				if (stack.isEmpty()) {
					biproduct = true;
					break;
				}
			}
		} else {
			biproduct = true;
		}
		return output && biproduct;
	}

	public boolean hasItemsInOutput() {
		for (ItemStack stack : getOutputContents()) {
			if (!stack.isEmpty()) {
				return true;
			}
		}
		for (ItemStack stack : getItemBiContents()) {
			if (!stack.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// specialized case of hasInputRoom()
	public boolean areInputsEmpty() {
		for (List<ItemStack> stacks : getInputContents()) {
			for (ItemStack stack : stacks) {
				if (stack.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasInputRoom() {
		for (List<ItemStack> stacks : getInputContents()) {
			for (ItemStack stack : stacks) {
				if (stack.getMaxStackSize() > stack.getCount()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isUpgradeValid(SubtypeItemUpgrade upgrade) {
		for (SubtypeItemUpgrade subtype : validUpgrades) {
			if (subtype == upgrade) {
				return true;
			}
		}
		return false;
	}

}
