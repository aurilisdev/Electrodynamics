package physica.nuclear.common;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import physica.Physica;
import physica.api.core.IContent;
import physica.nuclear.common.entity.EntityParticle;

public class NuclearEntityRegister implements IContent {

	public static final int MOD_ID = 120;

	@Override
	public void init()
	{
		registerEntity(EntityParticle.class, "particle", 80, 3, true);
		ForgeChunkManager.setForcedChunkLoadingCallback(Physica.INSTANCE, (tickets, world) -> {
			for (Ticket ticket : tickets) {
				if (ticket.getType() == Type.ENTITY) {
					final Entity entity = ticket.getEntity();

					if (entity instanceof EntityParticle) {
						((EntityParticle) entity).updateTicket = ticket;
					}
				}
			}
		});
	}

	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(entityClass, entityName, MOD_ID, Physica.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}
