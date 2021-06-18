package electrodynamics.compatability.jei.recipecategories.psuedorecipes;

import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;

public class PsuedoO2ORecipe extends O2ORecipe{
	
	public Ingredient INPUT;
	public ItemStack OUTPUT;
	
	public PsuedoO2ORecipe(ItemStack input, ItemStack output) {
		super(null,null,null);
		INPUT = Ingredient.fromStacks(input);
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
