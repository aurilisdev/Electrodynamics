package physica.missiles.common;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.missiles.PhysicaMissiles;
import physica.missiles.common.entity.EntityFragment;
import physica.missiles.common.entity.EntityGrenade;
import physica.missiles.common.entity.EntityPrimedExplosive;

public class MissileEntityRegister implements IContent {

	private static int MOD_ID = 120;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.EntityRegister)
		{
			registerEntity(EntityPrimedExplosive.class, "physicaprimedexplosive", 40, 5, true);
			registerEntity(EntityFragment.class, "physicafragments", 40, 5, true);
			registerEntity(EntityGrenade.class, "physicagrenade", 40, 5, true);
		}
	}

	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(entityClass, entityName, MOD_ID++, PhysicaMissiles.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}
