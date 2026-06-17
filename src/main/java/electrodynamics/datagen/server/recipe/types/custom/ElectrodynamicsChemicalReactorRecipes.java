package electrodynamics.datagen.server.recipe.types.custom;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeCrudeMineralFluid;
import electrodynamics.common.fluid.subtype.SubtypeDirtyMineralFluid;
import electrodynamics.common.fluid.subtype.SubtypeRoyalMineralFluid;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.datagen.utils.ChemicalReactorBuilder;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasStack;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;

public class ElectrodynamicsChemicalReactorRecipes extends AbstractRecipeGenerator {

    public final String modID;

    public ElectrodynamicsChemicalReactorRecipes(String modID) {
	this.modID = modID;
    }

    public ElectrodynamicsChemicalReactorRecipes() {
	this(Electrodynamics.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	for (SubtypeSulfateFluid fluid : SubtypeSulfateFluid.values()) {
	    newRecipe(0, 200, 800.0, "pure_" + fluid.name() + "_from_" + fluid.name() + "_sulfate", modID)
		    //
		    .setFluidOutput(new FluidStack(fluid.result.get(), 200))
		    //
		    .addFluidTagInput(fluid.tag, 200)
		    //
		    .addFluidTagInput(FluidTags.WATER, 1000)
		    //
		    .addFluidBiproduct(
			    new ProbableFluid(new FluidStack(ElectrodynamicsFluids.FLUID_SULFURICACID.get(), 150), 1))
		    //
		    .save(output);
	}

	newRecipe(0, 200, 700, "hydrochloric_acid", modID)
		//
		.setFluidOutput(new FluidStack(ElectrodynamicsFluids.FLUID_HYDROCHLORICACID.get(), 500))
		//
		.addFluidTagInput(FluidTags.WATER, 1000)
		//
		.addItemTagInput(VoltaicTags.Items.DUST_SALT, 5)
		//
		.addGasTagInput(VoltaicTags.Gases.HYDROGEN, new BaseRecipeBuilder.GasIngWrapper(1000, 500, 8))
		//
		.save(output);

	newRecipe(0, 200, 1000, "ammonia", modID)
		//
		.setGasOutput(new GasStack(ElectrodynamicsGases.AMMONIA.value(), 1000, Gas.ROOM_TEMPERATURE,
			Gas.PRESSURE_AT_SEA_LEVEL))
		//
		.addFluidTagInput(FluidTags.WATER, 2000)
		//
		.addGasTagInput(VoltaicTags.Gases.NITROGEN, new BaseRecipeBuilder.GasIngWrapper(1000, 500, 4))
		//
		.addGasTagInput(VoltaicTags.Gases.HYDROGEN, new BaseRecipeBuilder.GasIngWrapper(1000, 500, 4))
		//
		.save(output);

	newRecipe(0, 200, 1000, "nitric_acid", modID)
		//
		.setFluidOutput(new FluidStack(ElectrodynamicsFluids.FLUID_NITRICACID.get(), 500))
		//
		.addFluidTagInput(FluidTags.WATER, 3000)
		//
		.addGasTagInput(VoltaicTags.Gases.AMMONIA, new BaseRecipeBuilder.GasIngWrapper(1000, 700, 4))
		//
		.save(output);
	newRecipe(0, 200, 500, "sulfur_trioxide_alternative", modID)
		//
		.setItemOutput(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.trisulfur)))
		//
		.addGasTagInput(VoltaicTags.Gases.SULFUR_DIOXIDE,
			new BaseRecipeBuilder.GasIngWrapper(1000, 373, Gas.PRESSURE_AT_SEA_LEVEL))
		//
		.addItemTagInput(VoltaicTags.Items.OXIDE_VANADIUM, 1)
		//
		.save(output);
	newRecipe(0, 200, 500, "aqua_regia", modID)
		//
		.setFluidOutput(new FluidStack(ElectrodynamicsFluids.FLUID_AQUAREGIA, 100))
		//
		.addFluidTagInput(VoltaicTags.Fluids.HYDROCHLORIC_ACID, 1000)
		//
		.addFluidTagInput(VoltaicTags.Fluids.NITRIC_ACID, 1000)
		//
		.save(output);
	newRecipe(0, 200, 200, "fertilizer", modID)
		//
		.setItemOutput(new ItemStack(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER, 16))
		//
		.addItemTagInput(VoltaicTags.Items.DUST_MOLYBDENUM, 2)
		//
		.addItemStackInput(new ItemStack(Items.BONE_MEAL))
		//
		.addGasTagInput(VoltaicTags.Gases.AMMONIA,
			new BaseRecipeBuilder.GasIngWrapper(100, Gas.ROOM_TEMPERATURE, 4))
		//
		.save(output);

	for (SubtypeRoyalMineralFluid fluid : SubtypeRoyalMineralFluid.values()) {
	    newRecipe(0, 100, 800.0, "crude_" + fluid.name() + "_from_royal_" + fluid.name(), modID)
		    //
		    .setFluidOutput(new FluidStack(fluid.result.get(), 200))
		    //
		    .addFluidStackInput(new FluidStack(ElectrodynamicsFluids.FLUIDS_ROYALMINERAL.getValue(fluid), 200))
		    //
		    .addFluidTagInput(FluidTags.WATER, 1000)
		    //
		    .addFluidBiproduct(
			    new ProbableFluid(new FluidStack(ElectrodynamicsFluids.FLUID_AQUAREGIA.get(), 50), 1))
		    //
		    .save(output);
	}

	for (SubtypeCrudeMineralFluid fluid : SubtypeCrudeMineralFluid.values()) {
	    newRecipe(0, 100, 600.0, "dirty_" + fluid.name() + "_from_crude_" + fluid.name(), modID)
		    //
		    .setFluidOutput(new FluidStack(fluid.result.get(), 200))
		    //
		    .addFluidStackInput(new FluidStack(ElectrodynamicsFluids.FLUIDS_CRUDEMINERAL.getValue(fluid), 200))
		    //
		    .addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 500)
		    //
		    .addFluidBiproduct(new ProbableFluid(new FluidStack(Fluids.WATER, 200), 0.25))
		    //
		    .save(output);
	}

	for (SubtypeDirtyMineralFluid fluid : SubtypeDirtyMineralFluid.values()) {
	    newRecipe(0, 100, 700.0, "impure_" + fluid.name() + "_from_dirty_" + fluid.name(), modID)
		    //
		    .setFluidOutput(new FluidStack(fluid.result.get(), 200))
		    //
		    .addFluidStackInput(new FluidStack(ElectrodynamicsFluids.FLUIDS_DIRTYMINERAL.getValue(fluid), 200))
		    //
		    .addFluidTagInput(FluidTags.WATER, 1000)
		    //
		    .addFluidBiproduct(
			    new ProbableFluid(new FluidStack(ElectrodynamicsFluids.FLUID_SULFURICACID, 500), 1))
		    //
		    .save(output);
	}

    }

    public ChemicalReactorBuilder newRecipe(float xp, int ticks, double usagePerTick, String name, String group) {
	return new ChemicalReactorBuilder(BaseRecipeBuilder.RecipeCategory.CHEMICAL_REACTOR, modID, name, group, xp,
		ticks, usagePerTick);
    }
}
