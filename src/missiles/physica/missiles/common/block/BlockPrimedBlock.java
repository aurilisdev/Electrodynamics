package physica.missiles.common.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import physica.api.core.abstraction.Face;
import physica.library.location.GridLocation;
import physica.missiles.MissileReferences;
import physica.missiles.common.MissileTabRegister;
import physica.missiles.common.entity.EntityPrimedExplosive;
import physica.missiles.common.explosive.Explosive;

public class BlockPrimedBlock extends Block {

	@SideOnly(Side.CLIENT)
	public ArrayList<IIcon>	topIcons	= new ArrayList<>();
	@SideOnly(Side.CLIENT)
	public ArrayList<IIcon>	sideIcons	= new ArrayList<>();
	@SideOnly(Side.CLIENT)
	public ArrayList<IIcon>	bottomIcons	= new ArrayList<>();

	public BlockPrimedBlock() {
		super(Material.tnt);
		setBlockName(MissileReferences.PREFIX + "blockExplosive");
		setCreativeTab(MissileTabRegister.missilesTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 0 ? bottomIcons.get(meta) : side == 1 ? topIcons.get(meta) : sideIcons.get(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		for (Explosive ex : Explosive.explosiveSet)
		{
			if (ex.hasBlock)
			{
				topIcons.add(ex.getId(), register.registerIcon(MissileReferences.PREFIX + "explosives/" + ex.getTopIcon()));
				sideIcons.add(ex.getId(), register.registerIcon(MissileReferences.PREFIX + "explosives/" + ex.getSideIcon()));
				bottomIcons.add(ex.getId(), register.registerIcon(MissileReferences.PREFIX + "explosives/" + ex.getBottomIcon()));
			} else
			{
				topIcons.add(ex.getId(), register.registerIcon("tnt_top"));
				sideIcons.add(ex.getId(), register.registerIcon("tnt_side"));
				bottomIcons.add(ex.getId(), register.registerIcon("tnt_bottom"));
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 3);
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
		{
			detonate(world, x, y, z, itemStack.getItemDamage(), 0);
		} else
		{
			for (Face face : Face.values())
			{
				GridLocation position = new GridLocation(x, y, z).OffsetFace(face);

				Block block = position.getBlock(world);

				if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava)
				{
					detonate(world, x, y, z, itemStack.getItemDamage(), 2);
					break;
				}
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int explosiveID = world.getBlockMetadata(x, y, z);

		if (world.isBlockIndirectlyGettingPowered(x, y, z))
		{
			detonate(world, x, y, z, explosiveID, 0);
		} else if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava)
		{
			detonate(world, x, y, z, explosiveID, 2);
		}
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		detonate(world, x, y, z, world.getBlockMetadata(x, y, z), 1);
		super.onBlockExploded(world, x, y, z, explosion);
	}

	/**
	 * Called upon block activation (left or right click on the block.). The three
	 * integers represent x,y,z of the block.
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
	{

		if (entityPlayer.getCurrentEquippedItem() != null)
		{
			if (entityPlayer.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
			{
				detonate(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				return true;
			}
		}

		return false;
	}

	public static void detonate(World world, int x, int y, int z, int explosiveID, int causeOfExplosion)
	{
		if (!world.isRemote)
		{
			EntityPrimedExplosive explosive = new EntityPrimedExplosive(world, x + 0.5, y + 0.5, z + 0.5, explosiveID);
			if (causeOfExplosion == 2)
			{
				explosive.setFire(100);
			}
			if (causeOfExplosion == 1)
			{
				explosive.fuse = 10;
			}
			world.spawnEntityInWorld(explosive);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, @SuppressWarnings("rawtypes") List holder)
	{
		for (Explosive ex : Explosive.explosiveSet)
		{
			if (ex.hasBlock)
			{
				holder.add(new ItemStack(item, 1, ex.getId()));
			}
		}

	}
}
