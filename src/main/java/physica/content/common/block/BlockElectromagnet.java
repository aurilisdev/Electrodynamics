package physica.content.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
import net.minecraftforge.common.util.ForgeDirection;
import physica.Physica;
import physica.References;
import physica.content.common.tile.TileElectromagnet;

public class BlockElectromagnet extends Block {
	@SideOnly(Side.CLIENT)
	private static IIcon iconTop, iconGlass;

	public BlockElectromagnet() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(20);
		setBlockTextureName(References.PREFIX + "electromagnet");
		setBlockName(References.PREFIX + "electromagnet");
		setCreativeTab(Physica.creativeTab);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		iconTop = iconRegister.registerIcon(References.PREFIX + "electromagnetTop");
		iconGlass = iconRegister.registerIcon(References.PREFIX + "electromagnetGlass");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return EnumElectromagnet.values()[metadata] == EnumElectromagnet.GLASS ? iconGlass : side == 0 || side == 1 ? iconTop : super.getIcon(side, metadata);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
		int xn = x + dir.offsetX, yn = y + dir.offsetY, zn = z + dir.offsetZ;
		Block neighborBlock = world.getBlock(xn, yn, zn);
		int neighborMetadata = world.getBlockMetadata(xn, yn, zn);

		return block == this && neighborBlock == this && metadata == 1 && neighborMetadata == 1 ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (EnumElectromagnet type : EnumElectromagnet.values())
		{
			list.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 2);
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
		return EnumElectromagnet.values()[world.getBlockMetadata(x, y, z)] == EnumElectromagnet.GLASS ? 0 : super.getLightOpacity(world, x, y, z);
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileElectromagnet();
	}

	public enum EnumElectromagnet
	{
		NORMAL, GLASS;

		public String getName() {
			return name().toLowerCase();
		}
	}
}