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
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
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
		setBlockTextureName(CoreReferences.PREFIX + "batteryBox");
		addToRegister("Core", this);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = reg.registerIcon(CoreReferences.PREFIX + "machineSide");
		machineOutput = reg.registerIcon(CoreReferences.PREFIX + "machineOutput");
		machineInput = reg.registerIcon(CoreReferences.PREFIX + "machineInput");
		iconFacing = reg.registerIcon(getTextureName());
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		return blockIcon;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 4)
		{
			return iconFacing;
		} else
		{
			return blockIcon;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileBatteryBox();
	}

	@Override
	public void registerRecipes()
	{
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
		world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 3);
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	public enum EnumBatteryBox {
		BASIC, ADVANCED;

		public String getName()
		{
			return name().toLowerCase();
		}
	}

}
