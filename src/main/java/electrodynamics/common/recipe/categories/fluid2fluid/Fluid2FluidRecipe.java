package electrodynamics.common.recipe.categories.fluid2fluid;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.recipeutils.AbstractFluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public abstract class Fluid2FluidRecipe extends AbstractFluidRecipe {

	private FluidIngredient[] inputFluidIngredients;
	private FluidStack outputFluidStack;

	protected Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid) {
		super(recipeID);
		inputFluidIngredients = inputFluids;
		outputFluidStack = outputFluid;
	}

	protected Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid, ProbableItem[] itemBiproducts) {
		super(recipeID, itemBiproducts);
		inputFluidIngredients = inputFluids;
		outputFluidStack = outputFluid;
	}

	protected Fluid2FluidRecipe(FluidIngredient[] inputFluids, FluidStack outputFluid, ProbableFluid[] fluidBiproducts, ResourceLocation recipeID) {
		super(fluidBiproducts, recipeID);
		inputFluidIngredients = inputFluids;
		outputFluidStack = outputFluid;
	}

	protected Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid, ProbableItem[] itemBiproducts,
			ProbableFluid[] fluidBiproducts) {
		super(recipeID, itemBiproducts, fluidBiproducts);
		inputFluidIngredients = inputFluids;
		outputFluidStack = outputFluid;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		Pair<List<Integer>, Boolean> pair = areFluidsValid(getFluidIngredients(),
				((AbstractFluidHandler<?>) pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
		if (Boolean.TRUE.equals(pair.getSecond())) {
			setFluidArrangement(pair.getFirst());
			return true;
		}
		return false;
	}

	@Override
	public FluidStack getFluidRecipeOutput() {
		return outputFluidStack;
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
