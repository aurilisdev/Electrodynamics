package physica.core.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.conductor.EnumConductorType;
import physica.api.core.conductor.IConductor;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.client.render.tile.TileRenderEnergyCable;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreTabRegister;
import physica.core.common.tile.cable.TileEnergyCable;

public class BlockEnergyCable extends Block implements ITileEntityProvider, IBaseUtilities, IRecipeRegister {

	public BlockEnergyCable() {
		super(Material.cloth);
		setHardness(3.5F);
		setStepSound(soundTypeCloth);
		setResistance(0.2F);
		setBlockName(CoreReferences.PREFIX + "energyCable");
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockBounds(TileRenderEnergyCable.pixelElevenTwo, TileRenderEnergyCable.pixelElevenTwo, TileRenderEnergyCable.pixelElevenTwo, 1 - TileRenderEnergyCable.pixelElevenTwo, 1 - TileRenderEnergyCable.pixelElevenTwo,
				1 - TileRenderEnergyCable.pixelElevenTwo);
		addToRegister("Core", this);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 0), "WIW", "WIW", "WIW", 'W', Blocks.wool, 'I', "ingotCopper");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 0), "LIL", "LIL", "LIL", 'L', Items.leather, 'I', "ingotCopper");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 0), "WWW", "III", "WWW", 'W', Blocks.wool, 'I', "ingotCopper");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 0), "LLL", "III", "LLL", 'L', Items.leather, 'I', "ingotCopper");

		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 1), "WIW", "WIW", "WIW", 'W', Blocks.wool, 'I', "ingotSilver");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 1), "LIL", "LIL", "LIL", 'L', Items.leather, 'I', "ingotSilver");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 1), "WWW", "III", "WWW", 'W', Blocks.wool, 'I', "ingotCopper");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 1), "LLL", "III", "LLL", 'L', Items.leather, 'I', "ingotCopper");

		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 2), "WIW", "WIW", "WIW", 'W', Blocks.wool, 'I', "ingotGold");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 2), "LIL", "LIL", "LIL", 'L', Items.leather, 'I', "ingotGold");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 2), "WWW", "III", "WWW", 'W', Blocks.wool, 'I', "ingotCopper");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 2), "LLL", "III", "LLL", 'L', Items.leather, 'I', "ingotCopper");

		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 3), "WIW", "WIW", "WIW", 'W', Blocks.wool, 'I', "ingotSuperConductive");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 3), "LIL", "LIL", "LIL", 'L', Items.leather, 'I', "ingotSuperConductive");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 3), "WWW", "III", "WWW", 'W', Blocks.wool, 'I', "ingotSuperConductive");
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6, 3), "LLL", "III", "LLL", 'L', Items.leather, 'I', "ingotSuperConductive");

	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		float tempMinX = TileRenderEnergyCable.pixelElevenTwo;
		float tempMinY = TileRenderEnergyCable.pixelElevenTwo;
		float tempMinZ = TileRenderEnergyCable.pixelElevenTwo;
		float tempMaxX = 1 - TileRenderEnergyCable.pixelElevenTwo;
		float tempMaxY = 1 - TileRenderEnergyCable.pixelElevenTwo;
		float tempMaxZ = 1 - TileRenderEnergyCable.pixelElevenTwo;
		for (Face dir : Face.VALID)
		{
			TileEntity sideTile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (AbstractionLayer.Electricity.canConnectElectricity(sideTile, dir.getOpposite()))
			{
				switch (dir) {
				case DOWN:
					tempMinY -= TileRenderEnergyCable.pixelElevenTwo;
					break;
				case EAST:
					tempMaxX += TileRenderEnergyCable.pixelElevenTwo;
					break;
				case NORTH:
					tempMinZ -= TileRenderEnergyCable.pixelElevenTwo;
					break;
				case SOUTH:
					tempMaxZ += TileRenderEnergyCable.pixelElevenTwo;
					break;
				case UP:
					tempMaxY += TileRenderEnergyCable.pixelElevenTwo;
					break;
				case WEST:
					tempMinX -= TileRenderEnergyCable.pixelElevenTwo;
					break;
				default:
					break;
				}
			}
		}
		setBlockBounds(tempMinX, tempMinY, tempMinZ, tempMaxX, tempMaxY, tempMaxZ);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		if (!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof IConductor)
			{
				((IConductor) tileEntity).refreshNetwork();
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		if (!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof IConductor)
			{
				((IConductor) tileEntity).refreshNetwork();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] icons = new IIcon[EnumConductorType.values().length];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		for (EnumConductorType type : EnumConductorType.values())
		{
			icons[type.ordinal()] = reg.registerIcon(CoreReferences.PREFIX + "wire/" + type.asset());
		}
	}

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return true;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEnergyCable();
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

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
	{
		for (EnumConductorType type : EnumConductorType.values())
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

}
