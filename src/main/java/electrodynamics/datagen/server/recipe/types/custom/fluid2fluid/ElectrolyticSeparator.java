package electrodynamics.datagen.server.recipe.types.custom.fluid2fluid;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeFluidOutput;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.fluids.FluidStack;

public class ElectrolyticSeparator extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {
		
		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidOxygen, 1000), 0, "water_to_hydrogen_and_oxygen")
		//
		.addFluidTagInput(FluidTags.WATER, 1000)
		//
		.addFluidBiproduct(new ProbableFluid(new FluidStack(ElectrodynamicsFluids.fluidHydrogen, 2000), 1))
		//
		.complete(consumer);;
		
	}
	
	private FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, String name) {
		return FinishedRecipeFluidOutput.of(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPARATOR_SERIALIZER.get(), stack, xp)
				.name(RecipeCategory.FLUID_2_FLUID, References.ID, "electrolytic_separator/" + name);
	}

}
