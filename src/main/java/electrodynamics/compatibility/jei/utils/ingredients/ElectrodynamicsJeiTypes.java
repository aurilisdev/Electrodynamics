package electrodynamics.compatibility.jei.utils.ingredients;

import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;

public class ElectrodynamicsJeiTypes {

	public static final IIngredientTypeWithSubtypes<Gas, GasStack> GAS_STACK = new IIngredientTypeWithSubtypes<>() {

		@Override
		public Class<? extends GasStack> getIngredientClass() {
			return GasStack.class;
		}

		@Override
		public Class<? extends Gas> getIngredientBaseClass() {
			return Gas.class;
		}

		@Override
		public Gas getBase(GasStack ingredient) {
			return ingredient.getGas();
		}
	};

}
