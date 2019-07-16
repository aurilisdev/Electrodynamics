package physica.nuclear.common.radiation;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.AbstractionLayer;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.effect.potion.PotionRadiation;
import physica.nuclear.common.items.armor.ItemHazmatArmor;

public class RadiationSystem {

	public static final double toRealRoentgenConversionRate = 2139.995 / 3;

	public static void applyRontgenEntity(EntityLivingBase base, float kiloRoentgen, float durationMultiplier, float distanceFromSource, float maxRadius)
	{
		if (base != null)
		{
			if (base.worldObj.rand.nextFloat() < kiloRoentgen)
			{
				if (base.worldObj.isRemote)
				{
					if (base == Minecraft.getMinecraft().thePlayer)
					{
						long worldTime = Minecraft.getMinecraft().thePlayer.worldObj.getTotalWorldTime();
						int amplifier = (int) ((maxRadius - distanceFromSource) / maxRadius * 5 - 1);
						RoentgenOverlay.storeDataValue(worldTime, kiloRoentgen * Math.max(1, amplifier));
					}
				} else
				{
					float protection = 1;
					distanceFromSource = Math.max(0.5f, distanceFromSource);
					boolean isPlayer = base instanceof EntityPlayer;
					playerCheck:
					{
						if (isPlayer)
						{
							EntityPlayer player = (EntityPlayer) base;
							if (player.capabilities.isCreativeMode)
							{
								break playerCheck;
							}
							boolean hasArmor = true;
							for (int i = 0; i < player.inventory.armorInventory.length; i++)
							{
								ItemStack armor = player.getCurrentArmor(i);
								if (!(armor != null && armor.getItem() instanceof ItemHazmatArmor))
								{
									hasArmor = false;
								} else
								{
									protection++;
									float damage = kiloRoentgen * 2.15f / ((ItemHazmatArmor) armor.getItem()).getPlatingProtection();
									if (Math.random() < damage)
									{
										int integerDamage = (int) (Math.max(1, kiloRoentgen * 2.15) / ((ItemHazmatArmor) armor.getItem()).getPlatingProtection());
										if (player.getCurrentArmor(i).getItemDamage() > player.getCurrentArmor(i).getMaxDamage() || player.getCurrentArmor(i).attemptDamageItem(integerDamage, base.worldObj.rand))
										{
											player.setCurrentItemOrArmor(i + 1, null);
										}
									}
								}
							}
							if (!hasArmor && protection == 0)
							{
								break playerCheck;
							}
						}
					}
					int duration = (int) (durationMultiplier * 20 * kiloRoentgen / protection / distanceFromSource);
					int amplifier = (int) ((maxRadius - distanceFromSource) / maxRadius * 5 - protection);
					if (protection < 5 && !(isPlayer && ((EntityPlayer) base).capabilities.isCreativeMode))
					{
						base.addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), duration, Math.max(0, amplifier)));
					}
					if (isPlayer && (maxRadius - distanceFromSource) / maxRadius * 75 > 0)
					{
						EntityPlayer player = (EntityPlayer) base;
						ItemStack stack = player.inventory.getCurrentItem();
						if (AbstractionLayer.Electricity.isItemElectric(stack))
						{
							int electricity = (int) (AbstractionLayer.Electricity.getElectricCapacity(stack) / (60 * 20 / (maxRadius - distanceFromSource) / maxRadius * 75));
							AbstractionLayer.Electricity.extractElectricity(stack, electricity, false);
						}
					}
				}
			}
		}
	}

	public static void spreadRadioactiveBlock(World world, int x, int y, int z)
	{
		int currentMeta = world.getBlockMetadata(x, y, z);
		if (currentMeta > 1)
		{
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				int i1 = x + dir.offsetX;
				int j1 = y + dir.offsetY;
				int k1 = z + dir.offsetZ;
				if (currentMeta > 1)
				{
					if (world.getBlock(i1, j1, k1) == Blocks.dirt && world.getBlockMetadata(i1, j1, k1) == 0)
					{
						world.setBlock(i1, j1, k1, NuclearBlockRegister.blockRadioactiveDirt, currentMeta - 1, 3);
						world.setBlockMetadataWithNotify(x, y, z, currentMeta - 1, 3);
					} else if (world.getBlock(i1, j1, k1) == Blocks.grass && world.getBlockMetadata(i1, j1, k1) == 0)
					{
						world.setBlock(i1, j1, k1, NuclearBlockRegister.blockRadioactiveGrass, currentMeta - 1, 3);
						world.setBlockMetadataWithNotify(x, y, z, currentMeta - 1, 3);
					} else if (world.getBlock(i1, j1, k1) == Blocks.stone && world.getBlockMetadata(i1, j1, k1) == 0)
					{
						world.setBlockMetadataWithNotify(x, y, z, currentMeta - 1, 3);
						world.setBlock(i1, j1, k1, NuclearBlockRegister.blockRadioactiveStone, currentMeta - 1, 3);
					}
				}
			}
		}
	}
}
