package electrodynamics.prefab.inventory.container.slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeCanister;
import electrodynamics.common.item.subtype.SubtypeLeadCanister;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotRestricted extends GenericSlot {

    public static final Item[] VALID_EMPTY_BUCKETS = new Item[] { Items.BUCKET, DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCanister.empty),
	    DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeLeadCanister.empty) };

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

    public SlotRestricted(IInventory inventory, int index, int x, int y, Fluid[] inputFluids) {
	super(inventory, index, x, y);
	List<Item> fluidBuckets = new ArrayList<>();
	for (Fluid fluid : inputFluids) {
	    Item bucket = fluid.getFilledBucket();
	    if (bucket != null) {
		fluidBuckets.add(bucket);
	    }
	}
	whitelist = fluidBuckets;
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
