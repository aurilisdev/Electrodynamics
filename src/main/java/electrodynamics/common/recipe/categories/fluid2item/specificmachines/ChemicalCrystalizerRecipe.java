package electrodynamics.common.recipe.categories.fluid2item.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ChemicalCrystalizerRecipe extends Fluid2ItemRecipe {

	public static final String RECIPE_GROUP = "chemical_crystallizer_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ChemicalCrystalizerRecipe(ResourceLocation recipeID, FluidIngredient[] fluidInput, ItemStack itemOutput, double experience) {
		super(recipeID, fluidInput, itemOutput, experience);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE.get();
	}

}
