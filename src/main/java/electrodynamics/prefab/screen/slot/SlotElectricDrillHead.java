package electrodynamics.prefab.screen.slot;

import electrodynamics.common.item.ItemDrillHead;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import voltaic.prefab.inventory.container.slot.itemhandler.SlotItemHandlerGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.SlotType;

public class SlotElectricDrillHead extends SlotItemHandlerGeneric {

	public SlotElectricDrillHead(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(SlotType.NORMAL, IconType.DRILL_HEAD_DARK, itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean mayPickup(PlayerEntity playerIn) {
		return playerIn.inventory.getCarried().getItem() instanceof ItemDrillHead;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack.getItem() instanceof ItemDrillHead;
	}

}
