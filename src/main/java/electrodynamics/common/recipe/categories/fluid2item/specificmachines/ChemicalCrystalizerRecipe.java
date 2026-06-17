package electrodynamics.common.recipe.categories.fluid2item.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import voltaic.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class ChemicalCrystalizerRecipe extends Fluid2ItemRecipe {

    public static final String RECIPE_GROUP = "chemical_crystallizer_recipe";
    public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

    public ChemicalCrystalizerRecipe(String group, List<FluidIngredient> fluidInputs, ItemStack itemOutput,
	    double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts,
	    List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
	super(group, fluidInputs, itemOutput, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts,
		gasBiproducts);

    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_TYPE.get();
    }

}
