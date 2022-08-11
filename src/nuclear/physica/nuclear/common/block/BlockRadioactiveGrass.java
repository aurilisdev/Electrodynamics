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
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.radiation.RadiationSystem;

public class BlockRadioactiveGrass extends BlockGrass {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon snowVersion;

	public BlockRadioactiveGrass() {
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "radioactiveGrass");
		setHardness(0.6F);
		setStepSound(soundTypeGrass);
		setBlockTextureName("grass");
//		setTickRandomly(true);
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

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		return plantType == EnumPlantType.Plains;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? topIcon : side == 0 ? Blocks.dirt.getBlockTextureFromSide(side) : blockIcon;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			if (world.rand.nextFloat() < 0.666f) {
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
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return Blocks.grass.colorMultiplier(world, x, y, z) - 10;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1) {
			return topIcon;
		} else if (side == 0) {
			return Blocks.dirt.getBlockTextureFromSide(side);
		} else {
			Material material = world.getBlock(x, y + 1, z).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? blockIcon : snowVersion;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
		topIcon = p_149651_1_.registerIcon(getTextureName() + "_top");
		snowVersion = p_149651_1_.registerIcon("grass_side_snowed");
	}

}
