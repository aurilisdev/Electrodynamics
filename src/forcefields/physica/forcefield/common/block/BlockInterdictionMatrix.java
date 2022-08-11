package physica.forcefield.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.block.BlockBaseContainerModelled;

public class BlockInterdictionMatrix extends BlockBaseContainerModelled {

	public BlockInterdictionMatrix() {
		super(Material.iron);
		setHardness(10);
		setResistance(500);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setBlockName(ForcefieldReferences.PREFIX + "interdictionMatrix");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileInterdictionMatrix();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "BEB", "ESE", "BEB", 'B', "phyBattery", 'E', "circuitElite", 'S', ForcefieldItemRegister.moduleMap.get("moduleUpgradeShock"));
	}

	@Override
	public String getSide()
	{
		return "Forcefields";
	}
}
