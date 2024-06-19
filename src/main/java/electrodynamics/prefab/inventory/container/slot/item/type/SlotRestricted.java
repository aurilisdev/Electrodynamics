package electrodynamics.prefab.inventory.container.slot.item.type;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class SlotRestricted extends SlotGeneric {

    private List<Item> whitelist;
    private List<Class<?>> classes;
    private List<ItemCapability<?, Void>> validCapabilities;

    private Predicate<ItemStack> mayPlace = stack -> false;

    public SlotRestricted(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public SlotRestricted(ISlotTexture slot, ITexture icon, Container inv, int index, int x, int y) {
        super(slot, icon, inv, index, x, y);
    }

    public SlotRestricted setRestriction(Predicate<ItemStack> mayPlace) {
        this.mayPlace = mayPlace;
        return this;
    }

    public SlotRestricted setRestriction(Item... items) {
        whitelist = Arrays.asList(items);
        mayPlace = stack -> whitelist.contains(stack.getItem());
        return this;
    }

    public SlotRestricted setRestriction(Class<?>... items) {
        classes = Arrays.asList(items);
        mayPlace = stack -> {
            if (classes != null) {
                for (Class<?> cl : classes) {
                    if (cl.isInstance(stack.getItem())) {
                        return true;
                    }
                }
            }
            return false;
        };
        return this;
    }

    public SlotRestricted setRestriction(ItemCapability<?, Void>... capabilities) {
        validCapabilities = Arrays.asList(capabilities);
        mayPlace = stack -> {
            if (validCapabilities != null) {
                for (ItemCapability<?, Void> cap : validCapabilities) {
                    if (stack.getCapability(cap) != null) {
                        return true;
                    }
                }
            }
            return false;
        };
        return this;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return super.mayPlace(stack) && mayPlace.test(stack);
    }
}
