package electrodynamics.prefab.inventory.container.slot.item.type;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentSlot.EnumSlotType;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SlotCharging extends SlotGeneric {

	public SlotCharging(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		if (super.mayPlace(stack) && stack.getItem() instanceof IItemElectric) {
			return true;
		}
		return false;
	}

	@Override
	public EnumSlotType getSlotType() {
		return EnumSlotType.BATTERY;
	}

}
