package electrodynamics.datagen.server.recipe.types.custom.item2item;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.recipe.categories.item2item.specificmachines.EnergizedAlloyerRecipe;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.Item2ItemBuilder;

public class ElectrodynamicsEnergizedAlloyerRecipes extends AbstractRecipeGenerator {

    public static int ENERGIZEDALLOYER_REQUIRED_TICKS = 50;
    public static double ENERGIZEDALLOYER_USAGE_PER_TICK = 50.0;

    public final String modID;

    public ElectrodynamicsEnergizedAlloyerRecipes(String modID) {
	this.modID = modID;
    }

    public ElectrodynamicsEnergizedAlloyerRecipes() {
	this(Electrodynamics.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum), 4), 0.5F, 50,
		50.0, "aluminum_glass", modID)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_ALUMINUM, 1)
		//
		.addItemTagInput(VoltaicTags.Items.DUST_SALTPETER, 1)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.25))
		//
		.save(output);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.bronze), 5), 0.35F, 50, 50.0,
		"ingot_bronze", modID)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_TIN, 1)
		//
		.addItemTagInput(Tags.Items.INGOTS_COPPER, 4)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
		//
		.save(output);

	newRecipe(new ItemStack(Items.NETHERITE_INGOT, 2), 1.0F, 50, 50.0, "ingot_netherite", modID)
		//
		.addItemStackInput(new ItemStack(Items.NETHERITE_SCRAP))
		//
		.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
		//
		.save(output);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.0F, 50, 50.0, "slag", modID)
		//
		.addItemTagInput(Tags.Items.INGOTS, 1)
		//
		.addItemTagInput(Tags.Items.SANDS, 1)
		//
		.save(output);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.stainlesssteel), 32), 0.4F, 50,
		50.0, "ingot_stainless_steel", modID)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_CHROMIUM, 1)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_STEEL, 32)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
		//
		.save(output);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 3), 0.3F, 50, 50.0,
		"ingot_steel", modID)
		//
		.addItemTagInput(Tags.Items.INGOTS_IRON, 2)
		//
		.addItemTagInput(VoltaicTags.Items.COAL_COKE, 1)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.5))
		//
		.save(output);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.vanadiumsteel), 8), 0.3F, 50,
		50.0, "ingot_vanadium_steel", modID)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_STEEL, 8)
		//
		.addItemTagInput(VoltaicTags.Items.INGOT_VANADIUM, 1)
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get()), 0.75))
		//
		.save(output);

    }

    public Item2ItemBuilder<EnergizedAlloyerRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick,
	    String name, String group) {
	return new Item2ItemBuilder<>(EnergizedAlloyerRecipe::new, stack, BaseRecipeBuilder.RecipeCategory.ITEM_2_ITEM,
		modID, "energized_alloyer/" + name, group, xp, ticks, usagePerTick);
    }

}
