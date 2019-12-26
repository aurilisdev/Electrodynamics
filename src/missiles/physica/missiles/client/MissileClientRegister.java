package physica.missiles.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.missiles.client.render.entity.RenderEntityPrimedExplosive;
import physica.missiles.client.render.entity.RenderGrenade;
import physica.missiles.client.render.entity.RenderShrapnelFragment;
import physica.missiles.common.entity.EntityFragment;
import physica.missiles.common.entity.EntityGrenade;
import physica.missiles.common.entity.EntityPrimedExplosive;

@SideOnly(Side.CLIENT)
public class MissileClientRegister implements IContent {

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.ClientRegister)
		{
		} else if (phase == LoadPhase.PostInitialize)
		{
			RenderingRegistry.registerEntityRenderingHandler(EntityPrimedExplosive.class, new RenderEntityPrimedExplosive());
			RenderingRegistry.registerEntityRenderingHandler(EntityFragment.class, new RenderShrapnelFragment());
			RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
		}
	}
}
