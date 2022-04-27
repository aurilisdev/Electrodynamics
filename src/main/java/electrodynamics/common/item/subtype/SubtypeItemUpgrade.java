package electrodynamics.common.item.subtype;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.TriConsumer;

import electrodynamics.api.ISubtype;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.tile.TileAdvancedSolarPanel;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.common.tile.TileHydroelectricGenerator;
import electrodynamics.common.tile.TileSolarPanel;
import electrodynamics.common.tile.TileWindmill;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum SubtypeItemUpgrade implements ISubtype {
	basiccapacity((holder, processor, upgrade) -> {
		if (holder instanceof TileBatteryBox box) {
			box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 1.5, Math.pow(1.5, 3));
			box.currentVoltageMultiplier = Math.min(box.currentVoltageMultiplier * 2, 2);
		}
	}, 2),
	basicspeed((holder, processor, upgrade) -> {
		if (processor != null) {
			processor.operatingSpeed = Math.min(processor.operatingSpeed * 1.5, Math.pow(1.5, 3));
		}
	}, 3),
	advancedcapacity((holder, processor, upgrade) -> {
		if (holder instanceof TileBatteryBox box) {
			box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 2.25, Math.pow(2.25, 3));
			box.currentVoltageMultiplier = Math.min(box.currentVoltageMultiplier * 4, 4);
		}
	}, 4),
	advancedspeed((holder, processor, upgrade) -> {
		if (processor != null) {
			processor.operatingSpeed = Math.min(processor.operatingSpeed * 2.25, Math.pow(2.25, 3));
		}
	}, 3),
	// the only way to optimize this one further is to increase the tick delay.
	// Currently, it's set to every 4 ticks
	iteminput((holder, processor, upgrade) -> {
		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
		if (inv.hasInputRoom()) {
			CompoundTag tag = upgrade.getOrCreateTag();
			int tickNumber = tag.getInt(NBTUtils.TIMER);
			if (tickNumber >= 4) {
				tag.putInt(NBTUtils.TIMER, 0);
				List<Direction> dirs = NBTUtils.readDirectionList(upgrade);
				boolean isSmart = tag.getBoolean(NBTUtils.SMART);
				if (isSmart) {
					int slot;
					Direction dir = Direction.DOWN;
					for (int i = 0; i < inv.getInputSlots().size(); i++) {
						slot = inv.getInputSlots().get(i);
						if (i < dirs.size()) {
							dir = dirs.get(i);
						}
						inputSmartMode(getBlockEntity(holder, dir), inv, slot, dir);
					}
				} else {
					for (Direction dir : dirs) {
						inputDefaultMode(getBlockEntity(holder, dir), inv, dir);
					}
				}
			}
			tag.putInt(NBTUtils.TIMER, tag.getInt(NBTUtils.TIMER) + 1);
		}
	}, 1),
	// I can't really optimize this one any more than it is
	itemoutput((holder, processor, upgrade) -> {
		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
		if (inv.hasItemsInOutput()) {
			CompoundTag tag = upgrade.getOrCreateTag();
			int tickNumber = tag.getInt(NBTUtils.TIMER);
			if (tickNumber >= 4) {
				tag.putInt(NBTUtils.TIMER, 0);
				List<Direction> dirs = NBTUtils.readDirectionList(upgrade);
				boolean isSmart = tag.getBoolean(NBTUtils.SMART);
				if (isSmart) {
					List<ItemStack> combinedItems = new ArrayList<>(inv.getOutputContents());
					combinedItems.addAll(inv.getItemBiContents());
					ItemStack stack;
					Direction dir = Direction.DOWN;
					for (int i = 0; i < combinedItems.size(); i++) {
						stack = combinedItems.get(i);
						if (i < dirs.size()) {
							dir = dirs.get(i);
						}
						outputSmartMode(getBlockEntity(holder, dir), stack, dir);
					}
				} else {
					for (Direction dir : dirs) {
						outputDefaultMode(getBlockEntity(holder, dir), inv, dir);
					}
				}
			}
			tag.putInt(NBTUtils.TIMER, tag.getInt(NBTUtils.TIMER) + 1);
		}
	}, 1),
	improvedsolarcell((holder, processor, upgrade) -> {
		if (holder instanceof IElectricGenerator generator && (holder instanceof TileSolarPanel || holder instanceof TileAdvancedSolarPanel)) {
			generator.setMultiplier(2.25);
		}
	}, 1),
	stator((holder, processor, upgrade) -> {
		if (holder instanceof IElectricGenerator generator && (holder instanceof TileWindmill || holder instanceof TileHydroelectricGenerator)) {
			generator.setMultiplier(2.25);
		}
	}, 1),
	range((holder, processor, upgrade) -> {
		/* it does nothing; the count determines the new range */}, 12),
	experience((holder, processor, upgrade) -> {
		/* the machine handles adding the experience */}, 1),
	itemvoid((holder, processor, upgrade) -> {
	}, 1),
	silktouch((holder, processor, upgrade) -> {
	}, 1),
	fortune((holder, processor, upgrade) -> {
	}, 3),
	unbreaking((holder, processor, upgrade) -> {
	}, 3);

	public final TriConsumer<GenericTile, ComponentProcessor, ItemStack> applyUpgrade;
	public final int maxSize;

	SubtypeItemUpgrade(TriConsumer<GenericTile, ComponentProcessor, ItemStack> applyUpgrade, int maxSize) {
		this.applyUpgrade = applyUpgrade;
		this.maxSize = maxSize;
	}

	@Override
	public String tag() {
		return "upgrade" + name();
	}

	@Override
	public String forgeTag() {
		return "upgrade/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}

	private static void inputSmartMode(BlockEntity entity, ComponentInventory inv, int slot, Direction dir) {
		if (entity instanceof Container container) {
			attemptContainerExtract(inv, slot, container, dir);
		} else if (entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if (otherInv != null) {
				takeItemFromCompInv(inv, slot, otherInv, dir);
			}
		}
	}

	private static void inputDefaultMode(BlockEntity entity, ComponentInventory inv, Direction dir) {
		if (entity instanceof Container container) {
			for (int slot : inv.getInputSlots()) {
				attemptContainerExtract(inv, slot, container, dir);
			}
		} else if (entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if (otherInv != null) {
				for (int slot : inv.getInputSlots()) {
					takeItemFromCompInv(inv, slot, otherInv, dir);
				}
			}
		}
	}

	private static void outputSmartMode(BlockEntity entity, ItemStack stack, Direction dir) {
		if (entity instanceof Container container) {
			attemptContainerInsert(stack, container, dir);
		} else if (entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if (otherInv != null) {
				addItemToCompInv(stack, otherInv, dir);
			}

		}
	}

	private static void outputDefaultMode(BlockEntity entity, ComponentInventory inv, Direction dir) {
		if (entity instanceof Container container) {
			List<ItemStack> combined = inv.getOutputContents();
			combined.addAll(inv.getItemBiContents());
			for (ItemStack stack : combined) {
				attemptContainerInsert(stack, container, dir);
			}
		} else if (entity != null && entity instanceof GenericTile tile) {
			ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
			if (otherInv != null) {
				for (ItemStack stack : inv.getOutputContents()) {
					addItemToCompInv(stack, otherInv, dir);
				}
			}
		}
	}

	private static void attemptContainerInsert(ItemStack stack, Container container, Direction dir) {
		if (container instanceof WorldlyContainer worldly) {
			for (int slot : worldly.getSlotsForFace(dir)) {
				addItemToContainer(stack, container, slot);
			}
		} else {
			for (int i = 0; i < container.getContainerSize(); i++) {
				addItemToContainer(stack, container, i);
			}
		}
	}

	private static void attemptContainerExtract(ComponentInventory inv, int slot, Container container, Direction dir) {
		if (container instanceof WorldlyContainer worldly) {
			for (int containerSlot : worldly.getSlotsForFace(dir)) {
				takeItemFromContainer(inv, slot, container, container.getItem(containerSlot));
			}
		} else {
			for (int i = 0; i < container.getContainerSize(); i++) {
				takeItemFromContainer(inv, slot, container, container.getItem(i));
			}
		}
	}

	private static void takeItemFromContainer(ComponentInventory inv, int slot, Container container, ItemStack containerItem) {
		if (inv.canPlaceItem(slot, containerItem)) {
			ItemStack invItem = inv.getItem(slot);
			if (invItem.isEmpty() && !containerItem.isEmpty()) {
				int room = inv.getMaxStackSize();
				int amtAccepted = room >= containerItem.getCount() ? containerItem.getCount() : room;
				inv.setItem(slot, new ItemStack(containerItem.getItem(), amtAccepted).copy());
				containerItem.shrink(amtAccepted);
				container.setChanged();
			} else if (!containerItem.isEmpty() && ItemUtils.testItems(invItem.getItem(), containerItem.getItem()) && inv.getMaxStackSize() >= invItem.getMaxStackSize()) {
				int room = invItem.getMaxStackSize() - invItem.getCount();
				int amtAccepted = room >= containerItem.getCount() ? containerItem.getCount() : room;
				invItem.grow(amtAccepted);
				containerItem.shrink(amtAccepted);
				container.setChanged();
			}
		}

	}

	public static void addItemToContainer(ItemStack stack, Container container, int slot) {
		if (!stack.isEmpty()) {
			if (container.canPlaceItem(slot, stack)) {
				ItemStack contained = container.getItem(slot);
				int room = container.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
				if (contained.isEmpty()) {
					container.setItem(slot, new ItemStack(stack.getItem(), amtAccepted).copy());
					stack.shrink(amtAccepted);
					container.setChanged();
				} else if (ItemUtils.testItems(stack.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					stack.shrink(amtAccepted);
					container.setChanged();
				}
			}
		}
	}

	private static void addItemToCompInv(ItemStack stack, ComponentInventory otherInv, Direction dir) {
		for (int i : otherInv.getSlotsForFace(dir.getOpposite())) {
			if (otherInv.canPlaceItem(i, stack)) {
				ItemStack contained = otherInv.getItem(i);
				int room = otherInv.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
				if (contained.isEmpty()) {
					otherInv.setItem(i, new ItemStack(stack.getItem(), amtAccepted).copy());
					stack.shrink(amtAccepted);
					otherInv.setChanged();
				} else if (ItemUtils.testItems(stack.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					stack.shrink(amtAccepted);
					otherInv.setChanged();
				}

			}
		}
	}

	private static void takeItemFromCompInv(ComponentInventory inv, int slot, ComponentInventory otherInv, Direction dir) {
		List<ItemStack> combinedOutputs = new ArrayList<>(otherInv.getOutputContents());
		combinedOutputs.addAll(otherInv.getItemBiContents());
		ItemStack invItem;
		for (ItemStack stack : combinedOutputs) {
			if (inv.canPlaceItem(slot, stack)) {
				invItem = inv.getItem(slot);
				int room = inv.getMaxStackSize() - invItem.getCount();
				int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;
				if (invItem.isEmpty()) {
					inv.setItem(slot, new ItemStack(stack.getItem(), amtAccepted).copy());
					stack.shrink(amtAccepted);
					otherInv.setChanged();
				} else if (ItemUtils.testItems(stack.getItem(), invItem.getItem())) {
					invItem.grow(amtAccepted);
					stack.shrink(amtAccepted);
					otherInv.setChanged();
				}
			}
		}

	}

	private static BlockEntity getBlockEntity(GenericTile holder, Direction dir) {
		BlockPos pos = holder.getBlockPos().relative(dir);
		BlockState state = holder.getLevel().getBlockState(pos);
		if (state.hasBlockEntity()) {
			return holder.getLevel().getBlockEntity(holder.getBlockPos().relative(dir));
		}
		return null;
	}
}
