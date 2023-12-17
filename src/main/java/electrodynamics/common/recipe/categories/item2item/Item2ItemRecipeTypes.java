package electrodynamics.common.recipe.categories.item2item;

import electrodynamics.common.recipe.categories.item2item.specificmachines.EnergizedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.LatheRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralGrinderRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.OxidationFurnaceRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.ReinforcedAlloyerRecipe;
import electrodynamics.common.recipe.categories.item2item.specificmachines.WireMillRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class Item2ItemRecipeTypes {

	public static final IRecipeSerializer<WireMillRecipe> WIRE_MILL_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(WireMillRecipe.class);
	public static final IRecipeSerializer<MineralGrinderRecipe> MINERAL_CRUSHER_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(MineralGrinderRecipe.class);
	public static final IRecipeSerializer<MineralCrusherRecipe> MINERAL_GRINDER_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(MineralCrusherRecipe.class);
	public static final IRecipeSerializer<LatheRecipe> LATHE_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(LatheRecipe.class);
	public static final IRecipeSerializer<OxidationFurnaceRecipe> OXIDATION_FURNACE_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(OxidationFurnaceRecipe.class);
	public static final IRecipeSerializer<EnergizedAlloyerRecipe> ENERGIZED_ALLOYER_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(EnergizedAlloyerRecipe.class);
	public static final IRecipeSerializer<ReinforcedAlloyerRecipe> REINFORCED_ALLOYER_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(ReinforcedAlloyerRecipe.class);
}
