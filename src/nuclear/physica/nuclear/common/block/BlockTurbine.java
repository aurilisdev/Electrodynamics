package physica.nuclear.common.block;

import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.IMekWrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileTurbine;

public class BlockTurbine extends BlockContainer implements IBaseUtilities, IRecipeRegister {

	public BlockTurbine() {
		super(Material.iron);
		setHardness(10);
		setResistance(5);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setHarvestLevel("pickaxe", 2);
		setBlockName(NuclearReferences.PREFIX + "turbine");
		addToRegister(RecipeSide.Nuclear, this);
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "PAP", "BMB", "PBP", 'P', "plateSteel", 'A', "circuitAdvanced", 'B', Blocks.iron_bars, 'M', "motor");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		if (!world.isRemote)
		{
			ItemStack stack = player.getHeldItem();
			if (stack != null)
			{
				Item item = stack.getItem();
				if (item instanceof IToolWrench || item instanceof IToolHammer || item instanceof IMekWrench)
				{
					if (world.getTileEntity(x, y, z) instanceof TileTurbine)
					{
						TileTurbine turbine = (TileTurbine) world.getTileEntity(x, y, z);
						if (turbine.isMain())
						{
							turbine.tryDeconstruct();
						} else
						{
							turbine.attemptConstruct();
						}
					}
					return true;
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6)
	{
		if (world.getTileEntity(x, y, z) instanceof TileTurbine)
		{
			TileTurbine turbine = (TileTurbine) world.getTileEntity(x, y, z);
			turbine.tryDeconstruct();
		}
		super.breakBlock(world, x, y, z, block, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileTurbine();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = reg.registerIcon(CoreReferences.PREFIX + "siren");
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isNormalCube()
	{
		return false;
	}
}
