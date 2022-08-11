package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.library.block.BlockBaseContainerModelled;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileChemicalExtractor;

public class BlockChemicalExtractor extends BlockBaseContainerModelled {

	public BlockChemicalExtractor() {
		super(Material.iron);
		setHardness(10);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "chemicalExtractor");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileChemicalExtractor();
	}

	@Override
	public void registerRecipes() {
		addRecipe(this, "IPI", "MCM", "IPI", 'I', "ingotSteel", 'M', "motor", 'P', "plateSteel", 'C', "circuitAdvanced");
	}

	@Override
	public String getSide() {
		return "Nuclear";
	}

}
