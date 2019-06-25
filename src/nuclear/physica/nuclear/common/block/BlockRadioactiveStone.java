package physica.nuclear.common.block;

import java.util.Random;

import net.minecraft.block.BlockStone;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.radiation.RadiationSystem;

public class BlockRadioactiveStone extends BlockStone {

	public BlockRadioactiveStone() {
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundTypePiston);
		setBlockName(NuclearReferences.PREFIX + "radioactiveStone");
		setBlockTextureName("stone");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote)
		{
			if (world.rand.nextFloat() < 0.167f)
			{
				RadiationSystem.spreadRadioactiveBlock(world, x, y, z);
			}
		}
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity ent)
	{
		if (ent instanceof EntityLivingBase)
		{
			int meta = world.getBlockMetadata(x, y, z);
			RadiationSystem.applyRontgenEntity((EntityLivingBase) ent, meta / 7.5f, meta, 1, 1);
		}
	}

}
