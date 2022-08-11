package physica.core.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;
import physica.core.common.configuration.ConfigCore;
import physica.core.common.tile.TileInfiniteEnergy;

public class BlockInfiniteEnergy extends Block implements ITileEntityProvider {

	public BlockInfiniteEnergy() {
		super(Material.iron);
		setBlockUnbreakable();
		setResistance(20);
		setLightLevel(0.1f);
		setHarvestLevel("pickaxe", 2);
		setBlockName(CoreReferences.PREFIX + "infEnergy");
		if (!ConfigCore.DISABLE_INFINITE_ENERGY_CUBE) {
			setCreativeTab(CoreTabRegister.coreTab);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.iron_bars.getIcon(0, 0);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileInfiniteEnergy();
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
