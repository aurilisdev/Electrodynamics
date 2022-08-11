package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.library.block.BlockBaseContainerModelled;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileQuantumAssembler;

public class BlockQuantumAssembler extends BlockBaseContainerModelled {

	public BlockQuantumAssembler() {
		super(Material.iron);
		setHardness(10);
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
	public void registerRecipes() {
		addRecipe(this, "CPC", "QTQ", "CPC", 'C', "circuitElite", 'Q', NuclearItemRegister.itemEmptyQuantumCell, 'P', new ItemStack(NuclearBlockRegister.blockElectromagnet, 1, BlockElectromagnet.EnumElectromagnet.CONTAINMENT_NORMAL.ordinal()), 'T', new ItemStack(NuclearBlockRegister.blockCentrifuge));
	}

	@Override
	public String getSide() {
		return "Nuclear";
	}
}
