package electrodynamics.prefab.utilities;

import java.util.List;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class UtilitiesContainers {

    static final int PLAYER_INV_Y_DEFAULT = 84;
    static final int PLAYER_INV_X_DEFAULT = 8;

    public static ItemStack handleShiftClick(List<Slot> slots, Player player, int slotIndex) {
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
}