package electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeFluidOutput;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class FermentationPlant extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_carrots")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(Tags.Items.CROPS_CARROT, 12)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_melon_slices")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemStackInput(new ItemStack(Items.MELON_SLICE, 3))
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_mushrooms")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(Tags.Items.MUSHROOMS, 11)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_potatos")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(Tags.Items.CROPS_POTATO, 13)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_pumpkins")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemStackInput(new ItemStack(Items.PUMPKIN, 12))
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_seeds")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(Tags.Items.SEEDS, 9)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_sugar_cane")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemStackInput(new ItemStack(Items.SUGAR_CANE, 9))
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidEthanol, 100), 0, "ethanol_from_wheat")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(Tags.Items.CROPS_WHEAT, 9)
				//
				.complete(consumer);

	}

	private FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, String name) {
		return FinishedRecipeFluidOutput.of(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_SERIALIZER.get(), stack, xp)
				.name(RecipeCategory.FLUID_ITEM_2_FLUID, References.ID, "fermentation_plant/" + name);
	}

}
