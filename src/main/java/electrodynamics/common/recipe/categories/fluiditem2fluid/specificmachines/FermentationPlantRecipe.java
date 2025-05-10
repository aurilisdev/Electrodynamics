package electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class FermentationPlantRecipe extends FluidItem2FluidRecipe {

	public static final String RECIPE_GROUP = "fermentation_plant_recipe";
	public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

	public FermentationPlantRecipe(ResourceLocation group, List<CountableIngredient> inputItems, List<FluidIngredient> inputFluids, FluidStack outputFluid, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts) {
        super(group, inputItems, inputFluids, outputFluid, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
    }

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ElectrodynamicsRecipies.FERMENTATION_PLANT_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ElectrodynamicsRecipies.FERMENTATION_PLANT_TYPE;
	}

}
