package electrodynamics.common.recipe.categories.fluid2gas;

import electrodynamics.common.recipe.categories.fluid2gas.specificmachines.ElectrolyticSeparatorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class Fluid2GasRecipeTypes {

	public static final RecipeSerializer<ElectrolyticSeparatorRecipe> ELECTROLYTIC_SEPARATOR_RECIPE_SERIALIZER = new Fluid2GasRecipeSerializer<>(ElectrolyticSeparatorRecipe.class);
	
}
