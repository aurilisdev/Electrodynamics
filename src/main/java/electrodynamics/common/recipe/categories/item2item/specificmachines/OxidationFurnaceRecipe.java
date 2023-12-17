package electrodynamics.common.recipe.categories.item2item.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class OxidationFurnaceRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "oxidation_furnace_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public OxidationFurnaceRecipe(ResourceLocation recipeID, CountableIngredient[] inputs, ItemStack output, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
		super(recipeID, inputs, output, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.OXIDATION_FURNACE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE;
	}

}
