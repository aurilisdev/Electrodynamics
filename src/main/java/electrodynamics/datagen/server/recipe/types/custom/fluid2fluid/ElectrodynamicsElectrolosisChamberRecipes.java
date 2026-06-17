package electrodynamics.datagen.server.recipe.types.custom.fluid2fluid;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeImpureMineralFluid;
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolosisChamberRecipe;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.Fluid2FluidBuilder;

public class ElectrodynamicsElectrolosisChamberRecipes extends AbstractRecipeGenerator {

    public final String modID;

    public ElectrodynamicsElectrolosisChamberRecipes(String modID) {
	this.modID = modID;
    }

    public ElectrodynamicsElectrolosisChamberRecipes() {
	this(Electrodynamics.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	for (SubtypeImpureMineralFluid impure : SubtypeImpureMineralFluid.values()) {
	    newRecipe(new FluidStack(impure.result.get(), 1), 0, 0, 0,
		    "impure_" + impure.name() + "fluid_to_pure_" + impure.name() + "_fluid", modID)
		    //
		    .addFluidStackInput(new FluidStack(ElectrodynamicsFluids.FLUIDS_IMPUREMINERAL.getValue(impure), 1))
		    //
		    .save(output);

	}

    }

    public Fluid2FluidBuilder<ElectrolosisChamberRecipe> newRecipe(FluidStack stack, float xp, int ticks,
	    double usagePerTick, String name, String group) {
	return new Fluid2FluidBuilder<>(ElectrolosisChamberRecipe::new, stack,
		BaseRecipeBuilder.RecipeCategory.FLUID_2_FLUID, modID, "electrolosis_chamber/" + name, group, xp, ticks,
		usagePerTick);
    }
}
