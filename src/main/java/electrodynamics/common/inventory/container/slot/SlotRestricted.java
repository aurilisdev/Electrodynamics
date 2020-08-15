package electrodynamics.common.inventory.container.slot;

import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotRestricted extends GenericSlot {
	private List<Item> whitelist;

	public SlotRestricted(IInventory inventory, int index, int x, int y, Item... items) {
		super(inventory, index, x, y);
		whitelist = Arrays.asList(items);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && whitelist != null && whitelist.contains(stack.getItem());
	}
}
