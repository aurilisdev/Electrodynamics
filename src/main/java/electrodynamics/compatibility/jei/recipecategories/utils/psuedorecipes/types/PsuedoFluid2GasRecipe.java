package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import voltaic.api.gas.GasStack;
import voltaic.common.recipe.recipeutils.FluidIngredient;

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
