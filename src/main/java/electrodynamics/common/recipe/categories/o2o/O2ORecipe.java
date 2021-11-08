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
    private CountableIngredient countableIngredient;
    private ItemStack output;

    protected O2ORecipe(ResourceLocation id, CountableIngredient input, ItemStack output) {
	super(id);
	countableIngredient = input;
	this.output = output;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	return countableIngredient.testStack(pr.getInput());
    }

    public boolean matchesRecipe(ItemStack stack) {
	return countableIngredient.testStack(stack);
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
	return output;
    }

    @Override
    public ItemStack getResultItem() {
	return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, countableIngredient);
    }

}
