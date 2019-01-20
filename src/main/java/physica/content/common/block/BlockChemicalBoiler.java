package physica.content.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.Physica;
import physica.References;
import physica.api.lib.block.BlockBaseContainer;
import physica.content.common.tile.TileChemicalBoiler;

public class BlockChemicalBoiler extends BlockBaseContainer {
	public BlockChemicalBoiler() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setCreativeTab(Physica.creativeTab);
		setBlockName(References.PREFIX + "chemicalBoiler");

	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileChemicalBoiler();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.iron_block.getIcon(0, 0);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}
}
