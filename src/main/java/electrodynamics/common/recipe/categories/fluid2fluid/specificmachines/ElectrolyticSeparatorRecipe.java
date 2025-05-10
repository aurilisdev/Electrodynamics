package electrodynamics.common.recipe.categories.fluid2fluid.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import voltaic.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class ElectrolyticSeparatorRecipe extends Fluid2FluidRecipe {

	public static final String RECIPE_GROUP = "electrolytic_separator_recipe";
	public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

	public ElectrolyticSeparatorRecipe(ResourceLocation recipeGroup, List<FluidIngredient> inputFluidIngredients, FluidStack outputFluidStack, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts) {
		super(recipeGroup, inputFluidIngredients, outputFluidStack, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);

	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipies.ELECTROLYTIC_SEPARATOR_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE;
	}

}
