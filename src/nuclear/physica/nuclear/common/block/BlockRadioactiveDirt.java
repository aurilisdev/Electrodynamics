package physica.nuclear.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.radiation.RadiationSystem;

public class BlockRadioactiveDirt extends Block {

	public BlockRadioactiveDirt() {
		super(Material.ground);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setBlockTextureName("dirt");
		setBlockName(NuclearReferences.PREFIX + "radioactiveDirt");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			if (world.rand.nextFloat() < 0.45f) {
				int currentMeta = world.getBlockMetadata(x, y, z);
				for (int l = 0; l < 4; ++l) {
					int i1 = x + rand.nextInt(3) - 1;
					int j1 = y + rand.nextInt(5) - 3;
					int k1 = z + rand.nextInt(3) - 1;
					if (world.getBlock(i1, j1, k1) == Blocks.dirt && world.getBlockMetadata(i1, j1, k1) == 0 && currentMeta > 1) {
						world.setBlock(i1, j1, k1, this, currentMeta - 1, 3);
						world.setBlockMetadataWithNotify(x, y, z, currentMeta - 1, 3);
					}
				}
			}
		}
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase) {
			RadiationSystem.applyRontgenEntity((EntityLivingBase) ent, 1f, world.getBlockMetadata(x, y, z), 1, 1);
		}
	}

}
