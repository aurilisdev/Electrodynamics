package physica.content.common.items.update;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.api.lib.item.IItemUpdate;
import physica.content.common.effect.damage.DamageSourceRadiation;

public class ItemUpdateUranium implements IItemUpdate {
	protected float scale = 1;

	public ItemUpdateUranium setScale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public void onEntityItemUpdate(ItemStack stack, EntityItem entity) {
		World world = entity.worldObj;
		if (rand.nextFloat() < 0.015f * scale)
		{
			world.spawnParticle("reddust", entity.posX + rand.nextDouble() - 0.5, entity.posY + rand.nextDouble() - 0.5, entity.posZ + rand.nextDouble() - 0.5, 0, 1.25f, 0);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean p_77663_5_) {
		if (rand.nextFloat() < 0.0025)
		{
			world.spawnParticle("reddust", entity.posX + rand.nextDouble() - 0.5, entity.posY + rand.nextDouble() - 0.5, entity.posZ + rand.nextDouble() - 0.5, 0, 1.25f, 0);
			if (!world.isRemote) entity.attackEntityFrom(DamageSourceRadiation.INSTANCE, 2f + scale * 1.5f * rand.nextFloat());
		}
	}
}
