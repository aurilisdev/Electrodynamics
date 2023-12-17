package electrodynamics.common.recipe.categories.fluid2item.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class ChemicalCrystalizerRecipe extends Fluid2ItemRecipe {

	public static final String RECIPE_GROUP = "chemical_crystallizer_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ChemicalCrystalizerRecipe(ResourceLocation recipeID, FluidIngredient[] fluidInput, ItemStack itemOutput, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
		super(recipeID, fluidInput, itemOutput, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
		
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE;
	}

}
