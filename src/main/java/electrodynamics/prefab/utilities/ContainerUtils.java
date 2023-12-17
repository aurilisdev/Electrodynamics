package electrodynamics.prefab.utilities;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public final class ContainerUtils {

	static final int PLAYER_INV_Y_DEFAULT = 84;
	static final int PLAYER_INV_X_DEFAULT = 8;

	public static ItemStack handleShiftClick(List<Slot> slots, PlayerEntity player, int slotIndex) {
		Slot sourceSlot = slots.get(slotIndex);
		ItemStack inputStack = sourceSlot.getItem();
		if (inputStack == null) {
			return null;
		}

		boolean sourceIsPlayer = sourceSlot.container == player.inventory;

		ItemStack copy = inputStack.copy();

		if (sourceIsPlayer) {
			if (!mergeStack(player.inventory, false, sourceSlot, slots, false)) {
				return ItemStack.EMPTY;
			}
			return copy;
		}
		boolean isMachineOutput = !sourceSlot.mayPlace(inputStack);
		if (!mergeStack(player.inventory, true, sourceSlot, slots, !isMachineOutput)) {
			return ItemStack.EMPTY;
		}
		return copy;
	}

	private static boolean mergeStack(PlayerInventory playerInv, boolean mergeIntoPlayer, Slot sourceSlot, List<Slot> slots, boolean reverse) {
		ItemStack sourceStack = sourceSlot.getItem();

		int originalSize = sourceStack.getCount();

		int len = slots.size();
		int idx;
		if (sourceStack.isStackable()) {
			idx = reverse ? len - 1 : 0;

			while (sourceStack.getCount() > 0 && (reverse ? idx >= 0 : idx < len)) {
				Slot targetSlot = slots.get(idx);
				if (targetSlot.container == playerInv == mergeIntoPlayer) {
					ItemStack target = targetSlot.getItem();
					if (sourceStack.getItem() == target.getItem()) {
						int targetMax = Math.min(targetSlot.getMaxStackSize(), target.getMaxStackSize());
						int toTransfer = Math.min(sourceStack.getCount(), targetMax - target.getCount());
						if (toTransfer > 0) {
							target.setCount(target.getCount() + toTransfer);
							sourceStack.setCount(sourceStack.getCount() - toTransfer);
							targetSlot.setChanged();
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
				sourceSlot.set(ItemStack.EMPTY);
				return true;
			}
		}

		// 2nd pass: try to put anything remaining into a free slot
		idx = reverse ? len - 1 : 0;
		while (reverse ? idx >= 0 : idx < len) {
			Slot targetSlot = slots.get(idx);
			if (targetSlot.container == playerInv == mergeIntoPlayer && !targetSlot.hasItem() && targetSlot.mayPlace(sourceStack)) {
				targetSlot.set(sourceStack.copy());
				sourceSlot.set(ItemStack.EMPTY);
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
			sourceSlot.setChanged();
			return true;
		}
		return false;
	}
}