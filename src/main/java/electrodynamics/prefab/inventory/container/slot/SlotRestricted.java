package electrodynamics.prefab.inventory.container.slot;

import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

public class SlotRestricted extends GenericSlot {

    private List<Item> whitelist;
    private List<Class<?>> classes;
    private List<Capability<?>> validCapabilities;

    public SlotRestricted(IInventory inventory, int index, int x, int y, Item... items) {
		super(inventory, index, x, y);
		whitelist = Arrays.asList(items);
    }

    public SlotRestricted(IInventory inventory, int index, int x, int y, boolean holder, Class<?>... items) {
		super(inventory, index, x, y);
		classes = Arrays.asList(items);
    }
    
    public SlotRestricted(IInventory inv, int index, int x, int y, int holder, Capability<?>... capabilities) {
    	super(inv, index, x, y);
    	validCapabilities = Arrays.asList(capabilities);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
		if (super.isItemValid(stack)) {
		    if(validCapabilities != null) {
		    	for(Capability<?> cap : validCapabilities) {
		    		if(stack.getCapability(cap).map(m ->{return true;}).orElse(false)) {
		    			return true;
		    		}
		    	}
		    }
			if (classes != null) {
				for (Class<?> cl : classes) {
				    if (cl.isInstance(stack.getItem())) {
				    	return true;
				    }
				}
		    }
		    	
		return whitelist != null && whitelist.contains(stack.getItem());
	}
	return false;
    }
}
