package physica.nuclear.common.recipe;

import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.library.util.OreDictionaryUtilities;
import physica.nuclear.common.recipe.type.ChemicalBoilerRecipe;
import physica.nuclear.common.recipe.type.ChemicalExtractorRecipe;

public class NuclearCustomRecipeHelper {

	public static void addExtractorRecipe(int waterUse, Item input, ItemStack output)
	{
		NuclearCustomRecipeRegister.getInstance().addExtractorRecipe(waterUse, input, output);
	}

	public static void addBoilerRecipe(int waterUse, Item input, int hexafluorideProduced)
	{
		NuclearCustomRecipeRegister.getInstance().addBoilerRecipe(waterUse, input, hexafluorideProduced);
	}

	public static void addExtractorRecipe(int waterUse, String oreDictName, ItemStack output)
	{
		NuclearCustomRecipeRegister.getInstance().addExtractorRecipe(waterUse, oreDictName, output);
	}

	public static void addBoilerRecipe(int waterUse, String oreDictName, int hexafluorideProduced)
	{
		NuclearCustomRecipeRegister.getInstance().addBoilerRecipe(waterUse, oreDictName, hexafluorideProduced);
	}

	public static ChemicalBoilerRecipe getBoilerRecipe(ItemStack input)
	{
		if (input != null)
		{
			for (ChemicalBoilerRecipe recipe : NuclearCustomRecipeRegister.getInstance().getBoilerRecipes())
			{
				if (recipe.getInput() != null && recipe.getInput() == input.getItem()
						|| recipe.getOreDictName() != null && OreDictionaryUtilities.isSameOre(input, recipe.getOreDictName()))
				{
					return recipe;
				}
			}
		}
		return null;
	}

	public static ChemicalExtractorRecipe getExtractorRecipe(ItemStack input)
	{
		if (input != null)
		{
			for (ChemicalExtractorRecipe recipe : NuclearCustomRecipeRegister.getInstance().getExtractorRecipes())
			{
				if (recipe.getInput() != null && recipe.getInput() == input.getItem()
						|| recipe.getOreDictName() != null && OreDictionaryUtilities.isSameOre(input, recipe.getOreDictName()))
				{
					return recipe;
				}
			}
		}
		return null;
	}

	public static boolean isBoilerInput(ItemStack input)
	{
		if (input != null)
		{
			for (ChemicalBoilerRecipe recipe : NuclearCustomRecipeRegister.getInstance().getBoilerRecipes())
			{
				if (recipe.getInput() != null && recipe.getInput() == input.getItem() || recipe.getOreDictName() != null && OreDictionaryUtilities.isSameOre(input, recipe.getOreDictName()))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isExtractorInput(ItemStack input)
	{
		if (input != null)
		{
			for (ChemicalExtractorRecipe recipe : NuclearCustomRecipeRegister.getInstance().getExtractorRecipes())
			{
				if (recipe.getInput() != null && recipe.getInput() == input.getItem() || recipe.getOreDictName() != null && OreDictionaryUtilities.isSameOre(input, recipe.getOreDictName()))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static Set<ChemicalExtractorRecipe> getExtractorRecipes()
	{
		return NuclearCustomRecipeRegister.getInstance().getExtractorRecipes();
	}

	public static Set<ChemicalBoilerRecipe> getBoilerRecipes()
	{
		return NuclearCustomRecipeRegister.getInstance().getBoilerRecipes();
	}
}
