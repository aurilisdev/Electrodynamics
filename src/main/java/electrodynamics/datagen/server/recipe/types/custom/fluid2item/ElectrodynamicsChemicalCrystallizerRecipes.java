package electrodynamics.datagen.server.recipe.types.custom.fluid2item;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class ElectrodynamicsChemicalCrystallizerRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALCRYSTALLIZER_USAGE_PER_TICK = 800.0;
	public static int CHEMICALCRYSTALLIZER_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsChemicalCrystallizerRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsChemicalCrystallizerRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(Items.CLAY_BALL), 0, 200, 800.0, "clay_ball")
				//
				.addFluidTagInput(VoltaicTags.Fluids.CLAY, 200)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.OBSIDIAN), 0, 200, 800.0, "obsidian_from_lava")
				//
				.addFluidTagInput(FluidTags.LAVA, 1000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_PLASTIC_FIBERS.get()), 0, 200, 800.0, "plastic_fibers")
				//
				.addFluidTagInput(VoltaicTags.Fluids.POLYETHLYENE, 1000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_CONCRETE.getValue(SubtypeConcrete.regular)), 0, CHEMICALCRYSTALLIZER_REQUIRED_TICKS, CHEMICALCRYSTALLIZER_USAGE_PER_TICK, "concrete_regular")
				//
				.addFluidTagInput(VoltaicTags.Fluids.CONCRETE, 1000)
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

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipies.CHEMICAL_CRYSTALIZER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_2_ITEM, modID, "chemical_crystallizer/" + name);
	}

}
