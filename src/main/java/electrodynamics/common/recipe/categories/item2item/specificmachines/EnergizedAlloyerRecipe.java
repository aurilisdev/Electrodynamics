package electrodynamics.common.recipe.categories.item2item.specificmachines;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.registers.ElectrodynamicsRecipies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class EnergizedAlloyerRecipe extends Item2ItemRecipe {

    public static final String RECIPE_GROUP = "energized_alloyer_recipe";
    public static final ResourceLocation RECIPE_ID = Electrodynamics.rl(RECIPE_GROUP);

    public EnergizedAlloyerRecipe(ResourceLocation group, List<CountableIngredient> inputs, ItemStack output,
	    double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts,
	    List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
	super(group, inputs, output, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return ElectrodynamicsRecipies.ENERGIZED_ALLOYER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return ElectrodynamicsRecipies.ENERGIZED_ALLOYER_TYPE.get();
    }

}
