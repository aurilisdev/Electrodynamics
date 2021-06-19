package electrodynamics.common.recipe.categories.do2o;

import electrodynamics.common.recipe.categories.do2o.specificmachines.OxidationFurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class DO2ORecipeTypes {

    public static final IRecipeSerializer<OxidationFurnaceRecipe> OXIDATION_FURNACE_JSON_SERIALIZER = new DO2ORecipeSerializer<>(
	    OxidationFurnaceRecipe.class);
}
