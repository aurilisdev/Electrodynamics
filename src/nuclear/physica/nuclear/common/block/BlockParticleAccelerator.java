package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.library.block.BlockBaseContainer;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileParticleAccelerator;

public class BlockParticleAccelerator extends BlockBaseContainer implements IBaseUtilities, IRecipeRegister {

	public BlockParticleAccelerator() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setHarvestLevel("pickaxe", 2);
		setBlockName(NuclearReferences.PREFIX + "accelerator");
		setBlockTextureName(CoreReferences.PREFIX + "accelerator");
		addToRegister(RecipeSide.Nuclear, this);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileParticleAccelerator();
	}

	@Override
	public void initialize() {
		addRecipe(this, "PCP", "CMC", "PCP", 'M', "motor", 'C', "circuitElite", 'P',
				"plateSteel");
	}
}
