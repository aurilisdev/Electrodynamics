package physica.content.common.items.update;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.api.lib.IBaseUtilities;
import physica.api.lib.item.IItemUpdate;

public class ItemUpdateAntimatter implements IItemUpdate, IBaseUtilities {
	protected float scale = 1;

	public ItemUpdateAntimatter setScale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public void onEntityItemUpdate(ItemStack stack, EntityItem entity) {
		World world = entity.worldObj;
		if (entity.ticksExisted > 40 && entity.ticksExisted <= 120)
		{
			world.spawnParticle("smoke", entity.posX, entity.posY, entity.posZ, 0, 0.025, 0);
		}
		if (entity.ticksExisted > 120)
		{
			for (int i = 0; i < 5; i++)
			{
				float distance = (float) Math.sin(entity.ticksExisted / 40 - Math.floor(entity.ticksExisted)) / 3;
				world.spawnParticle("smoke", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
			}
		}
		if (entity.ticksExisted > 160 + rand.nextInt(entity.ticksExisted))
		{
			if (!world.isRemote)
			{
				world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, Math.max(0.5f, (entity.ticksExisted - 160) / 15) * scale, true);
				entity.setDead();
			}
		}
	}
}
