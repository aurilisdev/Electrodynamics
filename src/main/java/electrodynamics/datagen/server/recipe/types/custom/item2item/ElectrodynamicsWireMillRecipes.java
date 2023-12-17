package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;

public class ElectrodynamicsWireMillRecipes extends AbstractRecipeGenerator {

	public static double WIREMILL_USAGE_PER_TICK = 125.0;
	public static int WIREMILL_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsWireMillRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsWireMillRecipes() {
		this(References.ID);
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new ItemStack(WIRES[SubtypeWire.copper.ordinal()]), 0.1F, 200, 125.0, "copper_wire_from_ingot")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_COPPER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(WIRES[SubtypeWire.gold.ordinal()]), 0.2F, 200, 125.0, "gold_wire_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(WIRES[SubtypeWire.iron.ordinal()]), 0.1F, 200, 125.0, "iron_wire_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(WIRES[SubtypeWire.silver.ordinal()]), 0.1F, 200, 125.0, "silver_wire_from_ingot")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_SILVER, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(WIRES[SubtypeWire.superconductive.ordinal()]), 0.1F, 200, 125.0, "superconductive_wire_from_ingot")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_SUPERCONDUCTIVE, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(WIRES[SubtypeWire.tin.ordinal()]), 0.1F, 200, 125.0, "tin_wire_from_ingot")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_TIN, 1)
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_TITANIUM_COIL.get()), 0.1F, 200, 125.0, "titanium_coil")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.INGOT_TITANIUM, 9)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipeInit.WIRE_MILL_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "wire_mill/" + name);
	}

}
