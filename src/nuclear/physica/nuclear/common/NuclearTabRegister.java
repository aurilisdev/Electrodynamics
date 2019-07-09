package physica.nuclear.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import physica.api.core.IContent;
import physica.nuclear.NuclearReferences;

public class NuclearTabRegister implements IContent {

	public static CreativeTabs nuclearPhysicsTab;

	@Override
	public void preInit()
	{
		nuclearPhysicsTab = new CreativeTabs(NuclearReferences.DOMAIN + "NuclearPhysics")
		{

			@Override
			public Item getTabIconItem()
			{
				return NuclearItemRegister.itemHazmatHelmet;
			}
		};
	}
}
