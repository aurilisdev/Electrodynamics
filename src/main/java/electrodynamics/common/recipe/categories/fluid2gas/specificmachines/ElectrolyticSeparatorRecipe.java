package electrodynamics.common.recipe.categories.fluid2gas.specificmachines;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2gas.Fluid2GasRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ElectrolyticSeparatorRecipe extends Fluid2GasRecipe {

	public static final String RECIPE_GROUP = "electrolytic_separator_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ElectrolyticSeparatorRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, GasStack outputGas, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidbiproducts, ProbableGas[] gasBiproducts) {
		super(recipeID, inputFluids, outputGas, experience, ticks, usagePerTick, itemBiproducts, fluidbiproducts, gasBiproducts);

	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPARATOR_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get();
	}

}
