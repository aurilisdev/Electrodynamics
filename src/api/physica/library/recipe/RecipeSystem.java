package physica.library.recipe;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.item.ItemStack;
import physica.library.util.OreDictionaryUtilities;

public class RecipeSystem {
	private static final HashMap<Object, HashSet<RecipeSystemTemplate>> recipeMap = new HashMap<>();

	public static HashMap<Object, HashSet<RecipeSystemTemplate>> getRecipemap()
	{
		return recipeMap;
	}

	public static <T extends RecipeSystemTemplate> void registerRecipe(Object handle, T recipe)
	{
		if (!recipeMap.containsKey(handle))
		{
			recipeMap.put(handle, new HashSet<>());
		}
		recipeMap.get(handle).add(recipe);
	}

	public static <T extends RecipeSystemTemplate> void unregisterRecipe(Object handle, T recipe)
	{
		if (recipeMap.containsKey(handle))
		{
			recipeMap.get(handle).remove(recipe);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends RecipeSystemTemplate> HashSet<T> getHandleRecipes(Object handle)
	{
		if (!recipeMap.containsKey(handle))
		{
			recipeMap.put(handle, new HashSet<>());
		}
		return (HashSet<T>) recipeMap.get(handle);
	}

	@SuppressWarnings("unchecked")
	public static <T extends RecipeSystemTemplate> T getRecipe(Object handle, ItemStack input)
	{
		if (input != null)
		{
			for (RecipeSystemTemplate recipe : getHandleRecipes(handle))
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
			for (RecipeSystemTemplate recipe : getHandleRecipes(handle))
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
