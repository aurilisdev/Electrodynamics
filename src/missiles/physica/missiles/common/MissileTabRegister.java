package physica.missiles.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.missiles.MissileReferences;

public class MissileTabRegister implements IContent {

	public static CreativeTabs missilesTab;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.CreativeTabRegister)
		{
			missilesTab = new CreativeTabs(MissileReferences.DOMAIN + "Missiles")
			{
				@Override
				public Item getTabIconItem()
				{
					return Items.arrow;
				}
			};
		}
	}
}
