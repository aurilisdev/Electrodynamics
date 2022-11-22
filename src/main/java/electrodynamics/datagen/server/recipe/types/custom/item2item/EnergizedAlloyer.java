package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class EnergizedAlloyer extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(CUSTOM_GLASS[SubtypeGlass.aluminum.ordinal()], 4), 0.5F, "aluminum_glass")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_ALUMINUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALTPETER, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.25))
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.bronze.ordinal()], 5), 0.35F, "ingot_bronze")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_TIN, 1)
				//
				.addItemTagInput(Tags.Items.INGOTS_COPPER, 4)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.NETHERITE_INGOT, 2), 1.0F, "ingot_netherite")
				//
				.addItemStackInput(new ItemStack(Items.NETHERITE_SCRAP))
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.SLAG.get()), 0.0F, "slag")
				//
				.addItemTagInput(Tags.Items.INGOTS, 1)
				//
				.addItemTagInput(Tags.Items.SAND, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.stainlesssteel.ordinal()], 4), 0.4F, "ingot_stainless_steel")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_CHROMIUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_STEEL, 3)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.steel.ordinal()], 3), 0.3F, "ingot_steel")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 2)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.COAL_COKE, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.vanadiumsteel.ordinal()], 8), 0.3F, "ingot_vanadium_steel")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_STEEL, 8)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_VANADIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.75))
				//
				.complete(consumer);

	}

	private FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.ENERGIZED_ALLOYER_SERIALIZER.get(), stack, xp)
				.name(RecipeCategory.ITEM_2_ITEM, References.ID, "energized_alloyer/" + name);
	}

}
