package physica.nuclear.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.nuclear.NuclearReferences;

public class NuclearTabRegister implements IContent {

	public static CreativeTabs nuclearPhysicsTab;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.CreativeTabRegister)
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
}
