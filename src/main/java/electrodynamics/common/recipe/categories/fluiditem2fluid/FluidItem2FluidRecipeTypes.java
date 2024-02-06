package electrodynamics.common.recipe.categories.fluiditem2fluid;

import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.FermentationPlantRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.MineralWasherRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FluidItem2FluidRecipeTypes {

	public static final RecipeSerializer<ChemicalMixerRecipe> CHEMICAL_MIXER_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(ChemicalMixerRecipe::new);
	public static final RecipeSerializer<FermentationPlantRecipe> FERMENTATION_PLANT_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(FermentationPlantRecipe::new);
	public static final RecipeSerializer<MineralWasherRecipe> MINERAL_WASHER_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(MineralWasherRecipe::new);

}
