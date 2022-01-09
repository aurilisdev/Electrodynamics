package electrodynamics.prefab.inventory.container.slot.itemhandler;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotItemHandlerRestricted extends SlotItemHandler {

	private Class<?>[] classes;
	private boolean isWhitelist;

	public SlotItemHandlerRestricted(IItemHandler itemHandler, int index, int xPosition, int yPosition, boolean isWhitelist, Class<?>... classes) {
		super(itemHandler, index, xPosition, yPosition);
		this.classes = classes;
		this.isWhitelist = isWhitelist;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		for (Class<?> clazz : classes) {
			if ((isWhitelist && clazz.isInstance(stack.getItem())) || !clazz.isInstance(stack.getItem())) {
				return super.mayPlace(stack);
			}
		}
		return false;
	}

}
