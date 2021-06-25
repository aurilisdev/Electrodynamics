package electrodynamics.common.recipe.categories.o2o.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class MineralCrusherRecipe extends O2ORecipe/* <MineralCrusherRecipe> */ {

    public static final String RECIPE_GROUP = "mineral_crusher_recipe";
    public static final String MOD_ID = electrodynamics.api.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public MineralCrusherRecipe(ResourceLocation id, CountableIngredient input, ItemStack output) {
	super(id, input, output);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipeInit.MINERAL_CRUSHER_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
	// LOGGER.info("Recipe Type " + MineralCrusherRecipe.class.toString() + ": " +
	// Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID));
	return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
    }

}
