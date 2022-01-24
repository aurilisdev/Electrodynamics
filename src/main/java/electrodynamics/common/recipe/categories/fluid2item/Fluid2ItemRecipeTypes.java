package electrodynamics.common.recipe.categories.fluid2item;

import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class Fluid2ItemRecipeTypes {

	public static final RecipeSerializer<ChemicalCrystalizerRecipe> CHEMICAL_CRYSTALIZER_JSON_SERIALIZER = new Fluid2ItemRecipeSerializer<>(ChemicalCrystalizerRecipe.class);

}
