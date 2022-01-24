package electrodynamics.common.recipe.categories.fluid2fluid;

import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolyticSeparatorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class Fluid2FluidRecipeTypes {

	public static final RecipeSerializer<ElectrolyticSeparatorRecipe> ELECTROLYTIC_SEPARATOR_RECIPE_SERIALIZER = new Fluid2FluidRecipeSerializer<>(ElectrolyticSeparatorRecipe.class);
}
