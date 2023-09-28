package electrodynamics.common.recipe.categories.fluid2item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class Fluid2ItemRecipe extends AbstractMaterialRecipe {

	private FluidIngredient[] inputFluids;
	private ItemStack outputItem;

	public Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient[] fluidInputs, ItemStack itemOutput, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, ProbableGas[] gasBiproducts) {
		super(recipeID, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
		inputFluids = fluidInputs;
		outputItem = itemOutput;
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
	public ItemStack getItemOutputNoAccess() {
		return outputItem;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		for (Ingredient ing : inputFluids) {
			list.add(ing);
		}
		return list;
	}

	@Override
	public List<FluidIngredient> getFluidIngredients() {
		List<FluidIngredient> list = new ArrayList<>();
		for (FluidIngredient ing : inputFluids) {
			list.add(ing);
		}
		return list;
	}

}
