package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.util.ChatUtilities;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileThermometer;

public class BlockThermometer extends Block implements IBaseUtilities, IRecipeRegister, ITileEntityProvider {

	@SideOnly(Side.CLIENT)
	private static IIcon iconTop, iconGlass;

	public BlockThermometer() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(20);
		setHarvestLevel("pickaxe", 2);
		setBlockTextureName(NuclearReferences.PREFIX + "thermometer");
		setBlockName(NuclearReferences.PREFIX + "thermometer");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setTickRandomly(true);
		addToRegister("Nuclear", this);
	}

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "SSS", "SWS", "SSS", 'S', "ingotSteel", 'W', "circuitAdvanced");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		TileEntity tile = world.getTileEntity(x, y + 1, z);
		if (player.isSneaking())
		{
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlockMetadataWithNotify(x, y, z, meta = meta == 0 ? 1 : meta == 1 ? 2 : meta == 2 ? 3 : meta == 3 ? 4 : 0, 2);
			String temp = meta == 0 ? "4500" : meta == 1 ? "4000" : meta == 2 ? "3500" : meta == 3 ? "3000" : "2500";
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 100, "Signal at: " + temp + ".0C");
		} else if (tile instanceof TileFissionReactor)
		{
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 99, "Heat: " + IBaseUtilities.roundPreciseStatic((double) ((TileFissionReactor) tile).getTemperature(), 2) + "C");
			int meta = world.getBlockMetadata(x, y, z);
			String temp = meta == 0 ? "4500" : meta == 1 ? "4000" : meta == 2 ? "3500" : meta == 3 ? "3000" : "2500";
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 100, "Signal at: " + temp + ".0C");
		} else
		{
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 99, "Heat: 15.0C");
			int meta = world.getBlockMetadata(x, y, z);
			String temp = meta == 0 ? "4500" : meta == 1 ? "4000" : meta == 2 ? "3500" : meta == 3 ? "3000" : "2500";
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 100, "Signal at: " + temp + ".0C");
		}
		return true;
	}

	@Override
	public int tickRate(World world)
	{
		return 1;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		world.notifyBlocksOfNeighborChange(x, y, z, this);
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		TileEntity tile = world.getTileEntity(x, y + 1, z);
		if (tile instanceof TileFissionReactor)
		{
			world.notifyBlocksOfNeighborChange(x, y, z, this);
		}
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
	{
		return isProvidingStrongPower(world, x, y, z, side);
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity tile = world.getTileEntity(x, y + 1, z);
		if (tile instanceof TileFissionReactor)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (((TileFissionReactor) tile).getTemperature() > (meta == 0 ? 4500 : meta == 1 ? 4000 : meta == 2 ? 3500 : meta == 3 ? 3000 : 2500) - 25)
			{
				return 15;
			}
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		iconTop = iconRegister.registerIcon(NuclearReferences.PREFIX + "thermometerTop");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side == 0 || side == 1 ? iconTop : super.getIcon(side, metadata);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileThermometer();
	}
}
