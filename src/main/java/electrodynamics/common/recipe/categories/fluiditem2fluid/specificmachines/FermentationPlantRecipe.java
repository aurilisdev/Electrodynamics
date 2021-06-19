package electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

public class FermentationPlantRecipe extends FluidItem2FluidRecipe {

    public static final String RECIPE_GROUP = "fermentation_plant_recipe";
    public static final String MOD_ID = electrodynamics.api.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public FermentationPlantRecipe(ResourceLocation recipeID, CountableIngredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid) {
	super(recipeID, inputItem, inputFluid, outputFluid);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipeInit.FERMENTATION_PLANT_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
	return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
    }

}
