package physica.nuclear.common.recipe.type;

import net.minecraft.item.ItemStack;
import physica.library.recipe.RecipeSystemTemplate;

public class ChemicalBoilerRecipe extends RecipeSystemTemplate {

	private int waterRequired;
	private int hexaFluorideGenerated;

	public ChemicalBoilerRecipe(int waterRequired, ItemStack inputItem, int hexaFluorideGenerated) {
		super("", inputItem, null);
		this.waterRequired = waterRequired;
		this.hexaFluorideGenerated = hexaFluorideGenerated;
	}

	public ChemicalBoilerRecipe(int waterRequired, String oreDictName, int hexaFluorideGenerated) {
		super(oreDictName, null, null);
		this.waterRequired = waterRequired;
		this.hexaFluorideGenerated = hexaFluorideGenerated;
	}

	public int getWaterUse() {
		return waterRequired;
	}

	public int getHexafluorideGenerated() {
		return hexaFluorideGenerated;
	}

}
