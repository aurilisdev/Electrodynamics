package electrodynamics.prefab.inventory.container.slot.item.type;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

public class SlotRestricted extends SlotGeneric {

	private List<Item> whitelist;
	private List<Class<?>> classes;
	private List<Capability<?>> validCapabilities;

	public SlotRestricted(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public SlotRestricted(ISlotTexture slot, ITexture icon, Container inv, int index, int x, int y) {
		super(slot, icon, inv, index, x, y);
	}
	
	public SlotRestricted setRestriction(Item... items) {
		whitelist = Arrays.asList(items);
		return this;
	}
	
	public SlotRestricted setRestriction(Class<?>... items) {
		classes = Arrays.asList(items);
		return this;
	}
	
	public SlotRestricted setRestriction(Capability<?>... capabilities) {
		validCapabilities = Arrays.asList(capabilities);
		return this;
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
