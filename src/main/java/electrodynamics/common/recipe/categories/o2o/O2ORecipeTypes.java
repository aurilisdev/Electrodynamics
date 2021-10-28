package electrodynamics.common.recipe.categories.o2o;

import electrodynamics.common.recipe.categories.o2o.specificmachines.LatheRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.WireMillRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class O2ORecipeTypes {

    public static final RecipeSerializer<WireMillRecipe> WIRE_MILL_JSON_SERIALIZER = new O2ORecipeSerializer<>(WireMillRecipe.class);
    public static final RecipeSerializer<MineralGrinderRecipe> MINERAL_CRUSHER_JSON_SERIALIZER = new O2ORecipeSerializer<>(
	    MineralGrinderRecipe.class);
    public static final RecipeSerializer<MineralCrusherRecipe> MINERAL_GRINDER_JSON_SERIALIZER = new O2ORecipeSerializer<>(
	    MineralCrusherRecipe.class);
    public static final RecipeSerializer<LatheRecipe> LATHE_JSON_SERIALIZER = new O2ORecipeSerializer<>(LatheRecipe.class);
}
