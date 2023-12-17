package electrodynamics.prefab.inventory.container.slot.itemhandler.type;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.slot.itemhandler.SlotItemHandlerGeneric;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;

public class SlotItemHandlerRestricted extends SlotItemHandlerGeneric {

	private List<Item> whitelist;
	private List<Class<?>> classes;
	private List<Capability<?>> validCapabilities;

	private Predicate<ItemStack> mayPlace = stack -> false;

	public SlotItemHandlerRestricted(ISlotTexture slotTexture, ITexture iconTexture, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(slotTexture, iconTexture, itemHandler, index, xPosition, yPosition);
	}

	public SlotItemHandlerRestricted setRestriction(Predicate<ItemStack> mayPlace) {
		this.mayPlace = mayPlace;
		return this;
	}

	public SlotItemHandlerRestricted setRestriction(Item... items) {
		whitelist = Arrays.asList(items);
		mayPlace = stack -> whitelist.contains(stack.getItem());
		return this;
	}

	public SlotItemHandlerRestricted setRestriction(Class<?>... items) {
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

	public SlotItemHandlerRestricted setRestriction(Capability<?>... capabilities) {
		validCapabilities = Arrays.asList(capabilities);
		mayPlace = stack -> {
			if (validCapabilities != null) {
				for (Capability<?> cap : validCapabilities) {
					if (stack.getCapability(cap).map(m -> true).orElse(false)) {
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
