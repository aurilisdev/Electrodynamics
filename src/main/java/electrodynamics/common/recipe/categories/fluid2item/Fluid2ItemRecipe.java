package electrodynamics.common.recipe.categories.fluid2item;

import java.util.ArrayList;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class Fluid2ItemRecipe extends AbstractFluidRecipe {

	private FluidIngredient[] INPUT_FLUIDS;
	private ItemStack ITEM_OUTPUT;

	public Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient[] fluidInputs, ItemStack itemOutput) {
		super(recipeID);
		INPUT_FLUIDS = fluidInputs;
		ITEM_OUTPUT = itemOutput;
	}

	public Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableItem[] itemBiproducts) {
		super(recipeID, itemBiproducts);
		INPUT_FLUIDS = inputFluids;
		ITEM_OUTPUT = itemOutput;
	}

	public Fluid2ItemRecipe(FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableFluid[] fluidBiproducts, ResourceLocation recipeID) {
		super(fluidBiproducts, recipeID);
		INPUT_FLUIDS = inputFluids;
		ITEM_OUTPUT = itemOutput;
	}

	public Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableItem[] itemBiproducts,
			ProbableFluid[] fluidBiproducts) {
		super(recipeID, itemBiproducts, fluidBiproducts);
		INPUT_FLUIDS = inputFluids;
		ITEM_OUTPUT = itemOutput;
	}

	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		Pair<List<Integer>, Boolean> pair = areFluidsValid(getFluidIngredients(),
				((AbstractFluidHandler<?>) pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
		if (pair.getSecond()) {
			setFluidArrangement(pair.getFirst());
			return true;
		}
		return false;
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return ITEM_OUTPUT;
	}

	@Override
	public ItemStack getResultItem() {
		return ITEM_OUTPUT;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		for (Ingredient ing : INPUT_FLUIDS) {
			list.add(ing);
		}
		return list;
	}

	@Override
	public List<FluidIngredient> getFluidIngredients() {
		List<FluidIngredient> list = new ArrayList<>();
		for (FluidIngredient ing : INPUT_FLUIDS) {
			list.add(ing);
		}
		return list;
	}

}
