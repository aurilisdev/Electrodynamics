package electrodynamics.prefab.inventory.container.slot.item.type;

import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SlotCharging extends SlotGeneric {

	public SlotCharging(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		if(super.mayPlace(stack) && stack.getItem() instanceof ItemElectric) {
			return true;
		}
		return false;
	}
	
	@Override
	public EnumSlotType getSlotType() {
		return EnumSlotType.BATTERY;
	}

}
