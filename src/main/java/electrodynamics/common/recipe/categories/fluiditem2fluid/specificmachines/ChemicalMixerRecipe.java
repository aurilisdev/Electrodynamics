package electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class ChemicalMixerRecipe extends FluidItem2FluidRecipe {

    public static final String RECIPE_GROUP = "chemical_mixer_recipe";
    public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

    public ChemicalMixerRecipe(String group, List<CountableIngredient> inputItems, List<FluidIngredient> inputFluids,
	    FluidStack outputFluid, double experience, int ticks, double usagePerTick,
	    List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
	super(group, inputItems, inputFluids, outputFluid, experience, ticks, usagePerTick, itemBiproducts,
		fluidBiproducts, gasBiproducts);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipies.CHEMICAL_MIXER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return ElectrodynamicsRecipies.CHEMICAL_MIXER_TYPE.get();
    }

}
