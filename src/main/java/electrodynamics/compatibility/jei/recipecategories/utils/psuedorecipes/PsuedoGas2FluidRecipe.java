package electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.GasIngredient;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoGas2FluidRecipe {

	public List<GasIngredient> inputs;
	public FluidStack output;
	
	public PsuedoGas2FluidRecipe(List<GasStack> inputs, FluidStack output) {
		this.inputs = new ArrayList<>();
		for(GasStack stack : inputs) {
			this.inputs.add(new GasIngredient(stack));
		}
		
		this.output = output;
	}
	
}
