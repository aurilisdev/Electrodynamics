package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.radiation.RadiationSystem;

public class BlockRadioactiveGrass extends BlockGrass {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon snowVersion;

	public BlockRadioactiveGrass() {
		super();
		this.setTickRandomly(true);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "radioactiveGrass");
		setHardness(0.6F);
		setStepSound(soundTypeGrass);
		setBlockTextureName("grass");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? this.topIcon : side == 0 ? Blocks.dirt.getBlockTextureFromSide(side) : this.blockIcon;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			if (world.rand.nextFloat() < 0.666f) {
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

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return Blocks.grass.colorMultiplier(world, x, y, z) - 3009;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1) {
			return this.topIcon;
		} else if (side == 0) {
			return Blocks.dirt.getBlockTextureFromSide(side);
		} else {
			Material material = world.getBlock(x, y + 1, z).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? this.blockIcon : this.snowVersion;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		this.snowVersion = p_149651_1_.registerIcon("grass_side_snowed");
	}

	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
		super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);

		if (p_149734_5_.nextInt(10) == 0) {
			p_149734_1_.spawnParticle("reddust", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 1.1F,
					p_149734_4_ + p_149734_5_.nextFloat(), 0.01f, 1.0f, 0.01f);
		}
	}
}
