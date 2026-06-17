package electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import voltaic.Voltaic;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeFluidOutput;

public class ElectrodynamicsMineralWasherRecipes extends AbstractRecipeGenerator {

    public static double MINERALWASHER_USAGE_PER_TICK = 400.0;
    public static int MINERALWASHER_REQUIRED_TICKS = 200;

    private final String modID;

    public ElectrodynamicsMineralWasherRecipes(String modID) {
	this.modID = modID;
    }

    public ElectrodynamicsMineralWasherRecipes() {
	this(Electrodynamics.ID);
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer) {

	Voltaic.LOGGER.info(Fluids.LAVA + " some text");

	newRecipe(new FluidStack(Fluids.LAVA, 1500), 0, 200, 400.0, "lava_from_magma_block")
		//
		.addFluidTagInput(FluidTags.LAVA, 1000)
		//
		.addItemStackInput(new ItemStack(Items.MAGMA_BLOCK))
		//
		.complete(consumer);

	for (SubtypeSulfateFluid fluid : SubtypeSulfateFluid.values()) {
	    if (fluid.source != null) {
		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(fluid), 1000), 0, 200, 400.0,
			"sulfate_" + fluid.name())
			//
			.addItemTagInput(fluid.source, 1)
			//
			.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 1000)
			//
			.complete(consumer);
	    }
	}

    }

    public FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, int ticks, double usagePerTick,
	    String name) {
	return FinishedRecipeFluidOutput
		.of(ElectrodynamicsRecipies.MINERAL_WASHER_SERIALIZER.get(), stack, xp, ticks, usagePerTick)
		.name(RecipeCategory.FLUID_ITEM_2_FLUID, modID, "mineral_washer/" + name);
    }

}
