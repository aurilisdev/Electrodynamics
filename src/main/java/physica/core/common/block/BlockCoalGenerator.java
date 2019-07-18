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
import physica.core.common.tile.TileCoalGenerator;
import physica.library.block.BlockBaseContainerModelled;

public class BlockCoalGenerator extends BlockBaseContainerModelled {

	public BlockCoalGenerator() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockName(CoreReferences.PREFIX + "coalGenerator");
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		TileCoalGenerator tile = (TileCoalGenerator) world.getTileEntity(x, y, z);
		return tile.generate > 0 ? Blocks.lava.getLightValue() : 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileCoalGenerator();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(CoreBlockRegister.blockCoalGenerator, "ISI", "CFC", "SSS", 'F', Blocks.furnace, 'I', Items.iron_ingot, 'S', "ingotSteel", 'C', Blocks.cobblestone);
	}

	@Override
	public String getSide()
	{
		return "Core";
	}
}
