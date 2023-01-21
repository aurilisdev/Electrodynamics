package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeOxide;
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

public class OxidationFurnace extends AbstractRecipeGenerator {

	public static double OXIDATIONFURNACE_USAGE_PER_TICK = 350.0;
	public static int OXIDATIONFURNACE_REQUIRED_TICKS = 200;

	private final String modID;

	public OxidationFurnace(String modID) {
		this.modID = modID;
	}

	public OxidationFurnace() {
		this(References.ID);
	}
	
	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.aluminum.ordinal()], 3), 0.4F, 200, 350.0, "ingot_aluminum")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_ALUMINUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALTPETER, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.5))
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.calciumcarbonate.ordinal()], 2), 0.1F, 200, 350.0, "calcium_carbonate")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_SODIUMCARBONATE, 1)
				//
				.addItemStackInput(new ItemStack(Items.BONE_MEAL))
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.chromiumdisilicide.ordinal()]), 0.3F, 200, 350.0, "chromium_disilicide")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_CHROMIUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SILICA, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.chromium.ordinal()]), 0.3F, 200, 350.0, "ingot_chromium")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_CHROMIUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_CALCIUMCARBONATE, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.disulfur.ordinal()], 1), 0.1F, 200, 350.0, "sulfur_dioxide")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SULFUR, 1)
				//
				.addItemTagInput(ItemTags.COALS, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(DUSTS[SubtypeDust.silica.ordinal()], 3), 0.1F, 200, 350.0, "dust_silica")
				//
				.addItemTagInput(Tags.Items.SAND, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.COAL_COKE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.sodiumcarbonate.ordinal()], 1), 0.1F, 200, 350.0, "sodium_carbonate")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALT, 1)
				//
				.addItemTagInput(ItemTags.COALS, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.sulfurdichloride.ordinal()], 1), 0.1F, 200, 350.0, "sulfur_dichloride")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALT, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.thionylchloride.ordinal()], 1), 0.1F, 200, 350.0, "thionyl_chloride")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_SULFURDICHLORIDE, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_TRISULFUR, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(INGOTS[SubtypeIngot.titanium.ordinal()], 1), 0.2F, 200, 350.0, "ingot_titanium")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_DITITANIUM, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALT, 1)
				//
				.addItemBiproduct(new ProbableItem(ElectrodynamicsItems.SLAG.get(), 1, 0.75))
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.trisulfur.ordinal()], 1), 0.1F, 200, 350.0, "sulfur_trioxide")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_DISULFUR, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.OXIDE_VANADIUM, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(OXIDES[SubtypeOxide.thionylchloride.ordinal()], 1), 0.1F, 200, 350.0, "vanadium_oxide")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_VANADIUM, 1)
				//
				.addItemTagInput(ItemTags.COALS, 1)
				//
				.complete(consumer);

	}

	private FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.OXIDATION_FURNACE_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "oxidation_furnace/" + name);
	}

}
