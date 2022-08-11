package physica.nuclear.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;

public class BlockControlRod extends Block implements IBaseUtilities, IRecipeRegister {

	public BlockControlRod() {
		super(Material.iron);
		setHardness(5);
		setResistance(10);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "controlRod");
		setBlockTextureName("iron_block");
		setStepSound(Block.soundTypeMetal);
		setBlockBounds(0.25f, 0, 0.25f, 0.75f, 1, 0.75f);
		addToRegister("Nuclear", this);
	}

	@Override
	public void registerRecipes() {
		addRecipe(this, "ISI", "IAI", "ISI", 'I', "plateIron", 'S', "plateSteel", 'A', "circuitAdvanced");
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
