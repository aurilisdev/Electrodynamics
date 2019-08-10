package physica.core.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.Face;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.conductor.EnumConductorType;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreTabRegister;
import physica.core.common.tile.TileBatteryBox;
import physica.library.block.BlockBaseContainer;

public class BlockBatteryBox extends BlockBaseContainer implements IBaseUtilities, IRecipeRegister {
	@SideOnly(Side.CLIENT)
	private IIcon	machineSide;
	@SideOnly(Side.CLIENT)
	private IIcon	iconFacing;
	@SideOnly(Side.CLIENT)
	private IIcon	machineOutput;
	@SideOnly(Side.CLIENT)
	private IIcon	machineInput;

	public BlockBatteryBox() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockName(CoreReferences.PREFIX + "batteryBox");
		setBlockTextureName(CoreReferences.PREFIX_TEXTURE_MACHINE + "batteryBox");
		addToRegister("Core", this);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineSide");
		machineOutput = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineOutput");
		machineInput = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineInput");
		iconFacing = reg.registerIcon(getTextureName());
	}

	@Override
	public int getRenderColor(int par1)
	{
		return par1 == 1 ? (255 & 0xFF) << 24 | (135 & 0xFF) << 16 | (151 & 0xFF) << 8 | (165 & 0xFF) << 0 : par1 == 2 ? (255 & 0xFF) << 24 | (81 & 0xFF) << 16 | (91 & 0xFF) << 8 | (99 & 0xFF) << 0 : super.getRenderColor(par1);
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		return getRenderColor(world.getBlockMetadata(x, y, z));
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{

		TileEntity tile = access.getTileEntity(x, y, z);
		if (tile instanceof TileBatteryBox)
		{
			TileBatteryBox generator = (TileBatteryBox) tile;
			Face facing = generator.getFacing();
			if (side == facing.getOpposite().getRelativeSide(Face.EAST).ordinal())
			{
				return machineOutput;
			} else if (side == facing.getOpposite().getRelativeSide(Face.WEST).ordinal())
			{
				return machineInput;
			}
		}
		return side <= 1 ? blockIcon : iconFacing;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return side <= 1 ? blockIcon : side == 4 ? machineInput : side == 5 ? machineOutput : iconFacing;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileBatteryBox();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(new ItemStack(this, 1, 0), "SSS", "BBB", "SSS", 'S', "ingotSteel", 'B', "phyBattery");
		addRecipe(new ItemStack(this, 1, 1), "BTB", "WWW", "BAB", 'T', new ItemStack(this, 1, 0), 'A', "circuitAdvanced", 'W', new ItemStack(CoreBlockRegister.blockCable, 1, 0), 'B', "phyBattery");
		addRecipe(new ItemStack(this, 1, 2), "BEB", "TWT", "BEB", 'T', new ItemStack(this, 1, 1), 'E', "circuitElite", 'W', new ItemStack(CoreBlockRegister.blockCable, 1, EnumConductorType.superConductor.ordinal()), 'B', "phyBattery");
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
	{
		for (EnumBatteryBox type : EnumBatteryBox.values())
		{
			list.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
		world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 3);
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	public enum EnumBatteryBox {
		BASIC, ADVANCED, ELITE;

		public String getName()
		{
			return name().toLowerCase();
		}
	}

}
