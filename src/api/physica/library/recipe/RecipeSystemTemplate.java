package physica.library.recipe;

import net.minecraft.item.ItemStack;

public class RecipeSystemTemplate {
	private final String	oredict;
	private final ItemStack	input;
	private final ItemStack	output;

	public RecipeSystemTemplate(String oredict, ItemStack input, ItemStack output) {
		this.oredict = oredict;
		this.input = input;
		this.output = output;
	}

	public ItemStack getInput()
	{
		return input;
	}

	public String getOredict()
	{
		return oredict;
	}

	public ItemStack getOutput()
	{
		return output;
	}
}
