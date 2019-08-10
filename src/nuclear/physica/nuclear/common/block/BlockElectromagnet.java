package physica.nuclear.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.Face;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.api.nuclear.IElectromagnet;
import physica.library.util.OreDictionaryUtilities;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearTabRegister;

public class BlockElectromagnet extends Block implements IElectromagnet, IBaseUtilities, IRecipeRegister {

	@SideOnly(Side.CLIENT)
	private static IIcon	iconTop, iconGlass;
	@SideOnly(Side.CLIENT)
	private static IIcon	containment, containmentIconTop, containmentIconGlass;

	public BlockElectromagnet() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(20);
		setHarvestLevel("pickaxe", 2);
		setBlockTextureName(CoreReferences.PREFIX + "electromagnet");
		setBlockName(NuclearReferences.PREFIX + "electromagnet");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		addToRegister("Nuclear", this);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(new ItemStack(this, 2), "BSB", "SMS", "BSB", 'B', OreDictionaryUtilities.getAlternatingOreItem("ingotBronze", "ingotCopper"), 'M', "motor", 'S', "ingotSteel");
		addShapeless(new ItemStack(this, 1, EnumElectromagnet.GLASS.ordinal()), NuclearBlockRegister.blockElectromagnet, Blocks.glass);
		addRecipe(new ItemStack(this, 2, EnumElectromagnet.CONTAINMENT_NORMAL.ordinal()), "ELE", "LEL", "ELE", 'E', "circuitElite", 'L', new ItemStack(this, 1, 0));
		addShapeless(new ItemStack(this, 1, EnumElectromagnet.CONTAINMENT_GLASS.ordinal()), new ItemStack(this, 1, EnumElectromagnet.CONTAINMENT_NORMAL.ordinal()), Blocks.glass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		iconTop = iconRegister.registerIcon(CoreReferences.PREFIX + "electromagnetTop");
		iconGlass = iconRegister.registerIcon(CoreReferences.PREFIX + "electromagnetGlass");
		containment = iconRegister.registerIcon(CoreReferences.PREFIX + "containmentBlock");
		containmentIconTop = iconRegister.registerIcon(CoreReferences.PREFIX + "containmentTop");
		containmentIconGlass = iconRegister.registerIcon(CoreReferences.PREFIX + "containmentGlass");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		if (metadata == EnumElectromagnet.NORMAL.ordinal() || metadata == EnumElectromagnet.CONTAINMENT_NORMAL.ordinal())
		{
			if (side == 0 || side == 1)
			{
				return metadata == EnumElectromagnet.NORMAL.ordinal() ? iconTop : containmentIconTop;
			} else if (metadata == EnumElectromagnet.NORMAL.ordinal())
			{
				return blockIcon;
			}
			return containment;
		} else if (metadata == EnumElectromagnet.GLASS.ordinal())
		{
			return iconGlass;
		}
		return containmentIconGlass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		Face dir = Face.getOrientation(side).getOpposite();
		int xn = x + dir.offsetX, yn = y + dir.offsetY, zn = z + dir.offsetZ;
		Block neighborBlock = world.getBlock(xn, yn, zn);
		int neighborMetadata = world.getBlockMetadata(xn, yn, zn);

		return block == this && neighborBlock == this && (metadata == 1 && neighborMetadata == 1 || metadata == 3 && neighborMetadata == 3) ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
	{
		for (EnumElectromagnet type : EnumElectromagnet.values())
		{
			list.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 3);
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
	{
		return EnumElectromagnet.values()[world.getBlockMetadata(x, y, z)] == EnumElectromagnet.GLASS ? 0 : super.getLightOpacity(world, x, y, z);
	}

	public enum EnumElectromagnet {
		NORMAL, GLASS, CONTAINMENT_NORMAL, CONTAINMENT_GLASS;

		public String getName()
		{
			return name().toLowerCase();
		}
	}

}
