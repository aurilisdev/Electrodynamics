package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoFluid2GasRecipe {

	public List<FluidIngredient> inputs;
	public GasStack output;
	
	public PsuedoFluid2GasRecipe(List<FluidStack> inputs, GasStack output) {
		this.inputs = new ArrayList<>();
		for(FluidStack stack : inputs) {
			this.inputs.add(new FluidIngredient(stack));
		}
		
		this.output = output;
	}
	
}
