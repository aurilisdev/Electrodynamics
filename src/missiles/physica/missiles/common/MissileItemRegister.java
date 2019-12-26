package physica.missiles.common;

import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.missiles.common.items.ItemGrenade;

public class MissileItemRegister implements IContent {
	public static ItemGrenade itemGrenade;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			GameRegistry.registerItem(itemGrenade = new ItemGrenade(), itemGrenade.getUnlocalizedName());
		}
	}
}
