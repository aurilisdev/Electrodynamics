package electrodynamics.common.recipe.categories.fluiditem2item;

import java.util.Arrays;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.recipeutils.AbstractFluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.generic.AbstractFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class FluidItem2ItemRecipe extends AbstractFluidRecipe {

	private CountableIngredient[] ingredients;
	private FluidIngredient[] fluidIngredients;
	private ItemStack outputItemStack;

	protected FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient[] itemInputs, FluidIngredient[] fluidInputs, ItemStack itemOutput, double experience) {
		super(recipeID, experience);
		ingredients = itemInputs;
		fluidIngredients = fluidInputs;
		outputItemStack = itemOutput;
	}

	protected FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableItem[] itemBiproducts, double experience) {
		super(recipeID, itemBiproducts, experience);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputItemStack = itemOutput;
	}

	protected FluidItem2ItemRecipe(CountableIngredient[] inputItems, FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableFluid[] fluidBiproducts, ResourceLocation recipeID, double experience) {
		super(fluidBiproducts, recipeID, experience);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputItemStack = itemOutput;
	}

	protected FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, double experience) {
		super(recipeID, itemBiproducts, fluidBiproducts, experience);
		ingredients = inputItems;
		fluidIngredients = inputFluids;
		outputItemStack = itemOutput;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		Pair<List<Integer>, Boolean> itemPair = areItemsValid(getCountedIngredients(), ((ComponentInventory) pr.getHolder().getComponent(ComponentType.Inventory)).getInputContents().get(pr.getProcessorNumber()));
		if (Boolean.TRUE.equals(itemPair.getSecond())) {
			Pair<List<Integer>, Boolean> fluidPair = areFluidsValid(getFluidIngredients(), ((AbstractFluidHandler<?>) pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
			if (Boolean.TRUE.equals(fluidPair.getSecond())) {
				setItemArrangement(pr.getProcessorNumber(), itemPair.getFirst());
				setFluidArrangement(fluidPair.getFirst());
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return outputItemStack;
	}

	@Override
	public ItemStack getResultItem() {
		return outputItemStack;
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
