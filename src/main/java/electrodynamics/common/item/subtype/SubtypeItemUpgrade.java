package electrodynamics.common.item.subtype;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.TriConsumer;

import electrodynamics.api.ISubtype;
import electrodynamics.api.capability.dirstorage.CapabilityDirectionalStorage;
import electrodynamics.api.capability.dirstorage.ICapabilityDirectionalStorage;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum SubtypeItemUpgrade implements ISubtype {
    basiccapacity((holder, processor, upgrade) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 1.5, Math.pow(2.25, 3));
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 2);
	}
    }, 2),
    basicspeed((holder, processor, upgrade) -> {
	if (processor != null) {
	    processor.operatingSpeed = Math.min(processor.operatingSpeed * 1.5, Math.pow(2.25, 3));
	}
    }, 3),
    advancedcapacity((holder, processor, upgrade) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 2.25, Math.pow(2.25, 3));
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 4);
	}
    }, 4),
    advancedspeed((holder, processor, upgrade) -> {
	if (processor != null) {
	    processor.operatingSpeed = Math.min(processor.operatingSpeed * 2.25, Math.pow(2.25, 3));
	}
    }, 3),
    itemoutput((holder, processor, upgrade) -> {
	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	if (CapabilityDirectionalStorage.DIR_STORAGE_CAPABILITY != null) {
	    List<Direction> dirs = upgrade.getCapability(CapabilityDirectionalStorage.DIR_STORAGE_CAPABILITY)
		    .map(ICapabilityDirectionalStorage::getDirections).orElse(new ArrayList<>());
	    boolean isSmart = upgrade.getCapability(CapabilityDirectionalStorage.DIR_STORAGE_CAPABILITY)
		    .map(ICapabilityDirectionalStorage::getBoolean).orElse(false);
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
		    smartMode(getBlockEntity(holder, dir), stack, dir);
		}
	    } else {
		for (Direction dir : dirs) {
		    defaultMode(getBlockEntity(holder, dir), inv, dir);
		}
	    }
	}
    }, 1);

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

    private static void smartMode(BlockEntity entity, ItemStack stack, Direction dir) {
	if (entity instanceof Container container) {
	    attemptContainerInsert(stack, container);
	} else if (entity != null && entity instanceof GenericTile tile) {
	    ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
	    if (otherInv != null) {
		attemptCompInvInsert(stack, otherInv, dir);
	    }
	}
    }

    private static void defaultMode(BlockEntity entity, ComponentInventory inv, Direction dir) {
	if (entity instanceof Container container) {
	    for (ItemStack stack : inv.getOutputContents()) {
		attemptContainerInsert(stack, container);
	    }
	} else if (entity != null && entity instanceof GenericTile tile) {
	    ComponentInventory otherInv = tile.getComponent(ComponentType.Inventory);
	    if (otherInv != null) {
		for (ItemStack stack : inv.getOutputContents()) {
		    attemptCompInvInsert(stack, otherInv, dir);
		}
	    }

	}
    }

    private static void attemptContainerInsert(ItemStack stack, Container container) {
	for (int i = 0; i < container.getContainerSize(); i++) {
	    if (!stack.isEmpty()) {
		if (container.canPlaceItem(i, stack)) {
		    ItemStack contained = container.getItem(i);
		    int room = container.getMaxStackSize() - contained.getCount();
		    int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;

		    if (contained.isEmpty()) {
			container.setItem(i, new ItemStack(stack.getItem(), amtAccepted).copy());
			stack.shrink(amtAccepted);
		    } else if (ItemUtils.testItems(stack.getItem(), contained.getItem())) {
			contained.grow(amtAccepted);
			stack.shrink(amtAccepted);
		    }
		    container.setChanged();
		}
	    }
	}
    }

    private static void attemptCompInvInsert(ItemStack stack, ComponentInventory otherInv, Direction dir) {
	for (int i : otherInv.getSlotsForFace(dir.getOpposite())) {
	    if (otherInv.canPlaceItem(i, stack)) {
		ItemStack contained = otherInv.getItem(i);
		int room = otherInv.getMaxStackSize() - contained.getCount();
		int amtAccepted = room >= stack.getCount() ? stack.getCount() : room;

		if (contained.isEmpty()) {
		    otherInv.setItem(i, new ItemStack(stack.getItem(), amtAccepted).copy());
		    stack.shrink(amtAccepted);
		} else if (ItemUtils.testItems(stack.getItem(), contained.getItem())) {
		    contained.grow(amtAccepted);
		    stack.shrink(amtAccepted);
		}
		otherInv.setChanged();
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
