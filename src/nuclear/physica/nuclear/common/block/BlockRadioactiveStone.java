package physica.nuclear.common.block;

import java.util.Random;

import net.minecraft.block.BlockStone;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
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
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			if (world.rand.nextFloat() < 0.333f) {
				if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2) {
					world.setBlock(x, y, z, Blocks.dirt);
				} else if (world.getBlockLightValue(x, y + 1, z) >= 9) {
					for (int l = 0; l < 4; ++l) {
						int i1 = x + rand.nextInt(3) - 1;
						int j1 = y + rand.nextInt(5) - 3;
						int k1 = z + rand.nextInt(3) - 1;
						if (world.getBlock(i1, j1, k1) == Blocks.stone && world.getBlockMetadata(i1, j1, k1) == 0 && world.getBlockLightValue(i1, j1 + 1, k1) >= 4
								&& world.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
							world.setBlock(i1, j1, k1, this);
						}
					}
				}
			}
		}
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase) {
			RadiationSystem.applyRontgenEntity((EntityLivingBase) ent, 1f, 15, 1, 1);
		}
	}

}
