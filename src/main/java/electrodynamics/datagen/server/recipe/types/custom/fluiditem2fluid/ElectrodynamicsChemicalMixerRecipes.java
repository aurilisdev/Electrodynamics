package electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeFluidOutput;

public class ElectrodynamicsChemicalMixerRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALMIXER_USAGE_PER_TICK = 400.0;
	public static int CHEMICALMIXER_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsChemicalMixerRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsChemicalMixerRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_HYDRAULIC.get(), 1000), 0, 200, 400.0, "hydraulic_fluid")
				//
				.addFluidTagInput(VoltaicTags.Fluids.ETHANOL, 500)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SILICA, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_CLAY.get(), 1000), 0, 200, 400.0, "liquid_clay")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemStackInput(new ItemStack(Items.DIRT))
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.molybdenum), 250), 0, 200, 400.0, "molybdenum_from_carrots")
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 100)
				//
				.addItemTagInput(Tags.Items.CROPS_CARROT, 6)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.molybdenum), 250), 0, 200, 400.0, "molybdenum_from_potatos")
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 100)
				//
				.addItemTagInput(Tags.Items.CROPS_POTATO, 4)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.molybdenum), 250), 0, 200, 400.0, "molybdenum_from_wheat")
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 100)
				//
				.addItemTagInput(Tags.Items.CROPS_WHEAT, 5)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_POLYETHYLENE.get(), 1000), 0, 200, 400.0, "polyethylene")
				//
				.addFluidTagInput(VoltaicTags.Fluids.ETHANOL, 1000)
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_CHROMIUMDISILICIDE, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_SULFURICACID.get(), 2500), 0, 200, 400.0, "sulfuric_acid")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_TRISULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_CONCRETE.get(), 5000), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "concrete_slurry")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEM_CONCRETEMIX.get()))
				//
				.complete(consumer);

	}

	public FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeFluidOutput.of(ElectrodynamicsRecipies.CHEMICAL_MIXER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_FLUID, modID, "chemical_mixer/" + name);
	}

}
