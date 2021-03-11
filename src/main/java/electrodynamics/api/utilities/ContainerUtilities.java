package electrodynamics.api.utilities;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public final class ContainerUtilities {

    static final int PLAYER_INV_Y_DEFAULT = 84;
    static final int PLAYER_INV_X_DEFAULT = 8;

    public static ItemStack handleShiftClick(List<Slot> slots, PlayerEntity player, int slotIndex) {
	Slot sourceSlot = slots.get(slotIndex);
	ItemStack inputStack = sourceSlot.getStack();
	if (inputStack == null) {
	    return null;
	}

	boolean sourceIsPlayer = sourceSlot.inventory == player.inventory;

	ItemStack copy = inputStack.copy();

	if (sourceIsPlayer) {
	    if (!mergeStack(player.inventory, false, sourceSlot, slots, false)) {
		return ItemStack.EMPTY;
	    }
	    return copy;
	}
	boolean isMachineOutput = !sourceSlot.isItemValid(inputStack);
	if (!mergeStack(player.inventory, true, sourceSlot, slots, !isMachineOutput)) {
	    return ItemStack.EMPTY;
	}
	return copy;
    }

    private static boolean mergeStack(PlayerInventory playerInv, boolean mergeIntoPlayer, Slot sourceSlot,
	    List<Slot> slots, boolean reverse) {
	ItemStack sourceStack = sourceSlot.getStack();

	int originalSize = sourceStack.getCount();

	int len = slots.size();
	int idx;
	if (sourceStack.isStackable()) {
	    idx = reverse ? len - 1 : 0;

	    while (sourceStack.getCount() > 0 && (reverse ? idx >= 0 : idx < len)) {
		Slot targetSlot = slots.get(idx);
		if (targetSlot.inventory == playerInv == mergeIntoPlayer) {
		    ItemStack target = targetSlot.getStack();
		    if (ItemStack.areItemStacksEqual(sourceStack, target)) { // also checks target != null, because
									     // stack is never null
			int targetMax = Math.min(targetSlot.getSlotStackLimit(), target.getMaxStackSize());
			int toTransfer = Math.min(sourceStack.getCount(), targetMax - target.getCount());
			if (toTransfer > 0) {
			    target.setCount(target.getCount() + toTransfer);
			    sourceStack.setCount(sourceStack.getCount() - toTransfer);
			    targetSlot.onSlotChanged();
			}
		    }
		}

		if (reverse) {
		    idx--;
		} else {
		    idx++;
		}
	    }
	    if (sourceStack.getCount() == 0) {
		sourceSlot.putStack(ItemStack.EMPTY);
		return true;
	    }
	}

	// 2nd pass: try to put anything remaining into a free slot
	idx = reverse ? len - 1 : 0;
	while (reverse ? idx >= 0 : idx < len) {
	    Slot targetSlot = slots.get(idx);
	    if (targetSlot.inventory == playerInv == mergeIntoPlayer && !targetSlot.getHasStack()
		    && targetSlot.isItemValid(sourceStack)) {
		targetSlot.putStack(sourceStack.copy());
		sourceSlot.putStack(ItemStack.EMPTY);
		sourceStack.setCount(0);
		return true;
	    }

	    if (reverse) {
		idx--;
	    } else {
		idx++;
	    }
	}

	// we had success in merging only a partial stack
	if (sourceStack.getCount() != originalSize) {
	    sourceSlot.onSlotChanged();
	    return true;
	}
	return false;
    }
}