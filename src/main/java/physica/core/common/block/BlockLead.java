package physica.core.common.block;

import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.world.IBlockAccess;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.core.common.CoreTabRegister;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;

public class BlockLead extends BlockCompressed implements IBaseUtilities, IRecipeRegister {

	public BlockLead() {
		super(MapColor.ironColor);
		setHardness(15.0F);
		setResistance(20.0F);
		setStepSound(soundTypeMetal);
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockName(CoreReferences.PREFIX + "blockLead");
		setBlockTextureName("iron_block");
		addToRegister(RecipeSide.Core, this);
	}

	@Override
	public int getRenderColor(int par1)
	{
		return (int) (3559534 * 0.47999999);
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		return getRenderColor(0);
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "IPI", "IPI", "IPI", 'I', "ingotLead", 'P', "plateLead");
	}
}
