package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class MineralCrusher extends AbstractRecipeGenerator {

	public static double MINERALCRUSHER_USAGE_PER_TICK = 450.0;
	public static int MINERALCRUSHER_REQUIRED_TICKS = 200;
	
	private final String modID;

	public MineralCrusher(String modID) {
		this.modID = modID;
	}

	public MineralCrusher() {
		this(References.ID);
	}
	
	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		for (SubtypePlate plate : SubtypePlate.values()) {
			newRecipe(new ItemStack(PLATES[plate.ordinal()]), 0.1F, 200, 450.0, "plate_" + plate.name() + "_from_ingot")
					//
					.addItemTagInput(plate.sourceIngot, 1)
					//
					.complete(consumer);
		}

		for (SubtypeCrystal crystal : SubtypeCrystal.values()) {
			if (crystal.crushedItem != null && crystal != SubtypeCrystal.halite) {
				newRecipe(new ItemStack(crystal.crushedItem.get()), 0.0F, 200, 450.0, "imp_dust_" + crystal.name() + "_from_crystal")
						//
						.addItemStackInput(new ItemStack(CRYSTALS[crystal.ordinal()]))
						//
						.addItemBiproduct(new ProbableItem(OXIDES[SubtypeOxide.trisulfur.ordinal()], 1, 0.19))
						//
						.complete(consumer);
			}

		}

		newRecipe(new ItemStack(DUSTS[SubtypeDust.salt.ordinal()], 1), 0.1F, 200, 450.0, "salt_from_halite_crystal")
				//
				.addItemStackInput(new ItemStack(CRYSTALS[SubtypeCrystal.halite.ordinal()]))
				//
				.complete(consumer);

		for (SubtypeRawOre raw : SubtypeRawOre.values()) {
			if (raw.crushedItem != null) {
				if (raw == SubtypeRawOre.titanium || raw == SubtypeRawOre.chromium) {
					newRecipe(new ItemStack(raw.crushedItem.get(), 3), 0.5F, 200, 450.0, "oxide_" + raw.name() + "_from_raw_ore")
							//
							.addItemTagInput(raw.tag, 3)
							//
							.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.iron), 1, 0.3))
							//
							.complete(consumer);
				} else {
					newRecipe(new ItemStack(raw.crushedItem.get(), 3), 0.3F, 200, 450.0, "imp_dust_" + raw.name() + "_from_raw_ore")
							//
							.addItemTagInput(raw.tag, 3)
							//
							.complete(consumer);
				}

			}
		}

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.chromite.ordinal()], 3), 0.3F, 200, 450.0, "oxide_chromite_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_CHROMIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.iron), 1, 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.copper.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_copper_from_ore")
				//
				.addItemTagInput(ItemTags.COPPER_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.gold), 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.gold.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_gold_from_ore")
				//
				.addItemTagInput(ItemTags.GOLD_ORES, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.silver), 1, 0.2))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.iron.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_iron_from_ore")
				//
				.addItemTagInput(ItemTags.IRON_ORES, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.lead.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_lead_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_LEAD, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.silver), 1, 0.4))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.molybdenum.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_molybdenum_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_MOLYBDENUM, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeDust.sulfur), 1, 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.netherite.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_netherite_from_ore")
				//
				.addItemTagInput(Tags.Items.ORES_NETHERITE_SCRAP, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.COAL, 1, 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.silver.ordinal()], 3), 0.2F, 200, 450.0, "imp_dust_silver_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SILVER, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.gold), 1, 0.1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.tin.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_tin_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_TIN, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.QUARTZ, 1, 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(IMPURE_DUSTS[SubtypeImpureDust.vanadium.ordinal()], 3), 0.3F, 200, 450.0, "imp_dust_vanadium_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_VANADIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeDust.lead), 1, 0.2))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.niter.ordinal()], 4), 0.1F, 200, 450.0, "niter_dust_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.sulfur.ordinal()], 4), 0.1F, 200, 450.0, "sulfur_dust_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_SULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.dititanium.ordinal()], 3), 0.5F, 200, 450.0, "oxide_titanium_from_ore")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_TITANIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.getItem(SubtypeImpureDust.iron), 1, 0.3))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEPLATING.get(), 1), 1F, 200, 450.0, "composite_plate")
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING.get()))
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.obsidian.ordinal()], 2), 0.1F, 200, 450.0, "dust_obsidian_from_obsidian")
				//
				.addItemTagInput(Tags.Items.OBSIDIAN, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.FLINT, 1), 0.1F, 200, 450.0, "flint_from_gravel")
				//
				.addItemTagInput(Tags.Items.GRAVEL, 1)
				//
				.addItemBiproduct(new ProbableItem(Items.SAND, 1, 0.2))
				//
				.complete(consumer);

	}

	private FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.MINERAL_CRUSHER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "mineral_crusher/" + name);
	}

}
