package electrodynamics.prefab.inventory.container.slot.item.type;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotUpgrade extends SlotGeneric {

	private List<Item> items;

	public SlotUpgrade(Container inventory, int index, int x, int y, SubtypeItemUpgrade... upgrades) {
		super(inventory, index, x, y);

		items = new ArrayList<>();
		for (SubtypeItemUpgrade upg : upgrades) {
			items.add(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(upg));
		}
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return items != null && items.contains(stack.getItem());
	}
	
	@Override
	public EnumSlotType getSlotType() {
		return EnumSlotType.SPEED;
	}

}
