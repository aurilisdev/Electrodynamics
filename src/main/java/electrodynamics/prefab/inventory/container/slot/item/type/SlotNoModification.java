package electrodynamics.prefab.inventory.container.slot.item.type;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotNoModification extends SlotGeneric {

	public SlotNoModification(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public SlotNoModification(ISlotTexture slot, ITexture icon, IInventory inventory, int index, int x, int y) {
		super(slot, icon, inventory, index, x, y);
	}

	@Override
	public boolean mayPickup(PlayerEntity pl) {
		return false;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}
}