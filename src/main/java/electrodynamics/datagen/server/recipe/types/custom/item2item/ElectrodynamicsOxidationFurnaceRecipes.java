package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class ElectrodynamicsOxidationFurnaceRecipes extends AbstractRecipeGenerator {

	public static double OXIDATIONFURNACE_USAGE_PER_TICK = 350.0;
	public static int OXIDATIONFURNACE_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsOxidationFurnaceRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsOxidationFurnaceRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.aluminum), 3), 0.4F, 200, 350.0, "ingot_aluminum")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_ALUMINUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALTPETER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.calciumcarbonate), 2), 0.1F, 200, 350.0, "calcium_carbonate")
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_SODIUMCARBONATE, 1)
				//
				.addItemStackInput(new ItemStack(Items.BONE_MEAL))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.chromiumdisilicide)), 0.3F, 200, 350.0, "chromium_disilicide")
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_CHROMIUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SILICA, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.chromium)), 0.3F, 200, 350.0, "ingot_chromium")
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_CHROMIUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_CALCIUMCARBONATE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.disulfur), 1), 0.1F, 200, 350.0, "sulfur_dioxide")
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SULFUR, 1)
				//
				.addItemTagInput(ItemTags.COALS, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.silica), 3), 0.1F, 200, 350.0, "dust_silica")
				//
				.addItemTagInput(Tags.Items.SAND, 1)
				//
				.addItemTagInput(VoltaicTags.Items.COAL_COKE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.sodiumcarbonate), 1), 0.1F, 200, 350.0, "sodium_carbonate")
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALT, 1)
				//
				.addItemTagInput(ItemTags.COALS, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.sulfurdichloride), 1), 0.1F, 200, 350.0, "sulfur_dichloride")
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALT, 1)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.thionylchloride), 1), 0.1F, 200, 350.0, "thionyl_chloride")
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_SULFURDICHLORIDE, 1)
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_TRISULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.titanium), 1), 0.2F, 200, 350.0, "ingot_titanium")
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_DITITANIUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALT, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.trisulfur), 1), 0.1F, 200, 350.0, "sulfur_trioxide")
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_DISULFUR, 1)
				//
				.addItemTagInput(VoltaicTags.Items.OXIDE_VANADIUM, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.vanadium), 1), 0.1F, 200, 350.0, "vanadium_oxide")
				//
				.addItemTagInput(VoltaicTags.Items.DUST_VANADIUM, 1)
				//
				.addItemTagInput(ItemTags.COALS, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipies.OXIDATION_FURNACE_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "oxidation_furnace/" + name);
	}

}
