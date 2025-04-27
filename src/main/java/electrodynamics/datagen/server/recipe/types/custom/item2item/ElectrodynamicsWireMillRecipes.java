package electrodynamics.datagen.server.recipe.types.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class ElectrodynamicsWireMillRecipes extends AbstractRecipeGenerator {

	public static double WIREMILL_USAGE_PER_TICK = 125.0;
	public static int WIREMILL_REQUIRED_TICKS = 200;

	private final String modID;

	public ElectrodynamicsWireMillRecipes(String modID) {
		this.modID = modID;
	}

	public ElectrodynamicsWireMillRecipes() {
		this(Electrodynamics.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper)), 0.1F, 200, 125.0, "copper_wire_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_COPPER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.copper)), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold)), 0.2F, 200, 125.0, "gold_wire_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_GOLD, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.GOLD_NUGGET), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.iron)), 0.1F, 200, 125.0, "iron_wire_from_ingot")
				//
				.addItemTagInput(Tags.Items.INGOTS_IRON, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(Items.IRON_NUGGET), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.silver)), 0.1F, 200, 125.0, "silver_wire_from_ingot")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_SILVER, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.silver)), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.superconductive)), 0.1F, 200, 125.0, "superconductive_wire_from_ingot")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_SUPERCONDUCTIVE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.superconductive)), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.tin)), 0.1F, 200, 125.0, "tin_wire_from_ingot")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_TIN, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(ElectrodynamicsItems.ITEMS_NUGGET.getValue(SubtypeNugget.tin)), 1))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.ITEM_TITANIUM_COIL.get()), 0.1F, 200, 125.0, "titanium_coil")
				//
				.addItemTagInput(VoltaicTags.Items.INGOT_TITANIUM, 9)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(ElectrodynamicsRecipies.WIRE_MILL_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "wire_mill/" + name);
	}

}
