package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreItemRegister;
import physica.library.block.BlockBaseContainerModelled;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileChemicalBoiler;

public class BlockChemicalBoiler extends BlockBaseContainerModelled {

	public BlockChemicalBoiler() {
		super(Material.iron);
		setHardness(10);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "chemicalBoiler");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileChemicalBoiler();
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "PCP", "TFT", "PMP", 'F', CoreBlockRegister.blockBlastFurnace, 'T', CoreItemRegister.itemEmptyCell, 'M', "motor", 'P',
				"plateSteel", 'C', "circuitAdvanced");
	}

	@Override
	public RecipeSide getSide()
	{
		return RecipeSide.Nuclear;
	}
}
