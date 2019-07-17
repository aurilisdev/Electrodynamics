package physica.core.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreTabRegister;
import physica.core.common.tile.TileBlastFurnace;
import physica.library.block.BlockBaseContainerModelled;

public class BlockBlastFurnace extends BlockBaseContainerModelled {

	public BlockBlastFurnace() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockName(CoreReferences.PREFIX + "blastFurnace");
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		TileBlastFurnace tile = (TileBlastFurnace) world.getTileEntity(x, y, z);
		return tile.isBurning() ? Blocks.lava.getLightValue() : 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileBlastFurnace();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(CoreBlockRegister.blockBlastFurnace, "III", "IFI", "SSS", 'F', Blocks.furnace, 'I', Items.iron_ingot, 'S', Blocks.stonebrick);
	}

	@Override
	public String getSide()
	{
		return "Core";
	}
}
