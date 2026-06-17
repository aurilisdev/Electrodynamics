package electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.FluidItem2FluidBuilder;

public class ElectrodynamicsChemicalMixerRecipes extends AbstractRecipeGenerator {

    public static double CHEMICALMIXER_USAGE_PER_TICK = 400.0;
    public static int CHEMICALMIXER_REQUIRED_TICKS = 200;

    public final String modID;

    public ElectrodynamicsChemicalMixerRecipes(String modID) {
	this.modID = modID;
    }

    public ElectrodynamicsChemicalMixerRecipes() {
	this(Electrodynamics.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_HYDRAULIC, 1000), 0, 200, 400.0, "hydraulic_fluid", modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.ETHANOL, 500)
		//
		.addItemTagInput(VoltaicTags.Items.DUST_SILICA, 1)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_HYDROFLUORICACID, 1000), 0, 200, 400.0,
		"hydrofluoric_acid", modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 1000)
		//
		.addItemTagInput(VoltaicTags.Items.RAW_ORE_FLUORITE, 1)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_CLAY, 1000), 0, 200, 400.0, "liquid_clay", modID)
		//
		.addFluidTagInput(FluidTags.WATER, 1000)
		//
		.addItemTagInput(ItemTags.DIRT, 1)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.molybdenum), 250), 0,
		200, 400.0, "molybdenum_from_carrots", modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 100)
		//
		.addItemTagInput(Tags.Items.CROPS_CARROT, 6)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.molybdenum), 250), 0,
		200, 400.0, "molybdenum_from_potatos", modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 100)
		//
		.addItemTagInput(Tags.Items.CROPS_POTATO, 4)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.molybdenum), 250), 0,
		200, 400.0, "molybdenum_from_wheat", modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 100)
		//
		.addItemTagInput(Tags.Items.CROPS_WHEAT, 5)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_POLYETHYLENE, 1000), 0, 200, 400.0, "polyethylene", modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.ETHANOL, 1000)
		//
		.addItemTagInput(VoltaicTags.Items.OXIDE_CHROMIUMDISILICIDE, 1)
		//
		.save(output);

	newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_SULFURICACID, 2500), 0, 200, 400.0, "sulfuric_acid", modID)
		//
		.addFluidTagInput(FluidTags.WATER, 1000)
		//
		.addItemTagInput(VoltaicTags.Items.OXIDE_TRISULFUR, 1)
		//
		.save(output);

    }

    public FluidItem2FluidBuilder<ChemicalMixerRecipe> newRecipe(FluidStack stack, float xp, int ticks,
	    double usagePerTick, String name, String group) {
	return new FluidItem2FluidBuilder<>(ChemicalMixerRecipe::new, stack,
		BaseRecipeBuilder.RecipeCategory.FLUID_ITEM_2_FLUID, modID, "chemical_mixer/" + name, group, xp, ticks,
		usagePerTick);
    }

}
