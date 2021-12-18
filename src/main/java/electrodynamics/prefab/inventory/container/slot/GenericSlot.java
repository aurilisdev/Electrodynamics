package electrodynamics.prefab.inventory.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GenericSlot extends Slot {

	public GenericSlot(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack != null && container.canPlaceItem(getSlotIndex(), stack);
	}

}