package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class ElectrodynamicsLatheRecipes extends AbstractRecipeGenerator {

	public static int LATHE_REQUIRED_TICKS = 200;
	public static double LATHE_USAGE_PER_TICK = 350.0;

	private final String modID;

	public ElectrodynamicsLatheRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsLatheRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_ROD.getValue(SubtypeRod.hslasteel), 1), 0.1F, 200, 350.0, "hsla_steel_rod")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_HSLASTEEL, 2)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.hslasteel), 2), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_ROD.getValue(SubtypeRod.stainlesssteel), 1), 0.1F, 200, 350.0, "stainless_steel_rod")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_STAINLESSSTEEL, 2)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.stainlesssteel), 2), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_ROD.getValue(SubtypeRod.steel), 1), 0.1F, 200, 350.0, "steel_rod")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_STEEL, 2)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.steel), 2), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_ROD.getValue(SubtypeRod.titaniumcarbide), 1), 0.1F, 200, 350.0, "titanium_carbide_rod")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_TITANIUMCARBIDE, 2)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.titaniumcarbide), 2), 1))
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipies.LATHE_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "lathe/" + name);
	}

}
