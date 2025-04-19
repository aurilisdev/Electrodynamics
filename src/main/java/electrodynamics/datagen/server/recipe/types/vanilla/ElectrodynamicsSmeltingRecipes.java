package electrodynamics.datagen.server.recipe.types.vanilla;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.CustomCookingRecipe;

public class ElectrodynamicsSmeltingRecipes extends AbstractRecipeGenerator {

	private static final String SMELTING_LOC = "smelting/";
	private static final String BLASTING_LOC = "blasting/";

	@Override
	public void addRecipes(RecipeOutput output) {

		for (SubtypeDust dust : SubtypeDust.values()) {

			if (dust.smeltedItem != null) {
				CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + dust.name() + "_ingot_from_dust"), Electrodynamics.ID, dust.smeltedItem.get(), 0, dust.smeltTime)
						//
						.input(dust.tag)
						//
						.save(output);

				CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + dust.name() + "_ingot_from_dust"), Electrodynamics.ID, dust.smeltedItem.get(), 0, dust.smeltTime / 2)
						//
						.input(dust.tag)
						//
						.save(output);
			}

		}

		for (SubtypeOre ore : SubtypeOre.values()) {
			if (ore.smeltingItem != null) {
				CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + ore.name() + "_ingot_from_ore"), Electrodynamics.ID, ore.smeltingItem.get(), (float) ore.smeltingXp, ore.smeltingTime)
						//
						.input(ore.itemTag)
						//
						.save(output);

				CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + ore.name() + "_ingot_from_ore"), Electrodynamics.ID, ore.smeltingItem.get(), (float) ore.smeltingXp, ore.smeltingTime / 2)
						//
						.input(ore.itemTag)
						//
						.save(output);
			}
		}

		// Coal Coke

		CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + "coal_coke"), Electrodynamics.ID, ElectrodynamicsItems.ITEM_COAL_COKE.get(), 0.1F, 200)
				//
				.input(ItemTags.COALS)
				//
				.save(output);

		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "coal_coke"), Electrodynamics.ID, ElectrodynamicsItems.ITEM_COAL_COKE.get(), 0.1F, 100)
				//
				.input(ItemTags.COALS)
				//
				.save(output);

		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "cooked_ceramic"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.cooked), 0.1F, 300)
				//
				.input(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.wet))
				//
				.save(output);

		// Clear Glass

		CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + "clear_glass"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear), 0.1F, 200)
				//
				.input(VoltaicTags.Items.DUST_SILICA)
				//
				.save(output);

		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "clear_glass"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear), 0.1F, 100)
				//
				.input(VoltaicTags.Items.DUST_SILICA)
				//
				.save(output);

		// Steel Ingot
		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "steel_ingot_from_iron_ingot"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 0.1F, 100)
				//
				.input(Tags.Items.INGOTS_IRON)
				//
				.save(output);

		// Tin Raw Ore

		CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + "tin_ingot_from_raw_ore"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin), 0.1F, 200)
				//
				.input(VoltaicTags.Items.RAW_ORE_TIN)
				//
				.save(output);

		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "tin_ingot_from_raw_ore"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin), 0.1F, 100)
				//
				.input(VoltaicTags.Items.RAW_ORE_TIN)
				//
				.save(output);

		// Silver Raw Ore

		CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + "silver_ingot_from_raw_ore"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver), 0.7F, 200)
				//
				.input(VoltaicTags.Items.RAW_ORE_SILVER)
				//
				.save(output);

		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "silver_ingot_from_raw_ore"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver), 0.7F, 100)
				//
				.input(VoltaicTags.Items.RAW_ORE_SILVER)
				//
				.save(output);

		// Lead Raw Ore

		CustomCookingRecipe.smeltingRecipe(Electrodynamics.rl(SMELTING_LOC + "lead_ingot_from_raw_ore"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead), 0.7F, 200)
				//
				.input(VoltaicTags.Items.RAW_ORE_LEAD)
				//
				.save(output);

		CustomCookingRecipe.blastingRecipe(Electrodynamics.rl(BLASTING_LOC + "lead_ingot_from_raw_ore"), Electrodynamics.ID, ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead), 0.7F, 100)
				//
				.input(VoltaicTags.Items.RAW_ORE_LEAD)
				//
				.save(output);

	}

}
