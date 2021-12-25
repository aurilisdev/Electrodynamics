package electrodynamics.prefab.inventory.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlotNoModification extends GenericSlot {

	public SlotNoModification(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
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
