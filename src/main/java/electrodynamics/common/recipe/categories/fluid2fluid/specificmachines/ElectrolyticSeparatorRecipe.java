package electrodynamics.common.recipe.categories.fluid2fluid.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

public class ElectrolyticSeparatorRecipe extends Fluid2FluidRecipe {
	
	public static final String RECIPE_GROUP = "electrolytic_separator_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ElectrolyticSeparatorRecipe(FluidIngredient[] inputFluids, FluidStack outputFluid, ProbableFluid[] fluidbiproducts, ResourceLocation recipeID, double experience) {
		super(inputFluids, outputFluid, fluidbiproducts, recipeID, experience);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPARATOR_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return Registry.RECIPE_TYPE.get(RECIPE_ID);
	}

}
