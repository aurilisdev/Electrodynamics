package electrodynamics.common.recipe.categories.fluiditem2fluid;

import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.FermentationPlantRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.MineralWasherRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class FluidItem2FluidRecipeTypes {

    public static final IRecipeSerializer<ChemicalMixerRecipe> CHEMICAL_MIXER_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(
	    ChemicalMixerRecipe.class);
    public static final IRecipeSerializer<FermentationPlantRecipe> FERMENTATION_PLANT_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(
	    FermentationPlantRecipe.class);
    public static final IRecipeSerializer<MineralWasherRecipe> MINERAL_WASHER_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(
    	MineralWasherRecipe.class);
    
}
