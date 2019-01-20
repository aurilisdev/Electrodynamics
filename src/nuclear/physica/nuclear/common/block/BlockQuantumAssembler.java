package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.library.block.BlockBaseContainerModelled;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileQuantumAssembler;

public class BlockQuantumAssembler extends BlockBaseContainerModelled {

	public BlockQuantumAssembler() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setHarvestLevel("pickaxe", 2);
		setBlockName(NuclearReferences.PREFIX + "assembler");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileQuantumAssembler();
	}

	@Override
	public void initialize() {
		addRecipe(this, "ESE", "QCQ", "ESE",
				'E', "circuitElite", 'Q', NuclearItemRegister.itemEmptyQuantumCell,
				'C', new ItemStack(NuclearBlockRegister.blockCentrifuge), 'S', "plateSteel");
	}

	@Override
	public RecipeSide getSide() {
		return RecipeSide.Nuclear;
	}
}
