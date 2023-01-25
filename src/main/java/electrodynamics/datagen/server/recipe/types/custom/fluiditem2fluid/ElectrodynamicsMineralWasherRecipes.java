package electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.fluid.types.liquid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeFluidOutput;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class ElectrodynamicsMineralWasherRecipes extends AbstractRecipeGenerator {

	public static double MINERALWASHER_USAGE_PER_TICK = 400.0;
	public static int MINERALWASHER_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsMineralWasherRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsMineralWasherRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new FluidStack(Fluids.LAVA, 1500), 0, 200, 400.0, "lava_from_magma_block")
				//
				.addFluidTagInput(FluidTags.LAVA, 1000)
				//
				.addItemStackInput(new ItemStack(Items.MAGMA_BLOCK))
				//
				.complete(consumer);

		for (SubtypeSulfateFluid fluid : SubtypeSulfateFluid.values()) {
			if (fluid.source != null) {
				newRecipe(new FluidStack(ElectrodynamicsFluids.SUBTYPEFLUID_REGISTRY_MAP.get(fluid).get(), 1000), 0, 200, 400.0, "sulfate_" + fluid.name())
						//
						.addItemTagInput(fluid.source, 1)
						//
						.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
						//
						.complete(consumer);
			}
		}

	}

	public FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeFluidOutput.of(ElectrodynamicsRecipeInit.MINERAL_WASHER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_FLUID, modID, "mineral_washer/" + name);
	}

}
