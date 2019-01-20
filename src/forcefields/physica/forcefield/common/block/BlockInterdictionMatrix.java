package physica.forcefield.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.block.BlockBaseContainerModelled;
import physica.library.recipe.RecipeSide;

public class BlockInterdictionMatrix extends BlockBaseContainerModelled {

	public BlockInterdictionMatrix() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setBlockName(ForcefieldReferences.PREFIX + "interdictionMatrix");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileInterdictionMatrix();
	}

	@Override
	public void initialize() {
		addRecipe(this, "SSS", "FFF", "FEF", 'S', ForcefieldItemRegister.moduleMap.get("moduleUpgradeShock"), 'E', Item.getItemFromBlock(Blocks.ender_chest), 'F',
				ForcefieldItemRegister.itemFocusMatrix);
	}

	@Override
	public RecipeSide getSide() {
		return RecipeSide.Forcefield;
	}
}
