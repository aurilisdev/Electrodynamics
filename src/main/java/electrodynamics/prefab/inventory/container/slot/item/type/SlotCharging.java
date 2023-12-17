package electrodynamics.prefab.inventory.container.slot.item.type;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCharging extends SlotGeneric {

	public SlotCharging(IInventory inventory, int index, int x, int y) {
		super(SlotType.NORMAL, IconType.ENERGY_DARK, inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		if (super.mayPlace(stack) && stack.getItem() instanceof IItemElectric) {
			return true;
		}
		return false;
	}

}