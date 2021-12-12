package electrodynamics.prefab.inventory.container.slot;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

public class SlotRestricted extends GenericSlot {

    private List<Item> whitelist;
    private List<Class<?>> classes;
    private List<Capability<?>> validCapabilities;

    public SlotRestricted(Container inventory, int index, int x, int y, Item... items) {
	super(inventory, index, x, y);
	whitelist = Arrays.asList(items);
    }

    public SlotRestricted(Container inventory, int index, int x, int y, boolean holder, Class<?>... items) {
	super(inventory, index, x, y);
	classes = Arrays.asList(items);
    }

    public SlotRestricted(Container inv, int index, int x, int y, int holder, Capability<?>... capabilities) {
	super(inv, index, x, y);
	validCapabilities = Arrays.asList(capabilities);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
	if (super.mayPlace(stack)) {
	    if (validCapabilities != null) {
		for (Capability<?> cap : validCapabilities) {
		    if (stack.getCapability(cap).map(m -> true).orElse(false)) {
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
