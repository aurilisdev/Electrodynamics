package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;

public class PsuedoDO2ORecipe extends DO2ORecipe {

    public Ingredient INPUT_1;
    public Ingredient INPUT_2;
    public ItemStack OUTPUT;

    public PsuedoDO2ORecipe(ItemStack input1, ItemStack input2, ItemStack output) {
	super(null, null, null, null);
	INPUT_1 = Ingredient.fromStacks(input1);
	INPUT_2 = Ingredient.fromStacks(input2);
	OUTPUT = output;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return null;
    }

    @Override
    public IRecipeType<?> getType() {
	return null;
    }

}
