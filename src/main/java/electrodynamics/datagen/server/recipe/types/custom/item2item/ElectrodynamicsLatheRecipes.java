package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

public class ElectrodynamicsLatheRecipes extends AbstractRecipeGenerator {

	public static int LATHE_REQUIRED_TICKS = 200;
	public static double LATHE_USAGE_PER_TICK = 350.0;

	private final String modID;

	public ElectrodynamicsLatheRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsLatheRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(RODS[SubtypeRod.hslasteel.ordinal()], 1), 0.1F, 200, 350.0, "hsla_steel_rod")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_HSLASTEEL, 2)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeNugget.hslasteel), 2, 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(RODS[SubtypeRod.stainlesssteel.ordinal()], 1), 0.1F, 200, 350.0, "stainless_steel_rod")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_STAINLESSSTEEL, 2)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeNugget.stainlesssteel), 2, 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(RODS[SubtypeRod.steel.ordinal()], 1), 0.1F, 200, 350.0, "steel_rod")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_STEEL, 2)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeNugget.steel), 2, 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(RODS[SubtypeRod.titaniumcarbide.ordinal()], 1), 0.1F, 200, 350.0, "titanium_carbide_rod")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_TITANIUMCARBIDE, 2)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeNugget.titaniumcarbide), 2, 1))
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.LATHE_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "lathe/" + name);
	}

}
