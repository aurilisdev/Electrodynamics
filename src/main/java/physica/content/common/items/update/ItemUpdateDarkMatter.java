package physica.content.common.items.update;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import physica.api.lib.IBaseUtilities;
import physica.api.lib.item.IItemUpdate;

public class ItemUpdateDarkMatter implements IItemUpdate, IBaseUtilities {

	@Override
	public void onEntityItemUpdate(ItemStack stack, EntityItem entity) {
		World world = entity.worldObj;
		if (entity.ticksExisted > 400)
		{
			if (rand.nextFloat() < 0.4)
			{
				float distance = (float) Math.sin(entity.ticksExisted / 40 - Math.floor(entity.ticksExisted));
				world.spawnParticle("smoke", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
				world.spawnParticle("smoke", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
				world.spawnParticle("smoke", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
				world.spawnParticle("portal", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
				world.spawnParticle("portal", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
			}
		}
		if (entity.ticksExisted > 1200 + rand.nextInt(entity.ticksExisted))
		{
			if (!world.isRemote)
			{
				float radius = rand.nextFloat() * 3 + 1.75f;
				AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
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
				world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 2.0f, true);
				entity.setDead();
			}
		}
	}
}
