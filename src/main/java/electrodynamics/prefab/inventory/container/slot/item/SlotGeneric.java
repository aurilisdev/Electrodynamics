package electrodynamics.prefab.inventory.container.slot.item;

import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotGeneric extends Slot {

	public SlotGeneric(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack != null && container.canPlaceItem(getSlotIndex(), stack);
	}

	public EnumSlotType getSlotType() {
		return EnumSlotType.NORMAL;
	}
	
	@Override
	public void setChanged() {
		if(container instanceof ComponentInventory inv) {
			inv.setChanged(index);
		} else {
			super.setChanged();
		}
	}

}