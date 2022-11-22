package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class MineralGrinder extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		for (SubtypeIngot ingot : SubtypeIngot.values()) {
			if (ingot.grindedDust != null) {
				newRecipe(new ItemStack(ingot.grindedDust.get()), 0, "dust_" + ingot.name() + "_from_ingot")
						//
						.addItemTagInput(ingot.tag, 1)
						//
						.complete(consumer);
			}

		}

		newRecipe(new ItemStack(DUSTS[SubtypeDust.iron.ordinal()]), 0, "dust_iron_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.gold.ordinal()]), 0, "dust_gold_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()]), 0, "dust_copper_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_COPPER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()]), 0, "dust_netherite_from_scrap")
				//
				.addItemStackInput(new ItemStack(Items.NETHERITE_SCRAP))
				//
				.complete(consumer);

		for (SubtypeImpureDust dust : SubtypeImpureDust.values()) {
			newRecipe(new ItemStack(dust.grindedDust.get()), 0.1F, "dust_" + dust.name() + "_from_imp_dust")
					//
					.addItemTagInput(dust.tag, 1)
					//
					.complete(consumer);
		}

		for (SubtypeRawOre raw : SubtypeRawOre.values()) {
			if (raw.grindedItem != null) {
				newRecipe(new ItemStack(raw.grindedItem.get(), 2), 0.1F, "dust_" + raw.name() + "_from_raw_ore")
						//
						.addItemTagInput(raw.tag, 1)
						//
						.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
						//
						.complete(consumer);
			}
		}

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()], 2), 0.1F, "dust_copper_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_COPPER, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.iron.ordinal()], 2), 0.1F, "dust_iron_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_IRON, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.gold.ordinal()], 2), 0.1F, "dust_gold_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_GOLD, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()], 2), 0.1F, "dust_copper_from_ore")
				//
				.addItemTagInput(ItemTags.COPPER_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.gold.ordinal()], 2), 0.5F, "dust_gold_from_ore")
				//
				.addItemTagInput(ItemTags.GOLD_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.iron.ordinal()], 2), 0.3F, "dust_iron_from_ore")
				//
				.addItemTagInput(ItemTags.IRON_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.lead.ordinal()], 2), 0.3F, "dust_lead_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_LEAD, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.molybdenum.ordinal()], 2), 0.3F, "dust_molybdenum_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_MOLYBDENUM, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.netherite.ordinal()], 2), 1F, "dust_netherite_from_ore")
				//
				.addItemTagInput(Tags.Items.ORES_NETHERITE_SCRAP, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.silver.ordinal()], 2), 0.5F, "dust_silver_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SILVER, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.tin.ordinal()], 2), 0.1F, "dust_tin_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_TIN, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.vanadium.ordinal()], 2), 0.1F, "dust_vanadium_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_VANADIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COBBLESTONE, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.COAL, 3), 0.3F, "gem_coal_from_ore")
				//
				.addItemTagInput(ItemTags.COAL_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.DIAMOND, 1, 0.05))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.DIAMOND, 3), 1.0F, "gem_diamond_from_ore")
				//
				.addItemTagInput(ItemTags.DIAMOND_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COAL, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.EMERALD, 3), 1F, "gem_emerald_from_ore")
				//
				.addItemTagInput(ItemTags.EMERALD_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.DIAMOND, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.LAPIS_LAZULI, 9), 0.4F, "gem_lapis_from_ore")
				//
				.addItemTagInput(ItemTags.LAPIS_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.DIAMOND, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.QUARTZ, 7), 0.7F, "gem_quartz_from_ore")
				//
				.addItemTagInput(Tags.Items.ORES_QUARTZ, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COAL, 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.endereye.ordinal()], 2), 1F, "dust_ender_eye")
				//
				.addItemTagInput(Tags.Items.ENDER_PEARLS, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.niter.ordinal()], 3), 0.1F, "dust_niter_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(CRYSTALS[SubtypeCrystal.potassiumchloride.ordinal()], 3), 0.3F, "pot_chloride_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_POTASSIUMCHLORIDE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.REDSTONE, 6), 0.4F, "dust_redstone_from_ore")
				//
				.addItemTagInput(ItemTags.REDSTONE_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.DIAMOND, 1, 0.05))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.sulfur.ordinal()], 3), 0.2F, "dust_sulfur_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SULFUR, 2)
				//
				.addItemBiproduct(new ProbableItem(Items.COAL, 1, 0.05))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.COBBLESTONE, 1), 0.01F, "cobblestone_from_stone")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SULFUR, 2)
				//
				.addItemBiproduct(new ProbableItem(Items.GRAVEL, 1, 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.GRAVEL, 1), 0.01F, "gravel_from_cobblestone")
				//
				.addItemTagInput(Tags.Items.COBBLESTONE, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.SAND, 1, 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.SAND, 1), 0.01F, "sand_from_gravel")
				//
				.addItemTagInput(Tags.Items.GRAVEL, 1)
				//
				.complete(consumer);

	}

	private FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.MINERAL_GRINDER_SERIALIZER.get(), stack, xp)
				.name(RecipeCategory.ITEM_2_ITEM, References.ID, "mineral_grinder/" + name);
	}

}
