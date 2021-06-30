package electrodynamics.common.recipe.categories.fluid2item;

import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class Fluid2ItemRecipeTypes {

    public static final IRecipeSerializer<ChemicalCrystalizerRecipe> CHEMICAL_CRYSTALIZER_JSON_SERIALIZER = new Fluid2ItemRecipeSerializer<>(
	    ChemicalCrystalizerRecipe.class);

}
