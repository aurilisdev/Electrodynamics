package physica.core.common.block;

import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import physica.CoreReferences;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreTabRegister;

public class BlockLead extends BlockCompressed implements IBaseUtilities, IRecipeRegister {

	public BlockLead() {
		super(MapColor.ironColor);
		setHardness(15.0F);
		setResistance(20.0F);
		setStepSound(soundTypeMetal);
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockName(CoreReferences.PREFIX + "blockLead");
		setBlockTextureName(CoreReferences.PREFIX + "blocklead");
		addToRegister("Core", this);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "IPI", "IPI", "IPI", 'I', "ingotLead", 'P', "plateLead");
	}
}
