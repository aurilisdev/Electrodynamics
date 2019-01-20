package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.effect.potion.PotionRadiation;
import physica.nuclear.common.items.armor.ItemHazmatArmor;

public class BlockUraniumOre extends Block {

	public BlockUraniumOre() {
		super(Material.rock);
		setHardness(3f);
		setResistance(5f);
		setLightLevel(0.1f);
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", ConfigNuclearPhysics.URANIUM_ORE_HARVEST_LEVEL);
		setBlockTextureName(CoreReferences.PREFIX + "uraniumOre");
		setBlockName(NuclearReferences.PREFIX + "uraniumOre");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setTickRandomly(true);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
		super.onEntityWalking(world, x, y, z, ent);
		if (world.rand.nextFloat() < 0.333) {
			if (ent instanceof EntityLivingBase) {
				if (ent instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) ent;
					boolean hasArmor = true;
					for (int i = 0; i < player.inventory.armorInventory.length; i++) {
						if (!(player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() instanceof ItemHazmatArmor)) {
							hasArmor = false;
						}
					}
					if (!hasArmor) {
						player.addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), 300));
					} else {
						for (int i = 0; i < player.inventory.armorInventory.length; i++) {
							player.getCurrentArmor(i).damageItem(30, player);
						}
					}
				} else {
					((EntityLivingBase) ent).addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), 300));
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (random.nextFloat() < 0.333) {
			for (int i = 0; i < 2; i++) {
				if (random.nextFloat() < 0.666) {
					world.spawnParticle("reddust", x + random.nextDouble() * 3 - 1.5, y + random.nextDouble() * 3 - 1.5, z + random.nextDouble() * 3 - 1.5, 0, 1, 0);
				}
			}
		}
	}
}
