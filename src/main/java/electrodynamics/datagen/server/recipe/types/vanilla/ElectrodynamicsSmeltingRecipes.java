package electrodynamics.datagen.server.recipe.types.vanilla;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.CustomCookingRecipe;

public class ElectrodynamicsSmeltingRecipes extends AbstractRecipeGenerator {

	private static final String SMELTING_LOC = "smelting/";
	private static final String BLASTING_LOC = "blasting/";

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		for (SubtypeDust dust : SubtypeDust.values()) {

			if (dust.smeltedItem != null) {
				CustomCookingRecipe.smeltingRecipe(dust.smeltedItem.get(), 0, dust.smeltTime)
						//
						.input(dust.tag)
						//
						.complete(Electrodynamics.ID, SMELTING_LOC + dust.name() + "_ingot_from_dust", consumer);

				CustomCookingRecipe.blastingRecipe(dust.smeltedItem.get(), 0, dust.smeltTime / 2)
						//
						.input(dust.tag)
						//
						.complete(Electrodynamics.ID, BLASTING_LOC + dust.name() + "_ingot_from_dust", consumer);
			}

		}

		for (SubtypeOre ore : SubtypeOre.values()) {
			if (ore.smeltingItem != null) {
				CustomCookingRecipe.smeltingRecipe(ore.smeltingItem.get(), (float) ore.smeltingXp, ore.smeltingTime)
						//
						.input(ore.itemTag)
						//
						.complete(Electrodynamics.ID, SMELTING_LOC + ore.name() + "_ingot_from_ore", consumer);

				CustomCookingRecipe.blastingRecipe(ore.smeltingItem.get(), (float) ore.smeltingXp, ore.smeltingTime / 2)
						//
						.input(ore.itemTag)
						//
						.complete(Electrodynamics.ID, BLASTING_LOC + ore.name() + "_ingot_from_ore", consumer);
			}
		}

		// Coal Coke

		CustomCookingRecipe.smeltingRecipe(ElectrodynamicsItems.ITEM_COAL_COKE.get(), 0.1F, 200)
				//
				.input(ItemTags.COALS)
				//
				.complete(Electrodynamics.ID, SMELTING_LOC + "coal_coke", consumer);

		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEM_COAL_COKE.get(), 0.1F, 100)
				//
				.input(ItemTags.COALS)
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "coal_coke", consumer);

		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.cooked), 0.1F, 300)
				//
				.input(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.wet))
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "cooked_ceramic", consumer);

		// Clear Glass

		CustomCookingRecipe.smeltingRecipe(ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear), 0.1F, 200)
				//
				.input(VoltaicTags.Items.DUST_SILICA)
				//
				.complete(Electrodynamics.ID, SMELTING_LOC + "clear_glass", consumer);

		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear), 0.1F, 100)
				//
				.input(VoltaicTags.Items.DUST_SILICA)
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "clear_glass", consumer);

		// Steel Ingot
		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 0.1F, 100)
				//
				.input(Tags.Items.INGOTS_IRON)
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "steel_ingot_from_iron_ingot", consumer);

		// Tin Raw Ore

		CustomCookingRecipe.smeltingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin), 0.1F, 200)
				//
				.input(VoltaicTags.Items.RAW_ORE_TIN)
				//
				.complete(Electrodynamics.ID, SMELTING_LOC + "tin_ingot_from_raw_ore", consumer);

		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin), 0.1F, 100)
				//
				.input(VoltaicTags.Items.RAW_ORE_TIN)
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "tin_ingot_from_raw_ore", consumer);

		// Silver Raw Ore

		CustomCookingRecipe.smeltingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver), 0.7F, 200)
				//
				.input(VoltaicTags.Items.RAW_ORE_SILVER)
				//
				.complete(Electrodynamics.ID, SMELTING_LOC + "silver_ingot_from_raw_ore", consumer);

		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver), 0.7F, 100)
				//
				.input(VoltaicTags.Items.RAW_ORE_SILVER)
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "silver_ingot_from_raw_ore", consumer);

		// Lead Raw Ore

		CustomCookingRecipe.smeltingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead), 0.7F, 200)
				//
				.input(VoltaicTags.Items.RAW_ORE_LEAD)
				//
				.complete(Electrodynamics.ID, SMELTING_LOC + "lead_ingot_from_raw_ore", consumer);

		CustomCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead), 0.7F, 100)
				//
				.input(VoltaicTags.Items.RAW_ORE_LEAD)
				//
				.complete(Electrodynamics.ID, BLASTING_LOC + "lead_ingot_from_raw_ore", consumer);

	}

}
