package electrodynamics.common.recipe.categories.item2item.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class EnergizedAlloyerRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "energized_alloyer_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public EnergizedAlloyerRecipe(ResourceLocation recipeID, CountableIngredient[] inputs, ItemStack output, double experience, int ticks, double usagePerTick) {
		super(recipeID, inputs, output, experience, ticks, usagePerTick);
	}

	public EnergizedAlloyerRecipe(ResourceLocation id, CountableIngredient[] input, ItemStack output, ProbableItem[] itemBiproducts, double experience, int ticks, double usagePerTick) {
		super(id, input, output, itemBiproducts, experience, ticks, usagePerTick);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_TYPE.get();
	}

}
