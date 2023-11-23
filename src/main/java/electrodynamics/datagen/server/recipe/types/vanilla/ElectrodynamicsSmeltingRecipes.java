package electrodynamics.datagen.server.recipe.types.vanilla;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.ElectrodynamicsCookingRecipe;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsSmeltingRecipes extends AbstractRecipeGenerator {

	private static final String SMELTING_LOC = "smelting/";
	private static final String BLASTING_LOC = "blasting/";

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		for (SubtypeDust dust : SubtypeDust.values()) {

			if (dust.smeltedItem != null) {
				ElectrodynamicsCookingRecipe.smeltingRecipe(dust.smeltedItem.get(), 0, dust.smeltTime)
						//
						.input(dust.tag)
						//
						.complete(References.ID, SMELTING_LOC + dust.name() + "_ingot_from_dust", consumer);

				ElectrodynamicsCookingRecipe.blastingRecipe(dust.smeltedItem.get(), 0, dust.smeltTime / 2)
						//
						.input(dust.tag)
						//
						.complete(References.ID, BLASTING_LOC + dust.name() + "_ingot_from_dust", consumer);
			}

		}

		for (SubtypeOre ore : SubtypeOre.values()) {
			if (ore.smeltingItem != null) {
				ElectrodynamicsCookingRecipe.smeltingRecipe(ore.smeltingItem.get(), (float) ore.smeltingXp, ore.smeltingTime)
						//
						.input(ore.itemTag)
						//
						.complete(References.ID, SMELTING_LOC + ore.name() + "_ingot_from_ore", consumer);

				ElectrodynamicsCookingRecipe.blastingRecipe(ore.smeltingItem.get(), (float) ore.smeltingXp, ore.smeltingTime / 2)
						//
						.input(ore.itemTag)
						//
						.complete(References.ID, BLASTING_LOC + ore.name() + "_ingot_from_ore", consumer);
			}
		}

		// Coal Coke

		ElectrodynamicsCookingRecipe.smeltingRecipe(ElectrodynamicsItems.ITEM_COAL_COKE.get(), 0.1F, 200)
				//
				.input(ItemTags.COALS)
				//
				.complete(References.ID, SMELTING_LOC + "coal_coke", consumer);

		ElectrodynamicsCookingRecipe.blastingRecipe(ElectrodynamicsItems.ITEM_COAL_COKE.get(), 0.1F, 100)
				//
				.input(ItemTags.COALS)
				//
				.complete(References.ID, BLASTING_LOC + "coal_coke", consumer);

		ElectrodynamicsCookingRecipe.blastingRecipe(CERAMICS[SubtypeCeramic.cooked.ordinal()], 0.1F, 300)
				//
				.input(CERAMICS[SubtypeCeramic.wet.ordinal()])
				//
				.complete(References.ID, BLASTING_LOC + "cooked_ceramic", consumer);

		// Clear Glass

		ElectrodynamicsCookingRecipe.smeltingRecipe(CUSTOM_GLASS[SubtypeGlass.clear.ordinal()], 0.1F, 200)
				//
				.input(ElectrodynamicsTags.Items.DUST_SILICA)
				//
				.complete(References.ID, SMELTING_LOC + "clear_glass", consumer);

		ElectrodynamicsCookingRecipe.blastingRecipe(CUSTOM_GLASS[SubtypeGlass.clear.ordinal()], 0.1F, 100)
				//
				.input(ElectrodynamicsTags.Items.DUST_SILICA)
				//
				.complete(References.ID, BLASTING_LOC + "clear_glass", consumer);

		// Steel Ingot
		ElectrodynamicsCookingRecipe.blastingRecipe(INGOTS[SubtypeIngot.steel.ordinal()], 0.1F, 100)
				//
				.input(Tags.Items.INGOTS_IRON)
				//
				.complete(References.ID, BLASTING_LOC + "steel_ingot_from_iron_ingot", consumer);

		// Tin Raw Ore

		ElectrodynamicsCookingRecipe.smeltingRecipe(INGOTS[SubtypeIngot.tin.ordinal()], 0.1F, 200)
				//
				.input(ElectrodynamicsTags.Items.RAW_ORE_TIN)
				//
				.complete(References.ID, SMELTING_LOC + "tin_ingot_from_raw_ore", consumer);

		ElectrodynamicsCookingRecipe.blastingRecipe(INGOTS[SubtypeIngot.tin.ordinal()], 0.1F, 100)
				//
				.input(ElectrodynamicsTags.Items.RAW_ORE_TIN)
				//
				.complete(References.ID, BLASTING_LOC + "tin_ingot_from_raw_ore", consumer);

		// Silver Raw Ore

		ElectrodynamicsCookingRecipe.smeltingRecipe(INGOTS[SubtypeIngot.silver.ordinal()], 0.7F, 200)
				//
				.input(ElectrodynamicsTags.Items.RAW_ORE_SILVER)
				//
				.complete(References.ID, SMELTING_LOC + "silver_ingot_from_raw_ore", consumer);

		ElectrodynamicsCookingRecipe.blastingRecipe(INGOTS[SubtypeIngot.silver.ordinal()], 0.7F, 100)
				//
				.input(ElectrodynamicsTags.Items.RAW_ORE_SILVER)
				//
				.complete(References.ID, BLASTING_LOC + "silver_ingot_from_raw_ore", consumer);

		// Lead Raw Ore

		ElectrodynamicsCookingRecipe.smeltingRecipe(INGOTS[SubtypeIngot.lead.ordinal()], 0.7F, 200)
				//
				.input(ElectrodynamicsTags.Items.RAW_ORE_LEAD)
				//
				.complete(References.ID, SMELTING_LOC + "lead_ingot_from_raw_ore", consumer);

		ElectrodynamicsCookingRecipe.blastingRecipe(INGOTS[SubtypeIngot.lead.ordinal()], 0.7F, 100)
				//
				.input(ElectrodynamicsTags.Items.RAW_ORE_LEAD)
				//
				.complete(References.ID, BLASTING_LOC + "lead_ingot_from_raw_ore", consumer);

	}

}
