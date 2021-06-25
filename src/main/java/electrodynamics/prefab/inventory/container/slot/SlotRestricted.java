package electrodynamics.prefab.inventory.container.slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotRestricted extends GenericSlot {
    private List<Item> whitelist;

    public SlotRestricted(IInventory inventory, int index, int x, int y, Item... items) {
	super(inventory, index, x, y);
	whitelist = Arrays.asList(items);
    }

    public SlotRestricted(IInventory inventory, int index, int x, int y, Fluid[] inputFluids) {
	super(inventory, index, x, y);
	List<Item> fluidBuckets = new ArrayList<>();
	for (Fluid fluid : inputFluids) {
	    fluidBuckets.add(fluid.getFilledBucket());
	}
	whitelist = fluidBuckets;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
	return super.isItemValid(stack) && whitelist != null && whitelist.contains(stack.getItem());
    }
}
