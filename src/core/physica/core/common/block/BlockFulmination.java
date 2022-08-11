package physica.core.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreTabRegister;
import physica.core.common.tile.TileFulmination;

public class BlockFulmination extends Block implements ITileEntityProvider, IBaseUtilities, IRecipeRegister {

	public BlockFulmination() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(Float.MAX_VALUE);
		setHarvestLevel("pickaxe", 2);
		setBlockTextureName(CoreReferences.PREFIX + "fulmination");
		setBlockName(CoreReferences.PREFIX + "fulmination");
		setCreativeTab(CoreTabRegister.coreTab);
		addToRegister("Core", this);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileFulmination();
	}

	@Override
	public void registerRecipes() {
		addRecipe(CoreBlockRegister.blockFulmination, "OSO", "SCS", "OSO", 'O', Blocks.obsidian, 'C', "circuitAdvanced", 'S', "plateSteel");
	}

}
