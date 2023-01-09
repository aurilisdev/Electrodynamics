package electrodynamics.datagen.server.recipe.types.custom.fluid2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChemicalCrystallizer extends AbstractRecipeGenerator {

	public static double CHEMICALCRYSTALLIZER_USAGE_PER_TICK = 800.0;
	public static int CHEMICALCRYSTALLIZER_REQUIRED_TICKS = 200;

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(Items.CLAY_BALL), 0, 200, 800.0, "clay_ball")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.CLAY, 200)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.OBSIDIAN), 0, 200, 800.0, "obsidian_from_lava")
				//
				.addFluidTagInput(FluidTags.LAVA, 1000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_SHEETPLASTIC.get()), 0, 200, 800.0, "plastic_sheet")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.POLYETHLYENE, 500)
				//
				.complete(consumer);

		for (SubtypeSulfateFluid fluid : SubtypeSulfateFluid.values()) {
			if (fluid.crystal != null) {
				newRecipe(new ItemStack(fluid.crystal.get()), 0, 200, 800.0, "crystal_" + fluid.name() + "_from_sulfate")
						//
						.addFluidTagInput(fluid.tag, 200)
						//
						.complete(consumer);
			}
		}

	}

	private FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_2_ITEM, References.ID, "chemical_crystallizer/" + name);
	}

}
