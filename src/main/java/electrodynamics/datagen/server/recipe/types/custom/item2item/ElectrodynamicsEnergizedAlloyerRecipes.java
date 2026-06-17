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

public class ElectrodynamicsEnergizedAlloyerRecipes extends AbstractRecipeGenerator {

    public static int ENERGIZEDALLOYER_REQUIRED_TICKS = 50;
    public static double ENERGIZEDALLOYER_USAGE_PER_TICK = 50.0;

    private final String modID;

    public ElectrodynamicsEnergizedAlloyerRecipes(String modID) {
	this.modID = modID;
    }

    public ElectrodynamicsEnergizedAlloyerRecipes() {
	this(Electrodynamics.ID);
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer) {

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum), 4), 0.5F, 50,
		50.0, "aluminum_glass")
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_ALUMINUM, 1)
		//
		.addItemTagInput(VoltaicTags.Items.DUST_SALTPETER, 1)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.25))
		//
		.complete(consumer);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.bronze), 5), 0.35F, 50, 50.0,
		"ingot_bronze")
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_TIN, 1)
		//
		.addItemTagInput(Tags.Items.INGOTS_COPPER, 4)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
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

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.stainlesssteel), 32), 0.4F, 50,
		50.0, "ingot_stainless_steel")
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_CHROMIUM, 1)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_STEEL, 32)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
		//
		.complete(consumer);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 3), 0.3F, 50, 50.0,
		"ingot_steel")
		//
		.addItemTagInput(Tags.Items.INGOTS_IRON, 2)
		//
		.addItemTagInput(VoltaicTags.Items.COAL_COKE, 1)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
		//
		.complete(consumer);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.vanadiumsteel), 8), 0.3F, 50,
		50.0, "ingot_vanadium_steel")
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
	return FinishedRecipeItemOutput
		.of(ElectrodynamicsRecipies.ENERGIZED_ALLOYER_SERIALIZER.get(), stack, xp, ticks, usagePerTick)
		.name(RecipeCategory.ITEM_2_ITEM, modID, "energized_alloyer/" + name);
    }

}
