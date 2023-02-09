package electrodynamics.prefab.inventory.container.slot.item.type;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlotNoModification extends SlotGeneric {

	public SlotNoModification(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public SlotNoModification(ISlotTexture slot, ITexture icon, Container inventory, int index, int x, int y) {
		super(slot, icon, inventory, index, x, y);
	}

	@Override
	public boolean allowModification(Player pl) {
		return false;
	}

	@Override
	public boolean mayPickup(Player pl) {
		return false;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}
}
