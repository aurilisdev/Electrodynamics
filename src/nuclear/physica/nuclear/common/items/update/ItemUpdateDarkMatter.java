package physica.nuclear.common.items.update;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import physica.api.core.item.IItemUpdate;
import physica.api.core.utilities.IBaseUtilities;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;

public class ItemUpdateDarkMatter implements IItemUpdate, IBaseUtilities {

	@Override
	public void onEntityItemUpdate(ItemStack stack, EntityItem entity)
	{
		World world = entity.worldObj;
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(world.getWorldInfo().getWorldName().toLowerCase()))
		{
			return;
		}
		if (rand.nextFloat() * 400 < entity.ticksExisted)
		{
			for (int i = 0; i < rand.nextFloat() * entity.ticksExisted / 80; i++)
			{
				float distance = (float) Math.sin(entity.ticksExisted / 40 - Math.floor(entity.ticksExisted));
				world.spawnParticle("smoke", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5, switchRandom(distance) / 5,
						switchRandom(distance) / 5);
				if (rand.nextFloat() * 400 < entity.ticksExisted)
				{
					if (rand.nextFloat() < 0.016667)
					{
						distance = distance / 3 * (entity.ticksExisted / 400);
						world.spawnParticle("portal", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5, switchRandom(distance) / 5,
								switchRandom(distance) / 5);
					}
				}
			}
		}
		if (entity.ticksExisted > 1500 + rand.nextInt(entity.ticksExisted))
		{
			if (!world.isRemote)
			{
				float radius = rand.nextFloat() * 3 + 1.75f;
				AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
				@SuppressWarnings("unchecked")
				List<Entity> entitiesNearby = world.getEntitiesWithinAABB(Entity.class, bounds);

				if (entitiesNearby.size() > 1)
				{
					entitiesNearby.remove(entity);
					for (Entity selected : entitiesNearby)
					{
						if (rand.nextFloat() < 0.16667)
						{
							selected.setInPortal();
						}
					}
				}

				world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 5.0f, true);
				int x = (int) entity.posX, y = (int) entity.posY, z = (int) entity.posZ;
				NuclearBlockRegister.blockPlasma.spawn(world, world.getBlock(x, y, z), x, y, z, 4);
				entity.setDead();
			}
		}
	}
}
