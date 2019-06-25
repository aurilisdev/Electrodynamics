package physica.forcefield.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.forcefield.common.tile.TileBiometricIdentifier;
import physica.library.block.BlockBaseContainerModelled;
import physica.library.recipe.RecipeSide;

public class BlockBiometricIdentifier extends BlockBaseContainerModelled {

	public BlockBiometricIdentifier() {
		super(Material.iron);
		setHardness(10);
		setResistance(500);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setBlockName(ForcefieldReferences.PREFIX + "biometricIdentifier");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileBiometricIdentifier();
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "FMF", "CMC", "FMF", 'C', ForcefieldItemRegister.itemIdentifcationCard, 'M', "circuitElite", 'F', "plateSteel");
	}

	@Override
	public RecipeSide getSide()
	{
		return RecipeSide.Forcefield;
	}
}
