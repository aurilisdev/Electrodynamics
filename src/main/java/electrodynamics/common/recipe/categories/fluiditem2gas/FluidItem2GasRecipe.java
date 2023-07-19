package electrodynamics.common.recipe.categories.fluiditem2gas;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class FluidItem2GasRecipe extends AbstractMaterialRecipe {

	private FluidIngredient[] fluidIngredients;
	private CountableIngredient[] ingredients;
	private GasStack outputStack;

	public FluidItem2GasRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, GasStack outputGas, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, ProbableGas[] gasBiproducts) {
		super(recipeID, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputStack = outputGas;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		Pair<List<Integer>, Boolean> itemPair = areItemsValid(getCountedIngredients(), ((ComponentInventory) pr.getHolder().getComponent(ComponentType.Inventory)).getInputsForProcessor(pr.getProcessorNumber()));
		if (itemPair.getSecond()) {
			Pair<List<Integer>, Boolean> fluidPair = areFluidsValid(getFluidIngredients(), pr.getHolder().<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks());
			if (fluidPair.getSecond()) {
				setItemArrangement(pr.getProcessorNumber(), itemPair.getFirst());
				setFluidArrangement(fluidPair.getFirst());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<FluidIngredient> getFluidIngredients() {
		return Arrays.asList(fluidIngredients);
	}
	
	@Override
	public GasStack getGasRecipeOutput() {
		return outputStack;
	}
	
	public List<CountableIngredient> getCountedIngredients() {
		return Arrays.asList(ingredients);
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		for (Ingredient ing : ingredients) {
			list.add(ing);
		}
		for (Ingredient ing : fluidIngredients) {
			list.add(ing);
		}
		return list;
	}

}
