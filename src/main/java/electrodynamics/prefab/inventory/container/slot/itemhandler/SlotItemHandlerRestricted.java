package electrodynamics.prefab.inventory.container.slot.itemhandler;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotItemHandlerRestricted extends SlotItemHandler {

	private Class<?> validClass;
	
	public SlotItemHandlerRestricted(IItemHandler itemHandler, int index, int xPosition, int yPosition, Class<?> validClass) {
		super(itemHandler, index, xPosition, yPosition);
		this.validClass = validClass;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		if(validClass.isInstance(stack.getItem())) {
			return super.mayPlace(stack);
		}
		return false;
	}

}
