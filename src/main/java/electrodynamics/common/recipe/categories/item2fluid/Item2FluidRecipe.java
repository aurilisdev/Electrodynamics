package electrodynamics.common.recipe.categories.item2fluid;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public abstract class Item2FluidRecipe extends AbstractMaterialRecipe {

	private CountableIngredient[] inputItems;
	private FluidStack outputFluid;

	public Item2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] itemInputs, FluidStack fluidOutput, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, ProbableGas[] gasBiproducts) {
		super(recipeID, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
		inputItems = itemInputs;
		outputFluid = fluidOutput;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		Pair<List<Integer>, Boolean> pair = areItemsValid(getCountedIngredients(), ((ComponentInventory) pr.getHolder().getComponent(IComponentType.Inventory)).getInputsForProcessor(pr.getProcessorNumber()));
		if (pair.getSecond()) {
			setItemArrangement(pr.getProcessorNumber(), pair.getFirst());
			return true;
		}
		return false;
	}

	@Override
	public FluidStack getFluidRecipeOutput() {
		return outputFluid;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		for (Ingredient ing : inputItems) {
			list.add(ing);
		}
		return list;
	}

	public List<CountableIngredient> getCountedIngredients() {
		List<CountableIngredient> list = new ArrayList<>();
		for (CountableIngredient ing : inputItems) {
			list.add(ing);
		}
		return list;
	}

	@Override
	public List<FluidIngredient> getFluidIngredients() {
		return new ArrayList<>();
	}

}
