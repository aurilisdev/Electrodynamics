package electrodynamics.common.recipe.categories.o2o;

import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.o2o.specificmachines.WireMillRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class O2ORecipeTypes {

	public static final IRecipeSerializer<WireMillRecipe> WIRE_MILL_JSON_SERIALIZER = new O2ORecipeSerializer<WireMillRecipe>(WireMillRecipe.class);
	public static final IRecipeSerializer<MineralGrinderRecipe> MINERAL_CRUSHER_JSON_SERIALIZER = new O2ORecipeSerializer<MineralGrinderRecipe>(MineralGrinderRecipe.class);
	public static final IRecipeSerializer<MineralCrusherRecipe> MINERAL_GRINDER_JSON_SERIALIZER = new O2ORecipeSerializer<MineralCrusherRecipe>(MineralCrusherRecipe.class);
	
}
