package electrodynamics.common.recipe.categories.do2o.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ReinforcedAlloyerRecipe extends DO2ORecipe{

	public static final String RECIPE_GROUP = "reinforced_alloyer_recipe";
    public static final String MOD_ID = electrodynamics.api.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);
    
	public ReinforcedAlloyerRecipe(ResourceLocation recipeID, CountableIngredient input1, CountableIngredient input2,
			ItemStack output) {
		super(recipeID, input1, input2, output);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.REINFORCED_ALLOYER_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
	}

}
