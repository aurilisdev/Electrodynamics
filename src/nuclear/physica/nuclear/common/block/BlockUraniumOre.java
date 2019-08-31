package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.radiation.RadiationSystem;

public class BlockUraniumOre extends Block {

	public BlockUraniumOre() {
		super(Material.rock);
		setHardness(3f);
		setResistance(15f);
		setLightLevel(0.1f);
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", ConfigNuclearPhysics.URANIUM_ORE_HARVEST_LEVEL);
		setBlockTextureName(NuclearReferences.PREFIX + "uraniumore");
		setBlockName(NuclearReferences.PREFIX + "uraniumOre");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setTickRandomly(true);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity ent)
	{
		if (ent instanceof EntityLivingBase)
		{
			RadiationSystem.applyRontgenEntity((EntityLivingBase) ent, 0.75f, 15, 1, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (random.nextFloat() < 0.333)
		{
			for (int i = 0; i < 2; i++)
			{
				if (random.nextFloat() < 0.666)
				{
					world.spawnParticle("reddust", x + random.nextDouble() * 3 - 1.5, y + random.nextDouble() * 3 - 1.5, z + random.nextDouble() * 3 - 1.5, 0.01f, 1, 0.01f);
				}
			}
		}
	}
}
