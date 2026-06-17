package electrodynamics.common.recipe.categories.fluid2gas.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import voltaic.api.gas.GasStack;
import voltaic.common.recipe.categories.fluid2gas.Fluid2GasRecipe;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class ElectrolyticSeparatorRecipe extends Fluid2GasRecipe {

    public static final String RECIPE_GROUP = "electrolytic_separator_recipe";
    public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

    public ElectrolyticSeparatorRecipe(String recipeGroup, List<FluidIngredient> inputFluidIngredients,
	    GasStack outputGasStack, double experience, int ticks, double usagePerTick,
	    List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
	super(recipeGroup, inputFluidIngredients, outputGasStack, experience, ticks, usagePerTick, itemBiproducts,
		fluidBiproducts, gasBiproducts);

    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipies.ELECTROLYTIC_SEPARATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE.get();
    }

}
