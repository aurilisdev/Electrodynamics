package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class ElectrodynamicsReinforcedAlloyerRecipes extends AbstractRecipeGenerator {

	public static int REINFORCEDALLOYER_REQUIRED_TICKS = 50;
	public static double REINFORCEDALLOYER_USAGE_PER_TICK = 50.0;

	private final String modID;

	public ElectrodynamicsReinforcedAlloyerRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsReinforcedAlloyerRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum), 5), 0.5F, 50, 50.0, "aluminum_glass")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_ALUMINUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALTPETER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.bronze), 7), 0.1F, 50, 50.0, "ingot_bronze")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_TIN, 1)
				//
				.addItemTagInput(Tags.Items.INGOTS_COPPER, 4)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear), 16), 0.1F, 50, 50.0, "clear_glass")
				//
				.addItemTagInput(VoltaicTags.Items.COAL_COKE, 1)
				//
				.addItemTagInput(Tags.Items.SAND, 16)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.hslasteel), 2), 0.3F, 50, 50.0, "ingot_hsla_steel")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_STAINLESSSTEEL, 1)
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_MOLYBDENUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.NETHERITE_INGOT, 2), 1.0F, 50, 50.0, "ingot_netherite")
				//
				.addItemStackInput(new ItemStack(Items.NETHERITE_SCRAP))
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.0F, 50, 50.0, "slag")
				//
				.addItemTagInput(Tags.Items.INGOTS, 1)
				//
				.addItemTagInput(Tags.Items.SAND, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.stainlesssteel), 34), 0.4F, 50, 50.0, "ingot_stainless_steel")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_CHROMIUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_STEEL, 34)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 4), 0.3F, 50, 50.0, "ingot_steel")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 2)
				//
				.addItemTagInput(VoltaicTags.Items.COAL_COKE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.superconductive), 4), 0.3F, 50, 50.0, "ingot_superconductive_endereye")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_SILVER, 5)
				//
				.addItemStackInput(new ItemStack(Items.ENDER_EYE, 4))
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.9))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.superconductive), 4), 0.3F, 50, 50.0, "ingot_superconductive_netherite")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_SILVER, 5)
				//
				.addItemTagInput(Tags.Items.INGOTS_NETHERITE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.9))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.titaniumcarbide), 4), 0.3F, 50, 50.0, "ingot_titanium_carbide")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_TITANIUM, 1)
				//
				.addItemTagInput(VoltaicTags.Items.COAL_COKE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.vanadiumsteel), 8), 0.3F, 50, 50.0, "vanadium_steel_ingot")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_STEEL, 8)
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_VANADIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipies.REINFORCED_ALLOYER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "reinforced_alloyer/" + name);
	}

}
