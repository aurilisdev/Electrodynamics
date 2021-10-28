package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PsuedoO2ORecipe extends O2ORecipe {

    public Ingredient INPUT;
    public ItemStack OUTPUT;

    public PsuedoO2ORecipe(ItemStack input, ItemStack output) {
	super(null, null, null);
	INPUT = Ingredient.of(input);
	OUTPUT = output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return null;
    }

    @Override
    public RecipeType<?> getType() {
	return null;
    }

}
