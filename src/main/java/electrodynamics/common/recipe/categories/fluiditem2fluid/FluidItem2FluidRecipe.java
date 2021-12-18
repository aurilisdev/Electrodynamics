package electrodynamics.common.recipe.categories.fluiditem2fluid;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.recipeutils.AbstractFluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public abstract class FluidItem2FluidRecipe extends AbstractFluidRecipe {

	private FluidIngredient[] fluidIngredients;
	private CountableIngredient[] ingredients;
	private FluidStack outputStack;

	protected FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids,
			FluidStack outputFluid) {
		super(recipeID);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputStack = outputFluid;
	}

	protected FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids,
			FluidStack outputFluid, ProbableItem[] itemBiproducts) {
		super(recipeID, itemBiproducts);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputStack = outputFluid;
	}

	protected FluidItem2FluidRecipe(CountableIngredient[] inputItems, FluidIngredient[] inputFluids, FluidStack outputFluid,
			ProbableFluid[] fluidBiproducts, ResourceLocation recipeID) {
		super(fluidBiproducts, recipeID);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputStack = outputFluid;
	}

	protected FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids,
			FluidStack outputFluid, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
		super(recipeID, itemBiproducts, fluidBiproducts);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputStack = outputFluid;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {

		Pair<List<Integer>, Boolean> itemPair = areItemsValid(getCountedIngredients(),
				((ComponentInventory) pr.getHolder().getComponent(ComponentType.Inventory)).getInputContents().get(pr.getProcessorNumber()));
		// Electrodynamics.LOGGER.info("item pair " + itemPair.getSecond());
		if (Boolean.TRUE.equals(itemPair.getSecond())) {
			Pair<List<Integer>, Boolean> fluidPair = areFluidsValid(getFluidIngredients(),
					((AbstractFluidHandler<?>) pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
			if (Boolean.TRUE.equals(fluidPair.getSecond())) {
				// Electrodynamics.LOGGER.info("fluid pair" + fluidPair.getSecond());
				setItemArrangement(pr.getProcessorNumber(), itemPair.getFirst());
				setFluidArrangement(fluidPair.getFirst());
				return true;
			}
		}
		return false;
	}

	@Override
	public FluidStack getFluidRecipeOutput() {
		return outputStack;
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

	@Override
	public List<FluidIngredient> getFluidIngredients() {
		return Arrays.asList(fluidIngredients);
	}

	public List<CountableIngredient> getCountedIngredients() {
		return Arrays.asList(ingredients);
	}

}
