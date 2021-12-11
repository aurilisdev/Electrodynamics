package electrodynamics.prefab.inventory.container.slot;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class UpgradeSlot extends GenericSlot {

	private List<Item> items;
	
	public UpgradeSlot(Container inventory, int index, int x, int y, SubtypeItemUpgrade... upgrades) {
    	super(inventory, index, x, y);
    	
    	items = new ArrayList<>();
    	for(SubtypeItemUpgrade upg : upgrades) {
    		items.add(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(upg));
    	}
    }
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return items != null && items.contains(stack.getItem());
	}

}
