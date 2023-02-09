package electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeFluidOutput;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class ElectrodynamicsChemicalMixerRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALMIXER_USAGE_PER_TICK = 400.0;
	public static int CHEMICALMIXER_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsChemicalMixerRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsChemicalMixerRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidHydraulic, 1000), 0, 200, 400.0, "hydraulic_fluid")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.ETHANOL, 500)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SILICA, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidHydrogenFluoride, 1000), 0, 200, 400.0, "hydrofluoric_acid")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.RAW_ORE_FLUORITE, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidClay, 1000), 0, 200, 400.0, "liquid_clay")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(ItemTags.DIRT, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.SUBTYPEFLUID_REGISTRY_MAP.get(SubtypeSulfateFluid.molybdenum).get(), 250), 0, 200, 400.0, "molybdenum_from_carrots")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 100)
				//
				.addItemTagInput(Tags.Items.CROPS_CARROT, 6)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.SUBTYPEFLUID_REGISTRY_MAP.get(SubtypeSulfateFluid.molybdenum).get(), 250), 0, 200, 400.0, "molybdenum_from_potatos")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 100)
				//
				.addItemTagInput(Tags.Items.CROPS_POTATO, 4)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.SUBTYPEFLUID_REGISTRY_MAP.get(SubtypeSulfateFluid.molybdenum).get(), 250), 0, 200, 400.0, "molybdenum_from_wheat")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 100)
				//
				.addItemTagInput(Tags.Items.CROPS_WHEAT, 5)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidPolyethylene, 1000), 0, 200, 400.0, "polyethylene")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.ETHANOL, 1000)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_CHROMIUMDISILICIDE, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.fluidSulfuricAcid, 2500), 0, 200, 400.0, "sulfuric_acid")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_TRISULFUR, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeFluidOutput.of(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_FLUID, modID, "chemical_mixer/" + name);
	}

}
