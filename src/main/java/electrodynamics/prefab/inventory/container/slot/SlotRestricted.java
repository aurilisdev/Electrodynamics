package electrodynamics.prefab.inventory.container.slot;

import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotRestricted extends GenericSlot {

    private List<Item> whitelist;
    private List<Class<?>> classes;

    public SlotRestricted(IInventory inventory, int index, int x, int y, Item... items) {
	super(inventory, index, x, y);
	whitelist = Arrays.asList(items);
    }

    public SlotRestricted(IInventory inventory, int index, int x, int y, boolean holder, Class<?>... items) {
	super(inventory, index, x, y);
	classes = Arrays.asList(items);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
	if (super.isItemValid(stack)) {
	    if (classes != null) {
		for (Class<?> cl : classes) {
		    if (cl.isInstance(stack.getItem())) {
		    	return true;
		    }
		}
	    } else {
		return whitelist != null && whitelist.contains(stack.getItem());
	    }
	}
	return false;
    }
}
