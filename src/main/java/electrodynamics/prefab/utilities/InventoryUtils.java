package electrodynamics.prefab.utilities;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.item.ItemUtils;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtils {
	public static void addItemsToInventory(Container inv, List<ItemStack> items, int start, int count) {
		for (ItemStack item : items) {
			for (int i = start; i < count; i++) {
				ItemStack contained = inv.getItem(i);
				int room = inv.getMaxStackSize() - contained.getCount();
				int amtAccepted = room >= item.getCount() ? item.getCount() : room;
				if (contained.isEmpty()) {
					inv.setItem(i, new ItemStack(item.getItem(), amtAccepted).copy());
					item.shrink(amtAccepted);
					inv.setChanged();
				} else if (ItemUtils.testItems(item.getItem(), contained.getItem())) {
					contained.grow(amtAccepted);
					item.shrink(amtAccepted);
					inv.setChanged();
				}
			}
		}
	}

	public static void addItemsToItemHandler(IItemHandler inv, List<ItemStack> items) {
		addItemsToItemHandler(inv, items, 0, inv.getSlots());
	}

	public static List<ItemStack> addItemsToItemHandler(IItemHandler inv, List<ItemStack> items, int start, int count) {
		ArrayList<ItemStack> returner = new ArrayList<>();
		for (ItemStack stack : items) {
			returner.add(addItemToItemHandler(inv, stack, start, count));
		}
		return returner;
	}

	public static ItemStack addItemToItemHandler(IItemHandler inv, ItemStack items, int start, int count) {
		for (int i = start; i < count; i++) {
			if (inv.isItemValid(i, items)) {
				if (items.getCount() == 0) {
					break;
				}
				items = inv.insertItem(i, items, false);
			}
		}
		return items;
	}
}
