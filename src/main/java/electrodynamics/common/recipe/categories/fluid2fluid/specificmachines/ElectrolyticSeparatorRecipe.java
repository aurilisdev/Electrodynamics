package electrodynamics.common.recipe.categories.fluid2fluid.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

public class ElectrolyticSeparatorRecipe extends Fluid2FluidRecipe {

	public static final String RECIPE_GROUP = "electrolytic_separator_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ElectrolyticSeparatorRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidbiproducts) {
		super(recipeID, inputFluids, outputFluid, experience, ticks, usagePerTick, itemBiproducts, fluidbiproducts);
		
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
