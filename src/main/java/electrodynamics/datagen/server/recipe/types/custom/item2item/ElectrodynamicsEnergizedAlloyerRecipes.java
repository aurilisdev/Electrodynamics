package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsEnergizedAlloyerRecipes extends AbstractRecipeGenerator {

	public static int ENERGIZEDALLOYER_REQUIRED_TICKS = 50;
	public static double ENERGIZEDALLOYER_USAGE_PER_TICK = 50.0;

	private final String modID;

	public ElectrodynamicsEnergizedAlloyerRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsEnergizedAlloyerRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new ItemStack(CUSTOM_GLASS[SubtypeGlass.aluminum.ordinal()], 4), 0.5F, 50, 50.0, "aluminum_glass")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_ALUMINUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.bronze.ordinal()], 5), 0.35F, 50, 50.0, "ingot_bronze")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_TIN, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_COPPER, 4)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.NETHERITE_INGOT, 2), 1.0F, 50, 50.0, "ingot_netherite")
				//
				.addItemStackInput(new ItemStack(Items.NETHERITE_SCRAP))
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.stainlesssteel.ordinal()], 4), 0.4F, 50, 50.0, "ingot_stainless_steel")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_CHROMIUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_STEEL, 3)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.steel.ordinal()], 3), 0.3F, 50, 50.0, "ingot_steel")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 2)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.COAL_COKE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.vanadiumsteel.ordinal()], 8), 0.3F, 50, 50.0, "ingot_vanadium_steel")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_STEEL, 8)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_VANADIUM, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "energized_alloyer/" + name);
	}

}
