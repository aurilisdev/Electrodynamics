package electrodynamics.common.tab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupElectrodynamics extends ItemGroup {
	private Item icon;

	public ItemGroupElectrodynamics(String label, Item icon) {
		super(label);
		this.icon = icon;
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(icon);
	}
}
