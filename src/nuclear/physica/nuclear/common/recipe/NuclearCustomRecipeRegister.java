package physica.nuclear.common.recipe;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.nuclear.common.recipe.type.ChemicalBoilerRecipe;
import physica.nuclear.common.recipe.type.ChemicalExtractorRecipe;

public class NuclearCustomRecipeRegister {

	private static NuclearCustomRecipeRegister instance;

	private Set<ChemicalBoilerRecipe> boilerRecipes = new HashSet<>();
	private Set<ChemicalExtractorRecipe> extractorRecipes = new HashSet<>();

	public static NuclearCustomRecipeRegister getInstance()
	{
		if (instance == null)
		{
			instance = new NuclearCustomRecipeRegister();
		}

		return instance;
	}

	public void addExtractorRecipe(int waterUse, Item input, ItemStack output)
	{
		extractorRecipes.add(new ChemicalExtractorRecipe(waterUse, input, output));
	}

	public void addBoilerRecipe(int waterUse, Item input, int hexaProduced)
	{
		boilerRecipes.add(new ChemicalBoilerRecipe(waterUse, input, hexaProduced));
	}

	public void addExtractorRecipe(int waterUse, String input, ItemStack output)
	{
		extractorRecipes.add(new ChemicalExtractorRecipe(waterUse, output, input));
	}

	public void addBoilerRecipe(int waterUse, String input, int hexaProduced)
	{
		boilerRecipes.add(new ChemicalBoilerRecipe(waterUse, input, hexaProduced));
	}

	public Set<ChemicalBoilerRecipe> getBoilerRecipes()
	{
		return boilerRecipes;
	}

	public Set<ChemicalExtractorRecipe> getExtractorRecipes()
	{
		return extractorRecipes;
	}
}
