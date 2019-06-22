package physica.nuclear.common.radiation;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import physica.nuclear.common.effect.potion.PotionRadiation;
import physica.nuclear.common.items.armor.ItemHazmatArmor;

public class RadiationSystem {

	public static final double toRealRoentgenConversionRate = 2139.995 / 3;

	public static void applyRontgenEntity(EntityLivingBase base, float kiloRoentgen, float durationMultiplier, float distanceFromSource, float maxRadius) {
		if (base != null) {
			if (base.worldObj.rand.nextFloat() < kiloRoentgen) {
				if (base.worldObj.isRemote) {
					if (base == Minecraft.getMinecraft().thePlayer) {
						long worldTime = Minecraft.getMinecraft().thePlayer.worldObj.getTotalWorldTime();
						int amplifier = (int) ((maxRadius - distanceFromSource) / maxRadius * 5 - 1);
						RoentgenOverlay.storeDataValue(worldTime, kiloRoentgen * Math.max(1, amplifier));
					}
				} else {
					float protection = 1;
					distanceFromSource = Math.max(0.5f, distanceFromSource);
					boolean isPlayer = base instanceof EntityPlayer;
					playerCheck: {
						if (isPlayer) {
							EntityPlayer player = (EntityPlayer) base;
							if (player.capabilities.isCreativeMode) {
								break playerCheck;
							}
							boolean hasArmor = true;
							for (int i = 0; i < player.inventory.armorInventory.length; i++) {
								if (!(player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() instanceof ItemHazmatArmor)) {
									hasArmor = false;
								} else {
									protection++;
								}
							}
							if (!hasArmor && protection == 0) {
								break playerCheck;
							} else {
								for (int i = 0; i < player.inventory.armorInventory.length; i++) {
									if (player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() instanceof ItemHazmatArmor) {
										if (player.getCurrentArmor(i).getItemDamage() > player.getCurrentArmor(i).getMaxDamage()
												|| player.getCurrentArmor(i).attemptDamageItem((int) Math.max(1, kiloRoentgen * 2.15), base.worldObj.rand)) {
											player.setCurrentItemOrArmor(i + 1, null);
										}
									}
								}
							}
						}
					}
					int duration = (int) (durationMultiplier * 20 * kiloRoentgen / protection / distanceFromSource);
					int amplifier = (int) ((maxRadius - distanceFromSource) / maxRadius * 5 - protection);
					if (protection < 5 && !(isPlayer && ((EntityPlayer) base).capabilities.isCreativeMode)) {
						base.addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), duration, Math.max(0, amplifier)));
					}
					if (isPlayer && ((maxRadius - distanceFromSource) / maxRadius * 75) > 0) {
						EntityPlayer player = (EntityPlayer) base;
						ItemStack stack = player.inventory.getCurrentItem();
						if (stack != null && stack.getItem() instanceof IEnergyContainerItem) {
							IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
							int electricity = (int) (item.getMaxEnergyStored(stack) / ((60 * 20) / (maxRadius - distanceFromSource) / maxRadius * 75));
							item.extractEnergy(stack, electricity, false);
						}
					}
				}
			}
		}
	}
}
