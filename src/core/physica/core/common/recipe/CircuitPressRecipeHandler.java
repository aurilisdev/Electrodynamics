package physica.core.common.recipe;

import net.minecraft.item.ItemStack;
import physica.library.recipe.RecipeSystemTemplate;

public class CircuitPressRecipeHandler extends RecipeSystemTemplate {

	private final String	oredict2;
	private final ItemStack	input2;

	public CircuitPressRecipeHandler(String oredict, String oredict2, ItemStack input, ItemStack input2, ItemStack output) {
		super(oredict, input, output);
		this.oredict2 = oredict2;
		this.input2 = input2;
	}

	public ItemStack getInput2()
	{
		return input2;
	}

	public String getOredict2()
	{
		return oredict2;
	}
}
