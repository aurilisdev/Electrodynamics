package physica.core.common.block;

import java.awt.Color;
import java.util.List;

import cofh.api.energy.IEnergyConnection;
import cpw.mods.fml.common.FMLCommonHandler;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.core.client.render.tile.TileRenderEnergyCable;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreTabRegister;
import physica.core.common.network.ITransferNode;
import physica.core.common.tile.cable.TileEnergyCable;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;

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
		addToRegister(RecipeSide.Core, this);
	}

	@Override
	public int colorMultiplier(IBlockAccess access, int x, int y, int z)
	{
		return Color.GRAY.darker().darker().darker().getRGB();
	}

	@Override
	public void initialize()
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
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity sideTile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (sideTile instanceof IEnergyConnection && ((IEnergyConnection) sideTile).canConnectEnergy(dir.getOpposite()))
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = Blocks.wool.getIcon(0, 0);
	}

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return true;
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

	@SuppressWarnings("rawtypes")
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof ITransferNode)
			{
				((ITransferNode) tile).getTransferNetwork().validateNetwork();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
	{
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
		if (FMLCommonHandler.instance().getSide().isServer())
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof ITransferNode)
			{
				((ITransferNode) tile).getTransferNetwork().validateNetwork();
			}
		}
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
		for (EnumEnergyCable type : EnumEnergyCable.values())
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

	public enum EnumEnergyCable {
		copper(120, 15360), silver(240, 61440), gold(360, 138240), superConductor(-1, 307200);

		public String getName()
		{
			return name();
		}

		private int	voltage;
		private int	transferRate;

		private EnumEnergyCable(int voltage, int transferRate) {
			this.voltage = voltage;
			this.transferRate = transferRate;
		}

		public int getVoltage()
		{
			return voltage;
		}

		public int getTransferRate()
		{
			return transferRate;
		}
	}

}
