package electrodynamics.common.recipe.categories.fluid2gas;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class Fluid2GasRecipe extends AbstractMaterialRecipe {

	private FluidIngredient inputFluidIngredients[];
	private GasStack outputGasStack;

	public Fluid2GasRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluidIngredients, GasStack outputGasStack, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, ProbableGas[] gasBiproducts) {
		super(recipeID, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
		this.inputFluidIngredients = inputFluidIngredients;
		this.outputGasStack = outputGasStack;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		Pair<List<Integer>, Boolean> pair = areFluidsValid(getFluidIngredients(), pr.getHolder().<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks());
		if (pair.getSecond()) {
			setFluidArrangement(pair.getFirst());
			return true;
		}
		return false;
	}

	@Override
	public GasStack getGasRecipeOutput() {
		return outputGasStack;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ings = NonNullList.create();
		ings.addAll(Arrays.asList(inputFluidIngredients));
		return ings;
	}

	@Override
	public List<FluidIngredient> getFluidIngredients() {
		return Arrays.asList(inputFluidIngredients);
	}

}
