package physica.nuclear.common.items.update;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.api.core.IBaseUtilities;
import physica.api.core.IItemUpdate;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;

public class ItemUpdateAntimatter implements IItemUpdate, IBaseUtilities {

	public static int FULMINATION_ANTIMATTER_ENERGY_SCALE = 1000;
	protected float scale = 1;

	public ItemUpdateAntimatter setScale(float scale)
	{
		this.scale = scale;
		return this;
	}

	@Override
	public void onEntityItemUpdate(ItemStack stack, EntityItem entity)
	{
		World world = entity.worldObj;
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(world.getWorldInfo().getWorldName().toLowerCase())) {
			return;
		}
		if (rand.nextFloat() * 160 < entity.ticksExisted) {
			for (int i = 0; i < rand.nextFloat() * entity.age / 100; i++) {
				float distance = (float) Math.sin(entity.ticksExisted / 40F - Math.floor(entity.ticksExisted)) / 3;
				world.spawnParticle("smoke", entity.posX + switchRandom(distance), entity.posY + switchRandom(distance), entity.posZ + switchRandom(distance), switchRandom(distance) / 5,
						switchRandom(distance) / 5, switchRandom(distance) / 5);
			}
		}
		if (entity.ticksExisted > 160 + rand.nextInt(entity.ticksExisted)) {
			if (!world.isRemote) {
				entity.ticksExisted = 1;
				world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, Math.max(0.5f, (entity.ticksExisted - 160) / 15F) * scale * 5, true);
				entity.setDead();
			}
		}
	}
}
