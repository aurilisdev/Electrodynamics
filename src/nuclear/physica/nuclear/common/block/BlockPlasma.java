package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TilePlasma;

public class BlockPlasma extends Block implements ITileEntityProvider {

	public BlockPlasma() {
		super(Material.lava);
		setBlockTextureName("portal");
		setBlockName(NuclearReferences.PREFIX + "plasma");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setLightLevel(0.5f);
	}

	@Override
	public boolean isCollidable()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
		int xn = x + dir.offsetX, yn = y + dir.offsetY, zn = z + dir.offsetZ;
		Block neighborBlock = world.getBlock(xn, yn, zn);
		return block == this && neighborBlock == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	public void spawn(World world, Block block, int x, int y, int z, int strength)
	{
		if (TilePlasma.canPlace(block, world, x, y, z))
		{
			world.setBlock(x, y, z, this, 0, 3);
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TilePlasma)
			{
				((TilePlasma) tile).strength = strength;
			}
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public int getRenderType()
	{
		return 0;
	}

	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TilePlasma();
	}

}
