package electrodynamics.common.recipe.recipeutils;

public class IOFluidWrapper {

	private FluidIngredient fluidIngredient;
	private boolean input;

	public IOFluidWrapper(FluidIngredient fluid, boolean isInput) {
		fluidIngredient = fluid;
		input = isInput;
	}

	public FluidIngredient getFluidIngredient() {
		return fluidIngredient;
	}

	public boolean getIsInput() {
		return input;
	}

}
