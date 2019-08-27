package physica.library.recipe;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.item.ItemStack;
import physica.library.util.OreDictionaryUtilities;

public class RecipeSystem {
	private static final HashMap<Object, HashSet<SimpleRecipe>> recipeMap = new HashMap<>();

	public static HashMap<Object, HashSet<SimpleRecipe>> getRecipemap()
	{
		return recipeMap;
	}

	public static void registerRecipe(Object handle, SimpleRecipe recipe)
	{
		if (!recipeMap.containsKey(handle))
		{
			recipeMap.put(handle, new HashSet<>());
		}
		recipeMap.get(handle).add(recipe);
	}

	public static void unregisterRecipe(Object handle, SimpleRecipe recipe)
	{
		if (recipeMap.containsKey(handle))
		{
			recipeMap.get(handle).remove(recipe);
		}
	}

	public static HashSet<SimpleRecipe> getHandleRecipes(Object handle)
	{
		if (!recipeMap.containsKey(handle))
		{
			recipeMap.put(handle, new HashSet<>());
		}
		return recipeMap.get(handle);
	}

	@SuppressWarnings("unchecked")
	public static <T extends SimpleRecipe> T getRecipe(Object handle, ItemStack input)
	{
		if (input != null)
		{
			for (SimpleRecipe recipe : getHandleRecipes(handle))
			{
				if (recipe.getInput() != null && recipe.getInput().isItemEqual(input) || recipe.getOredict() != null && OreDictionaryUtilities.isSameOre(input, recipe.getOredict()))
				{
					return (T) recipe;
				}
			}
		}
		return null;
	}

	public static boolean isRecipeInput(Object handle, ItemStack input)
	{
		if (input != null)
		{
			for (SimpleRecipe recipe : getHandleRecipes(handle))
			{
				if (recipe.getInput() != null && recipe.getInput().isItemEqual(input) || recipe.getOredict() != null && OreDictionaryUtilities.isSameOre(input, recipe.getOredict()))
				{
					return true;
				}
			}
		}
		return false;
	}
}
