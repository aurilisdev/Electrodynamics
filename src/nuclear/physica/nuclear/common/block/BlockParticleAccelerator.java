package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.api.nuclear.IElectromagnet;
import physica.library.block.BlockBaseContainer;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileParticleAccelerator;

public class BlockParticleAccelerator extends BlockBaseContainer implements IBaseUtilities, IRecipeRegister, IElectromagnet {

	public BlockParticleAccelerator() {
		super(Material.iron);
		setHardness(7.5f);
		setResistance(5);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setHarvestLevel("pickaxe", 2);
		setBlockName(NuclearReferences.PREFIX + "accelerator");
		setBlockTextureName(CoreReferences.PREFIX + "accelerator");
		addToRegister("Nuclear", this);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileParticleAccelerator();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "PCP", "CMC", "PCP", 'M', "motor", 'C', "circuitElite", 'P', new ItemStack(NuclearBlockRegister.blockElectromagnet, 1, BlockElectromagnet.EnumElectromagnet.CONTAINMENT_NORMAL.ordinal()));

	}
}
