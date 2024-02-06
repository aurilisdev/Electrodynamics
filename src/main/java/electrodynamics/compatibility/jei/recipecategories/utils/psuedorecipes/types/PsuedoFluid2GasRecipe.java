package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class PsuedoFluid2GasRecipe {

	public List<FluidIngredient> inputs;
	public GasStack output;

	public ItemStack inputBucket;
	public ItemStack outputCylinder;

	public PsuedoFluid2GasRecipe(List<FluidStack> inputs, GasStack output, ItemStack inputBucket, ItemStack outputCylinder) {
		this.inputs = new ArrayList<>();
		for (FluidStack stack : inputs) {
			this.inputs.add(new FluidIngredient(stack));
		}
		this.output = output;

		this.inputBucket = inputBucket;
		this.outputCylinder = outputCylinder;
	}

}
