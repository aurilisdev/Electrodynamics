package physica.nuclear.common.recipe.type;

import net.minecraft.item.ItemStack;
import physica.library.recipe.RecipeSystemTemplate;

public class ChemicalExtractorRecipe extends RecipeSystemTemplate {

	private int waterRequired;

	public ChemicalExtractorRecipe(int waterRequired, ItemStack inputItem, ItemStack output) {
		super("", inputItem, output);
		this.waterRequired = waterRequired;
	}

	public ChemicalExtractorRecipe(int waterRequired, String oreDictName, ItemStack output) {
		super(oreDictName, null, output);
		this.waterRequired = waterRequired;
	}

	public int getWaterUse() {
		return waterRequired;
	}

}
