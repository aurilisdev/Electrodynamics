package electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class FermentationPlantRecipe extends FluidItem2FluidRecipe {

    public static final String RECIPE_GROUP = "fermentation_plant_recipe";
    public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

    public FermentationPlantRecipe(ResourceLocation group, List<CountableIngredient> inputItems,
	    List<FluidIngredient> inputFluids, FluidStack outputFluid, double experience, int ticks,
	    double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts,
	    List<ProbableGas> gasBiproducts) {
	super(group, inputItems, inputFluids, outputFluid, experience, ticks, usagePerTick, itemBiproducts,
		fluidBiproducts, gasBiproducts);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipies.FERMENTATION_PLANT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return ElectrodynamicsRecipies.FERMENTATION_PLANT_TYPE.get();
    }

}
