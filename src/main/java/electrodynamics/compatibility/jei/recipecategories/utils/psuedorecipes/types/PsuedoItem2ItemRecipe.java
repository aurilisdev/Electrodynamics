package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PsuedoItem2ItemRecipe {

	public List<Ingredient> INPUTS;
	public ItemStack OUTPUT;

	public PsuedoItem2ItemRecipe(List<ItemStack> inputs, ItemStack output) {
		INPUTS = new ArrayList<>();
		for (ItemStack stack : inputs) {
			INPUTS.add(Ingredient.of(stack));
		}
		OUTPUT = output;
	}

}
