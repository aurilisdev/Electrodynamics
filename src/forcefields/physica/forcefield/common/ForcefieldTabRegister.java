package physica.forcefield.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import physica.api.core.IContent;
import physica.forcefield.ForcefieldReferences;

public class ForcefieldTabRegister implements IContent {

	public static CreativeTabs forcefieldTab;

	@Override
	public void preInit()
	{
		forcefieldTab = new CreativeTabs(ForcefieldReferences.DOMAIN + "Forcefield")
		{

			@Override
			public Item getTabIconItem()
			{
				return Item.getItemFromBlock(ForcefieldBlockRegister.blockFortronConstructor);
			}
		};
	}
}
