package physica.core.common.items;

import physica.CoreReferences;
import physica.core.common.CoreTabRegister;
import physica.library.item.ItemElectric;

public class ItemBattery extends ItemElectric {

	public ItemBattery(String name) {
		super((int) (250000 / 0.4D));
		setUnlocalizedName(name);
		setTextureName(CoreReferences.PREFIX + name);
		setCreativeTab(CoreTabRegister.coreTab);
	}

}
