package physica.content.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import physica.Physica;
import physica.References;

public class BlockUraniumOre extends Block {
	public BlockUraniumOre() {
		super(Material.rock);
		setHardness(3f);
		setResistance(5f);
		setLightLevel(0.1f);
		setStepSound(soundTypePiston);
		setHarvestLevel("pickaxe", 2);
		setBlockTextureName(References.PREFIX + "uraniumOre");
		setBlockName(References.PREFIX + "uraniumOre");
		setCreativeTab(Physica.creativeTab);
		setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (random.nextFloat() < 0.333) for (int i = 0; i < 2; i++)
		{
			if (random.nextFloat() < 0.666) world.spawnParticle("reddust", x + random.nextDouble() * 3 - 1.5, y + random.nextDouble() * 3 - 1.5, z + random.nextDouble() * 3 - 1.5, 0, 1, 0);
		}
	}
}
