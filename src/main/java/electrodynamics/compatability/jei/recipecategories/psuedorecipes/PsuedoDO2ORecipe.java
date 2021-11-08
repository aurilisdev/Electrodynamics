package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PsuedoDO2ORecipe extends DO2ORecipe {

    public Ingredient ingredient1;
    public Ingredient ingredient2;
    public ItemStack output;

    public PsuedoDO2ORecipe(ItemStack input1, ItemStack input2, ItemStack output) {
	super(null, null, null, null);
	ingredient1 = Ingredient.of(input1);
	ingredient2 = Ingredient.of(input2);
	this.output = output;
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
