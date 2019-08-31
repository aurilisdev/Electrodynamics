package physica.core.common.items;

import physica.CoreReferences;
import physica.core.common.CoreTabRegister;
import physica.library.item.ItemElectric;

public class ItemBattery extends ItemElectric {

	public ItemBattery(String name) {
		super((int) (250000 / 0.4));
		setMaxTransfer((int) (2500 / 0.4));
		setUnlocalizedName(name);
		setTextureName(CoreReferences.PREFIX + name.toLowerCase());
		setCreativeTab(CoreTabRegister.coreTab);
	}
}
