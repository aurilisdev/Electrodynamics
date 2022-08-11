package physica.forcefield.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.forcefield.common.tile.TileFortronCapacitor;
import physica.library.block.BlockBaseContainerModelled;

public class BlockFortronCapacitor extends BlockBaseContainerModelled {

	public BlockFortronCapacitor() {
		super(Material.iron);
		setHardness(10);
		setResistance(500);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setBlockName(ForcefieldReferences.PREFIX + "fortronCapacitor");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFortronCapacitor();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "MFM", "FCF", "MFM", 'C', "phyBattery", 'F', ForcefieldItemRegister.itemFocusMatrix, 'M', "plateSteel");
	}

	@Override
	public String getSide()
	{
		return "Forcefields";
	}
}
