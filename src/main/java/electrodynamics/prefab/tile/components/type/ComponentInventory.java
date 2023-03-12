package electrodynamics.prefab.tile.components.type;

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
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

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

	public static final String SAVE_KEY = "itemproperty";

	/*
	 * IMPORTANT DEFINITIONS:
	 * 
	 * SLOT ORDER: 1. Item Input Slots 2. Item Output Slot 3. Item Biproduct Slots
	 * 4. Bucket Input Slots 5. Bucket Output Slots 6. Upgrade Slots
	 * 
	 */

	private int inputs = 0;
	private int outputs = 0;
	private int upgrades = 0;
	private int biproducts = 0;
	private int bucketInputs = 0;
	private int bucketOutputs = 0;

	private int inputsPerProc = 0;
	private int outputsPerProc = 0;
	private int biprodsPerProc = 0;

	private BiConsumer<ComponentInventory, Integer> onChanged = (componentInventory, slot) -> {
		if (holder != null) {
			holder.onInventoryChange(componentInventory, slot);
		}
	};

	protected SubtypeItemUpgrade[] validUpgrades = SubtypeItemUpgrade.values();

	public ComponentInventory(GenericTile holder) {
		this(holder, InventoryBuilder.EMPTY);
	}
	
	public ComponentInventory(GenericTile holder, InventoryBuilder builder) {
		holder(holder);

		if (builder.builderSize > 0) {
			inventorySize = builder.builderSize;
		} else {

			inputs = builder.builderInputs;
			outputs = builder.builderOutputs;
			upgrades = builder.builderUpgrades;
			biproducts = builder.builderBiproducts;
			bucketInputs = builder.builderBucketInputs;
			bucketOutputs = builder.builderBucketOutputs;

			inventorySize = inputs + outputs + upgrades + biproducts + bucketInputs + bucketOutputs + upgrades;

			inputsPerProc = builder.builderInputsPerProc;
			outputsPerProc = builder.builderOutputsPerProc;
			biprodsPerProc = builder.builderBiprodsPerProc;

		}

		items = holder.property(new Property<>(PropertyType.InventoryItems, "itemproperty", NonNullList.withSize(getContainerSize(), ItemStack.EMPTY)));

	}

	public ComponentInventory onChanged(BiConsumer<ComponentInventory, Integer> onChanged) {
		this.onChanged = onChanged;
		return this;
	}

	public ComponentInventory getSlots(Function<Direction, Collection<Integer>> getSlotsFunction) {
		this.getSlotsFunction = getSlotsFunction;
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

	public ComponentInventory implementMachineInputsAndOutputs() {
		ComponentInventory inv = this;

		for (int i : getInputSlots()) {
			inv = inv.relativeFaceSlots(Direction.EAST, i).relativeFaceSlots(Direction.UP, i);
		}

		for (int i : getOutputSlots()) {
			inv = inv.relativeFaceSlots(Direction.WEST, i).relativeFaceSlots(Direction.DOWN, i);
		}

		for (int i : getBiproductSlots()) {
			inv = inv.relativeFaceSlots(Direction.WEST, i).relativeFaceSlots(Direction.DOWN, i);
		}

		return inv;

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
		return (side == null || directionMappings.containsKey(side) || holder.hasComponent(ComponentType.Direction) && relativeDirectionMappings.containsKey(BlockEntityUtils.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), side))) && capability == ForgeCapabilities.ITEM_HANDLER;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		return hasCapability(capability, side) ? (LazyOptional<T>) sideWrappers[side == null ? Direction.UP.ordinal() : side.ordinal()] : LazyOptional.empty();
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
		if (!stack.isEmpty()) {
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
	// this is only called through someone instance checking of this class....
	public void setChanged() {
		setChanged(-1);
	}

	public void setChanged(int slot) {
		items.forceDirty();
		if (onChanged != null) {
			onChanged.accept(this, slot);
		}
	}

	public int inputs() {
		return inputs;
	}

	public int outputs() {
		return outputs;
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

	public int biproducts() {
		return biproducts;
	}

	public int bucketInputs() {
		return bucketInputs;
	}

	public int bucketOutputs() {
		return bucketOutputs;
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

	public List<ItemStack> getInputContents() {
		return items.get().subList(getInputStartIndex(), getOutputStartIndex());
	}

	public List<ItemStack> getOutputContents() {
		return items.get().subList(getOutputStartIndex(), getItemBiproductStartIndex());
	}

	public List<ItemStack> getItemBiContents() {
		return items.get().subList(getItemBiproductStartIndex(), getInputBucketStartIndex());
	}

	public List<ItemStack> getInputBucketContents() {
		return items.get().subList(getInputBucketStartIndex(), getOutputBucketStartIndex());
	}

	public List<ItemStack> getOutputBucketContents() {
		return items.get().subList(getOutputBucketStartIndex(), getUpgradeSlotStartIndex());
	}

	public List<ItemStack> getUpgradeContents() {
		return items.get().subList(getUpgradeSlotStartIndex(), items.get().size());
	}

	// processor number is indexed at zero
	public List<ItemStack> getInputsForProcessor(int processor) {
		return getInputContents().subList(inputsPerProc * processor, inputsPerProc * (processor + 1));
	}

	// processor number is indexed at zero
	public List<ItemStack> getOutputsForProcessor(int processor) {
		return getOutputContents().subList(outputsPerProc * processor, outputsPerProc * (processor + 1));
	}

	// processor number is indexed at zero
	public List<ItemStack> getBiprodsForProcessor(int processor) {
		return getInputContents().subList(biprodsPerProc * processor, biprodsPerProc * (processor + 1));
	}

	public List<Integer> getInputSlots() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < inputs; i++) {
			list.add(getInputStartIndex() + i);
		}
		return list;
	}

	public List<Integer> getOutputSlots() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < outputs; i++) {
			list.add(getOutputStartIndex() + i);
		}
		return list;
	}

	public List<Integer> getBiproductSlots() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < biproducts; i++) {
			list.add(getItemBiproductStartIndex() + i);
		}
		return list;
	}

	// processor number is indexed at zero
	public List<Integer> getInputSlotsForProcessor(int processor) {
		return getInputSlots().subList(inputsPerProc * processor, inputsPerProc * (processor + 1));
	}

	// processor number is indexed at zero
	public List<Integer> getOutputSlotsForProcessor(int processor) {
		return getOutputSlots().subList(outputsPerProc * processor, outputsPerProc * (processor + 1));
	}

	// processor number is indexed at zero
	public List<Integer> getBiprodSlotsForProcessor(int processor) {
		return getBiproductSlots().subList(biprodsPerProc * processor, biprodsPerProc * (processor + 1));
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

	public boolean areInputsEmpty() {
		for (ItemStack stack : getInputContents()) {
			if (stack.isEmpty()) {
				return false;
			}
		}
		return false;
	}

	public boolean hasInputRoom() {
		for (ItemStack stack : getInputContents()) {
			if (stack.getMaxStackSize() > stack.getCount()) {
				return true;
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

	public static class InventoryBuilder {
		
		private static final InventoryBuilder EMPTY = new InventoryBuilder();

		private int builderSize = 0;

		private int builderInputs = 0;
		private int builderOutputs = 0;
		private int builderBiproducts = 0;
		private int builderBucketInputs = 0;
		private int builderBucketOutputs = 0;
		private int builderUpgrades = 0;

		private int builderInputsPerProc = 0;
		private int builderOutputsPerProc = 0;
		private int builderBiprodsPerProc = 0;

		private InventoryBuilder() {

		}

		public InventoryBuilder inputs(int inputs) {
			this.builderInputs = inputs;
			return this;
		}

		public InventoryBuilder outputs(int outputs) {
			this.builderOutputs = outputs;
			return this;
		}

		public InventoryBuilder biproducts(int biproducts) {
			this.builderBiproducts = biproducts;
			return this;
		}

		public InventoryBuilder bucketInputs(int bucketInputs) {
			this.builderBucketInputs = bucketInputs;
			return this;
		}

		public InventoryBuilder bucketOutputs(int bucketOutputs) {
			this.builderBucketOutputs = bucketOutputs;
			return this;
		}

		public InventoryBuilder upgrades(int upgrades) {
			this.builderUpgrades = upgrades;
			return this;
		}

		/**
		 * Specialized method for machines that use ComponentProcessors. It removed the need to individually set input, output, and biproduct slots.
		 * 
		 * @param procCount How many ComponentProcessors the machine has
		 * @param inputsPerProc How many inputs are assigned to a processor
		 * @param outputsPerProc How many outputs are assigned to a processor
		 * @param biprodsPerProc how many biproducts are assigned to a processor
		 * @return The mutated inventory builder
		 */
		public InventoryBuilder processors(int procCount, int inputsPerProc, int outputsPerProc, int biprodsPerProc) {

			this.builderInputsPerProc = inputsPerProc;
			this.builderOutputsPerProc = outputsPerProc;
			this.builderBiprodsPerProc = biprodsPerProc;

			this.builderInputs = procCount * inputsPerProc;
			this.builderOutputs = procCount * outputsPerProc;
			this.builderBiproducts = procCount * biprodsPerProc;

			return this;
		}

		/**
		 * This method should not be used in tandem with other individual mutator methods and is designed for inventories that have no specified slot types
		 * @param size The desired size of the inventory
		 * @return The mutated builder
		 */
		public InventoryBuilder forceSize(int size) {
			this.builderSize = size;
			return this;
		}

		public static InventoryBuilder newInv() {
			return new InventoryBuilder();
		}

	}

}
