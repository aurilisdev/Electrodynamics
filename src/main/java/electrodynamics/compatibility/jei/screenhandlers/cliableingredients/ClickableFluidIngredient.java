package electrodynamics.compatibility.jei.screenhandlers.cliableingredients;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.neoforge.NeoForgeTypes;
import net.minecraft.client.renderer.Rect2i;
import net.neoforged.neoforge.fluids.FluidStack;

public class ClickableFluidIngredient extends AbstractClickableIngredient<FluidStack> {

	private final FluidTypeIngredient typeIngredient;

	public ClickableFluidIngredient(Rect2i rect, FluidStack stack) {
		super(rect);
		typeIngredient = new FluidTypeIngredient(stack);
	}

	@Override
	public ITypedIngredient<FluidStack> getTypedIngredient() {
		return typeIngredient;
	}

	private static class FluidTypeIngredient implements ITypedIngredient<FluidStack> {

		private final FluidStack fluidStack;

		public FluidTypeIngredient(FluidStack fluidStack) {
			this.fluidStack = fluidStack;
		}

		@Override
		public IIngredientType<FluidStack> getType() {
			return NeoForgeTypes.FLUID_STACK;
		}

		@Override
		public FluidStack getIngredient() {
			return fluidStack;
		}

	}

}
