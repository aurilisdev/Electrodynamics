package electrodynamics.common.recipe.categories.o2o;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class O2ORecipe extends ElectrodynamicsRecipe {

    private CountableIngredient INPUT;
    private ItemStack OUTPUT;

    public O2ORecipe(ResourceLocation id, CountableIngredient input, ItemStack output) {
	super(id);
	INPUT = input;
	OUTPUT = output;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (INPUT.testStack(pr.getInput())) {
	    return true;
	}
	return false;
    }

    public boolean matchesRecipe(ItemStack stack) {
	if (INPUT.testStack(stack)) {
	    return true;
	}
	return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
	return OUTPUT;
    }

    @Override
    public ItemStack getResultItem() {
	return OUTPUT;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, INPUT);
    }

}
