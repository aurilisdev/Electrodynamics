package electrodynamics.common.item;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.creativetab.CreativeTabSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemElectrodynamics extends Item implements CreativeTabSupplier {

	private final Supplier<CreativeModeTab> creativeTab;

	public ItemElectrodynamics(Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(properties);
		this.creativeTab = creativeTab;
	}

	@Override
	public void addCreativeModeItems(CreativeModeTab tab, List<ItemStack> items) {
		items.add(new ItemStack(this));
	}

	@Override
	public boolean isAllowedInCreativeTab(CreativeModeTab tab) {
		return creativeTab.get() == tab;
	}

	@Override
	public boolean hasCreativeTab() {
		return creativeTab != null;
	}

}
