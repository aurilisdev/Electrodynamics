package physica.nuclear.common.items.update;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.api.core.IItemUpdate;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.radiation.RadiationSystem;

public class ItemUpdateUranium implements IItemUpdate {

	protected float scale = 1;

	public ItemUpdateUranium setScale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public void onEntityItemUpdate(ItemStack stack, EntityItem entity) {
		World world = entity.worldObj;
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(world.getWorldInfo().getWorldName().toLowerCase())) {
			return;
		}
		if (rand.nextFloat() < 0.015f * scale) {
			world.spawnParticle("reddust", entity.posX + rand.nextDouble() - 0.5, entity.posY + rand.nextDouble() - 0.5, entity.posZ + rand.nextDouble() - 0.5, 0.01f, 1, 0.01f);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean p_77663_5_) {
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(world.getWorldInfo().getWorldName().toLowerCase())) {
			return;
		}
		if (entity instanceof EntityLivingBase) {
			RadiationSystem.applyRontgenEntity((EntityLivingBase) entity, scale, 15, 1, 1);
		}
	}
}
