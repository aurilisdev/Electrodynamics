package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRawOre;
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

public class ElectrodynamicsMineralCrusherRecipes extends AbstractRecipeGenerator {

	public static double MINERALCRUSHER_USAGE_PER_TICK = 450.0;
	public static int MINERALCRUSHER_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsMineralCrusherRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsMineralCrusherRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		for (SubtypePlate plate : SubtypePlate.values()) {
			newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_PLATE.getValue(plate)), 0.1F, 200, 450.0, "plate_" + plate.name() + "_from_ingot")
					//
					.addItemTagInput(plate.sourceIngot, 1)
					//
					.complete(consumer);
		}

		for (SubtypeCrystal crystal : SubtypeCrystal.values()) {
			if (crystal.crushedItem != null && crystal != SubtypeCrystal.halite) {
				newRecipe(new ItemStack(crystal.crushedItem.get()), 0.0F, 200, 450.0, "imp_dust_" + crystal.name() + "_from_crystal")
						//
						.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(crystal)))
						//
						.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.trisulfur)), 0.05))
						//
						.complete(consumer);
			}

		}

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.salt), 1), 0.1F, 200, 450.0, "salt_from_halite_crystal")
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.halite)))
				//
				.complete(consumer);

		for (SubtypeRawOre raw : SubtypeRawOre.values()) {
			if (raw.crushedItem != null) {
				if (raw == SubtypeRawOre.titanium || raw == SubtypeRawOre.chromium) {
					newRecipe(new ItemStack(raw.crushedItem.get(), 3), 0.5F, 200, 450.0, "oxide_" + raw.name() + "_from_raw_ore")
							//
							.addItemTagInput(raw.tag, 1)
							//
							.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.iron)), 0.3))
							//
							.complete(consumer);
				} else {
					newRecipe(new ItemStack(raw.crushedItem.get(), 3), 0.3F, 200, 450.0, "imp_dust_" + raw.name() + "_from_raw_ore")
							//
							.addItemTagInput(raw.tag, 1)
							//
							.complete(consumer);
				}

			}
		}

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.iron), 3), 0.3F, 200, 450.0, "imp_dust_iron_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_IRON, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.gold), 3), 0.3F, 200, 450.0, "imp_dust_gold_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_GOLD, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.copper), 3), 0.3F, 200, 450.0, "imp_dust_copper_from_raw_ore")
				//
				.addItemTagInput(Tags.Items.RAW_MATERIALS_COPPER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.chromite), 3), 0.3F, 200, 450.0, "oxide_chromite_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_CHROMIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.iron)), 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.copper), 3), 0.3F, 200, 450.0, "imp_dust_copper_from_ore")
				//
				.addItemTagInput(ItemTags.COPPER_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.gold)), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.gold), 3), 0.3F, 200, 450.0, "imp_dust_gold_from_ore")
				//
				.addItemTagInput(ItemTags.GOLD_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.silver)), 0.2))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.iron), 3), 0.3F, 200, 450.0, "imp_dust_iron_from_ore")
				//
				.addItemTagInput(ItemTags.IRON_ORES, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.lead), 3), 0.3F, 200, 450.0, "imp_dust_lead_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_LEAD, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.silver)), 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.molybdenum), 3), 0.3F, 200, 450.0, "imp_dust_molybdenum_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_MOLYBDENUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.sulfur)), 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.netherite), 3), 0.3F, 200, 450.0, "imp_dust_netherite_from_ore")
				//
				.addItemTagInput(Tags.Items.ORES_NETHERITE_SCRAP, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.COAL), 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.silver), 3), 0.2F, 200, 450.0, "imp_dust_silver_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_SILVER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.gold)), 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.tin), 3), 0.3F, 200, 450.0, "imp_dust_tin_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_TIN, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.QUARTZ), 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.vanadium), 3), 0.3F, 200, 450.0, "imp_dust_vanadium_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_VANADIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.lead)), 0.2))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.niter), 4), 0.1F, 200, 450.0, "niter_dust_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.sulfur), 4), 0.1F, 200, 450.0, "sulfur_dust_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_SULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.dititanium), 3), 0.5F, 200, 450.0, "oxide_titanium_from_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_TITANIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(SubtypeImpureDust.iron)), 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get(), 1), 1F, 200, 450.0, "composite_plate")
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING.get()))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.obsidian), 2), 0.1F, 200, 450.0, "dust_obsidian_from_obsidian")
				//
				.addItemTagInput(Tags.Items.OBSIDIAN, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.FLINT, 1), 0.1F, 200, 450.0, "flint_from_gravel")
				//
				.addItemTagInput(Tags.Items.GRAVEL, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.SAND), 0.2))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.salt), 5), 0.1F, 200, 450.0, "salt_from_halite_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_SALT, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.fluorite), 2), 0.1F, 200, 450.0, "fluorite_crystal_from_fluorite_ore")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_FLUORITE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_SHEETPLASTIC.get(), 2), 0.1F, 200, 450.0, "plastic_sheet_from_plastic_fibers")
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEM_PLASTIC_FIBERS.get()))
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_OXIDE.getValue(SubtypeOxide.chromiumdisilicide)), 1.0))
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.BLAZE_POWDER, 10), 0, 100, 500, "blaze_powder_from_blaze_rod")
				//
				.addItemTagInput(Tags.Items.RODS_BLAZE, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipies.MINERAL_CRUSHER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "mineral_crusher/" + name);
	}

}
