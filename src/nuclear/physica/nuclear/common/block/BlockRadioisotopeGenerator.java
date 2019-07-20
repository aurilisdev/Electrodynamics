package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.library.block.BlockBaseContainerModelled;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileRadioisotopeGenerator;

public class BlockRadioisotopeGenerator extends BlockBaseContainerModelled {

	public BlockRadioisotopeGenerator() {
		super(Material.iron);
		setHardness(10);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "radioisotopeGenerator");
		setBlockBounds(1.0f / 16.0f, 0, 1.0f / 16.0f, 15.0f / 16.0f, 1.0f, 15.0f / 16.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileRadioisotopeGenerator();
	}

	@Override
	public void registerRecipes()
	{
		// addRecipe(this, "IPI", "MCM", "IPI", 'I', "ingotSteel", 'M', "motor", 'P',
		// "plateSteel", 'C', "circuitAdvanced");
	}

	@Override
	public String getSide()
	{
		return "Nuclear";
	}
}
