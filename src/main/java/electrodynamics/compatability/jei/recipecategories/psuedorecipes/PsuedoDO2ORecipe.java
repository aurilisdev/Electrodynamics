package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PsuedoDO2ORecipe extends DO2ORecipe {

    public Ingredient INPUT_1;
    public Ingredient INPUT_2;
    public ItemStack OUTPUT;

    public PsuedoDO2ORecipe(ItemStack input1, ItemStack input2, ItemStack output) {
	super(null, null, null, null);
	INPUT_1 = Ingredient.of(input1);
	INPUT_2 = Ingredient.of(input2);
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
