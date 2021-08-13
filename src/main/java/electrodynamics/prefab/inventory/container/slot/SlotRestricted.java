package electrodynamics.prefab.inventory.container.slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class SlotRestricted extends GenericSlot {
	
    static {
    	if(ModList.get().isLoaded("nuclearscience")) {

    	}
    }
    
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
