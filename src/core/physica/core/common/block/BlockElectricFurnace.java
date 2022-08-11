package physica.core.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.core.common.tile.TileElectricFurnace;

public class BlockElectricFurnace extends BlockMachine {
	public BlockElectricFurnace() {
		super("electricFurnace", TileElectricFurnace.class);
		setHazard(true);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "SSS", "SCS", "SMS", 'S', "ingotSteel", 'C', "circuitAdvanced", 'M', "motor");
		addRecipe(new ItemStack(this, 1, EnumElectricFurnace.INDUSTRIAL.ordinal()), "SSS", "MCM", "SMS", 'S', "plateSteel", 'C', new ItemStack(this, 1, EnumElectricFurnace.NORMAL.ordinal()), 'M', "circuitElite");
	}

	@Override
	public int getRenderColor(int par1)
	{
		return EnumElectricFurnace.values()[par1].getRenderColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		return getRenderColor(world.getBlockMetadata(x, y, z));
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (EnumElectricFurnace type : EnumElectricFurnace.values())
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

	public enum EnumElectricFurnace {
		NORMAL(1000, 16777215), INDUSTRIAL(2500, (255 & 0xFF) << 24 | (145 & 0xFF) << 16 | (161 & 0xFF) << 8 | 175 & 0xFF);
		private int	usage;
		private int	renderColor;

		private EnumElectricFurnace(int usage, int renderColor) {
			this.usage = usage;
			this.renderColor = renderColor;
		}

		public int getUsage()
		{
			return usage;
		}

		public int getRenderColor()
		{
			return renderColor;
		}
	}
}
