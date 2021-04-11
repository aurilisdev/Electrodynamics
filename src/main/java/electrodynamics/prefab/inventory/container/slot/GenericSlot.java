package electrodynamics.prefab.inventory.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class GenericSlot extends Slot {

    public GenericSlot(IInventory inventory, int index, int x, int y) {
	super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
	return stack != null && inventory.isItemValidForSlot(getSlotIndex(), stack);
    }

}