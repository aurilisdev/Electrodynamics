package electrodynamics.datagen.server.recipe.types.custom.fluid2gas;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeGasOutput;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;

public class ElectrodynamicsElectrolyticSeparatorRecipes extends AbstractRecipeGenerator {

	public static int ELECTROLYTICSEPARATOR_REQUIRED_TICKS = 200;
	public static double ELECTROLYTICSEPARATOR_USAGE_PER_TICK = 250.0;

	private final String modID;

	public ElectrodynamicsElectrolyticSeparatorRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsElectrolyticSeparatorRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new GasStack(ElectrodynamicsGases.OXYGEN.get(), 1000, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL), 0, 200, 250.0, "water_to_hydrogen_and_oxygen")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addGasBiproduct(new ProbableGas(new GasStack(ElectrodynamicsGases.HYDROGEN.get(), 2000, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL), 1))
				//
				.complete(consumer);

	}

	public FinishedRecipeGasOutput newRecipe(GasStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeGasOutput.of(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPARATOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_2_GAS, modID, "electrolytic_separator/" + name);
	}

}
