package physica.nuclear.common.items.update;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import physica.api.core.IItemUpdate;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.effect.potion.PotionRadiation;
import physica.nuclear.common.items.armor.ItemHazmatArmor;

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
			world.spawnParticle("reddust", entity.posX + rand.nextDouble() - 0.5, entity.posY + rand.nextDouble() - 0.5, entity.posZ + rand.nextDouble() - 0.5, 0, 1.25f, 0);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean p_77663_5_) {
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(world.getWorldInfo().getWorldName().toLowerCase())) {
			return;
		}
		if (entity instanceof EntityLivingBase) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (!player.capabilities.isCreativeMode) {
					boolean hasArmor = true;
					for (int i = 0; i < player.inventory.armorInventory.length; i++) {
						ItemStack armorStack = player.getCurrentArmor(i);
						if (!(armorStack != null && armorStack.getItem() instanceof ItemHazmatArmor)) {
							hasArmor = false;
						}
					}
					if (!hasArmor) {
						player.addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), (int) (300 * scale)));
					} else {
						for (int i = 0; i < player.inventory.armorInventory.length; i++) {
							if (player.getCurrentArmor(i).attemptDamageItem((int) Math.max(1, scale), world.rand)) {
								player.renderBrokenItemStack(player.getCurrentArmor(i));
								player.addStat(StatList.objectBreakStats[Item.getIdFromItem(player.getCurrentArmor(i).getItem())], 1);
								player.setCurrentItemOrArmor(i + 1, null);
							}
						}
					}
				}
			} else {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), (int) (300 * scale)));
			}
		}
	}
}
