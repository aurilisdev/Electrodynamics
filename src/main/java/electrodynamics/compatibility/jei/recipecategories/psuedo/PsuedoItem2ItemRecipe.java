package electrodynamics.compatibility.jei.recipecategories.psuedo;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

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
