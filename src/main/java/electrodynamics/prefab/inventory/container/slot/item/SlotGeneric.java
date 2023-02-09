package electrodynamics.prefab.inventory.container.slot.item;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotGeneric extends Slot {

	private final ISlotTexture slotType;
	private final ITexture iconType;
	
	public SlotGeneric(ISlotTexture slotType, ITexture iconType, Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		this.slotType = slotType;
		this.iconType = iconType;
	}
	
	public SlotGeneric(Container inventory, int index, int x, int y) {
		this(SlotType.NORMAL, IconType.NONE, inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack != null && container.canPlaceItem(getSlotIndex(), stack);
	}

	public ISlotTexture getSlotType() {
		return slotType;
	}
	
	public ITexture getIconType() {
		return iconType;
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