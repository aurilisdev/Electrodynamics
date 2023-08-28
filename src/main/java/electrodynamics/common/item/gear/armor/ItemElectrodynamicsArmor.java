package electrodynamics.common.item.gear.armor;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.creativetab.CreativeTabSupplier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemElectrodynamicsArmor extends ArmorItem implements CreativeTabSupplier {

	private final Supplier<CreativeModeTab> creativeTab;

	public ItemElectrodynamicsArmor(ArmorMaterial material, Type type, Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(material, type, properties);
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
