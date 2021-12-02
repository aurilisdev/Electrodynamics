package electrodynamics.common.recipe.categories.fluiditem2fluid;

import java.util.ArrayList;
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

    private FluidIngredient[] INPUT_FLUIDS;
    private CountableIngredient[] INPUT_ITEMS;
    private FluidStack OUTPUT_FLUID;

    public FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, FluidStack outputFluid) {
	super(recipeID);
	INPUT_ITEMS = inputItems;
	INPUT_FLUIDS = inputFluids;
	OUTPUT_FLUID = outputFluid;
    }

    public FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, FluidStack outputFluid,
	    ProbableItem[] itemBiproducts) {
	super(recipeID, itemBiproducts);
	INPUT_ITEMS = inputItems;
	INPUT_FLUIDS = inputFluids;
	OUTPUT_FLUID = outputFluid;
    }

    public FluidItem2FluidRecipe(CountableIngredient[] inputItems, FluidIngredient[] inputFluids, FluidStack outputFluid,
	    ProbableFluid[] fluidBiproducts, ResourceLocation recipeID) {
	super(fluidBiproducts, recipeID);
	INPUT_ITEMS = inputItems;
	INPUT_FLUIDS = inputFluids;
	OUTPUT_FLUID = outputFluid;
    }

    public FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, FluidStack outputFluid,
	    ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
	super(recipeID, itemBiproducts, fluidBiproducts);
	INPUT_ITEMS = inputItems;
	INPUT_FLUIDS = inputFluids;
	OUTPUT_FLUID = outputFluid;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {

	Pair<List<Integer>, Boolean> itemPair = areItemsValid(getCountedIngredients(),
		((ComponentInventory) pr.getHolder().getComponent(ComponentType.Inventory)).getInputContents().get(pr.getProcessorNumber()));
	// Electrodynamics.LOGGER.info("item pair " + itemPair.getSecond());
	if (itemPair.getSecond()) {
	    Pair<List<Integer>, Boolean> fluidPair = areFluidsValid(getFluidIngredients(),
		    ((AbstractFluidHandler<?>) pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
	    if (fluidPair.getSecond()) {
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
	return OUTPUT_FLUID;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	NonNullList<Ingredient> list = NonNullList.create();
	for (Ingredient ing : INPUT_ITEMS) {
	    list.add(ing);
	}
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

    public List<CountableIngredient> getCountedIngredients() {
	List<CountableIngredient> list = new ArrayList<>();
	for (CountableIngredient ing : INPUT_ITEMS) {
	    list.add(ing);
	}
	return list;
    }

}
