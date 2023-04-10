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
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsMineralGrinderRecipes extends AbstractRecipeGenerator {

	public static double MINERALGRINDER_USAGE_PER_TICK = 350.0;
	public static int MINERALGRINDER_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsMineralGrinderRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsMineralGrinderRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		for (SubtypeIngot ingot : SubtypeIngot.values()) {
			if (ingot.grindedDust != null) {
				newRecipe(new ItemStack(ingot.grindedDust.get()), 0, 200, 350.0, "dust_" + ingot.name() + "_from_ingot")
						//
						.addItemTagInput(ingot.tag, 1)
						//
						.complete(consumer);
			}

		}

		newRecipe(new ItemStack(DUSTS[SubtypeDust.iron.ordinal()]), 0, 200, 350.0, "dust_iron_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.gold.ordinal()]), 0, 200, 350.0, "dust_gold_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()]), 0, 200, 350.0, "dust_copper_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_COPPER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.netherite.ordinal()]), 0, 200, 350.0, "dust_netherite_from_scrap")
				//
				.addItemStackInput(new ItemStack(Items.NETHERITE_SCRAP))
				//
				.complete(consumer);

		for (SubtypeImpureDust dust : SubtypeImpureDust.values()) {
			newRecipe(new ItemStack(dust.grindedDust.get()), 0.1F, 200, 350.0, "dust_" + dust.name() + "_from_imp_dust")
					//
					.addItemTagInput(dust.tag, 1)
					//
					.complete(consumer);
		}

		for (SubtypeRawOre raw : SubtypeRawOre.values()) {
			if (raw.grindedItem != null) {
				newRecipe(new ItemStack(raw.grindedItem.get(), 2), 0.1F, 200, 350.0, "dust_" + raw.name() + "_from_raw_ore")
						//
						.addItemTagInput(raw.tag, 1)
						//
						.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
						//
						.complete(consumer);
			}
		}

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()], 2), 0.1F, 200, 350.0, "dust_copper_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_COPPER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.iron.ordinal()], 2), 0.1F, 200, 350.0, "dust_iron_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_IRON, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.gold.ordinal()], 2), 0.1F, 200, 350.0, "dust_gold_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_GOLD, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.copper.ordinal()], 2), 0.1F, 200, 350.0, "dust_copper_from_ore")
				//
				.addItemTagInput(ItemTags.COPPER_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.gold.ordinal()], 2), 0.5F, 200, 350.0, "dust_gold_from_ore")
				//
				.addItemTagInput(ItemTags.GOLD_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.iron.ordinal()], 2), 0.3F, 200, 350.0, "dust_iron_from_ore")
				//
				.addItemTagInput(ItemTags.IRON_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.lead.ordinal()], 2), 0.3F, 200, 350.0, "dust_lead_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_LEAD, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.molybdenum.ordinal()], 2), 0.3F, 200, 350.0, "dust_molybdenum_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_MOLYBDENUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.netherite.ordinal()], 2), 1F, 200, 350.0, "dust_netherite_from_ore")
				//
				.addItemTagInput(Tags.Items.ORES_NETHERITE_SCRAP, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.silver.ordinal()], 2), 0.5F, 200, 350.0, "dust_silver_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SILVER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.tin.ordinal()], 2), 0.1F, 200, 350.0, "dust_tin_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_TIN, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.vanadium.ordinal()], 2), 0.1F, 200, 350.0, "dust_vanadium_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_VANADIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COBBLESTONE), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.COAL, 3), 0.3F, 200, 350.0, "gem_coal_from_ore")
				//
				.addItemTagInput(ItemTags.COAL_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.DIAMOND), 0.05))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.DIAMOND, 3), 1.0F, 200, 350.0, "gem_diamond_from_ore")
				//
				.addItemTagInput(ItemTags.DIAMOND_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COAL), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.EMERALD, 3), 1F, 200, 350.0, "gem_emerald_from_ore")
				//
				.addItemTagInput(ItemTags.EMERALD_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.DIAMOND), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.LAPIS_LAZULI, 9), 0.4F, 200, 350.0, "gem_lapis_from_ore")
				//
				.addItemTagInput(ItemTags.LAPIS_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.DIAMOND), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.QUARTZ, 7), 0.7F, 200, 350.0, "gem_quartz_from_ore")
				//
				.addItemTagInput(Tags.Items.ORES_QUARTZ, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COAL), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.endereye.ordinal()], 2), 1F, 200, 350.0, "dust_ender_eye")
				//
				.addItemTagInput(Tags.Items.ENDER_PEARLS, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.niter.ordinal()], 3), 0.1F, 200, 350.0, "dust_niter_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(CRYSTALS[SubtypeCrystal.potassiumchloride.ordinal()], 3), 0.3F, 200, 350.0, "pot_chloride_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_POTASSIUMCHLORIDE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.REDSTONE, 6), 0.4F, 200, 350.0, "dust_redstone_from_ore")
				//
				.addItemTagInput(ItemTags.REDSTONE_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.DIAMOND), 0.05))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.sulfur.ordinal()], 3), 0.2F, 200, 350.0, "dust_sulfur_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SULFUR, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COAL), 0.05))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.COBBLESTONE, 1), 0.01F, 200, 350.0, "cobblestone_from_stone")
				//
				.addItemTagInput(Tags.Items.STONE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.GRAVEL), 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.GRAVEL, 1), 0.01F, 200, 350.0, "gravel_from_cobblestone")
				//
				.addItemTagInput(Tags.Items.COBBLESTONE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.SAND), 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.SAND, 1), 0.01F, 200, 350.0, "sand_from_gravel")
				//
				.addItemTagInput(Tags.Items.GRAVEL, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(CRYSTALS[SubtypeCrystal.halite.ordinal()], 3), 0.1F, 200, 350.0, "halite_cystal_from_halite_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SALT, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(RAW_ORES[SubtypeRawOre.uranium.ordinal()]), 0.1F, 200, 350.0, "raw_uranium_from_uranium_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_URANIUM, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(RAW_ORES[SubtypeRawOre.thorium.ordinal()]), 0.1F, 200, 350.0, "raw_thorium_from_thorium_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_THORIUM, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(RAW_ORES[SubtypeRawOre.fluorite.ordinal()]), 0.1F, 200, 350.0, "fluorite_crystal_from_fluorite_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_FLUORITE, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.MINERAL_GRINDER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "mineral_grinder/" + name);
	}

}
