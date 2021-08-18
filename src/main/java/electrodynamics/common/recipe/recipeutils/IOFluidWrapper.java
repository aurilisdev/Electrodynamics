package electrodynamics.common.recipe.recipeutils;

public class IOFluidWrapper {
	
	private FluidIngredient FLUID;
	private boolean IS_INPUT;
	
	public IOFluidWrapper(FluidIngredient fluid, boolean isInput) {
		this.FLUID = fluid;
		this.IS_INPUT = isInput;
	}
	
	public FluidIngredient getFluidIngredient() {
		return FLUID;
	}
	
	public boolean getIsInput() {
		return IS_INPUT;
	}

}
