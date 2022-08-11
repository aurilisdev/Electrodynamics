package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
//		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			if (world.rand.nextFloat() < 0.167f) {
				RadiationSystem.spreadRadioactiveBlock(world, x, y, z);
			}
		}
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase) {
			int meta = world.getBlockMetadata(x, y, z);
			RadiationSystem.applyRontgenEntity((EntityLivingBase) ent, meta / 2.5f, meta, 1, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (random.nextFloat() < 0.1667) {
			for (int i = 0; i < 2; i++) {
				if (random.nextFloat() < 0.666) {
					world.spawnParticle("reddust", x + random.nextDouble() * 3 - 1.5, y + random.nextDouble() * 3 - 1.5, z + random.nextDouble() * 3 - 1.5, 0.01f, 1, 0.01f);
				}
			}
		}
	}

}
