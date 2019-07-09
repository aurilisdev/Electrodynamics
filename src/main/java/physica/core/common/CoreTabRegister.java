package physica.core.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import physica.CoreReferences;
import physica.api.core.IContent;

public class CoreTabRegister implements IContent {

	public static CreativeTabs coreTab;

	@Override
	public void preInit()
	{
		coreTab = new CreativeTabs(CoreReferences.DOMAIN + "Core")
		{

			@Override
			public Item getTabIconItem()
			{
				return CoreItemRegister.itemWrench;
			}
		};
	}
}
