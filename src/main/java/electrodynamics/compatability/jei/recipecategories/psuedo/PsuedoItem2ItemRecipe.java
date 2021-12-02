package electrodynamics.compatability.jei.recipecategories.psuedo;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PsuedoItem2ItemRecipe extends Item2ItemRecipe {

    public List<Ingredient> INPUTS;
    public ItemStack OUTPUT;

    public PsuedoItem2ItemRecipe(List<ItemStack> inputs, ItemStack output) {
		super(null, null, null);
		INPUTS = new ArrayList<>();
		for(ItemStack stack : inputs) {
			INPUTS.add(Ingredient.of(stack));
		}
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
