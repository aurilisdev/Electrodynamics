package electrodynamics.common.recipe.categories.fluiditem2item;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class FluidItem2ItemRecipe extends AbstractFluidRecipe {

    private CountableIngredient[] INPUT_ITEMS;
    private FluidIngredient[] INPUT_FLUIDS;
    private ItemStack ITEM_OUTPUT;

    public FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient[] itemInputs, FluidIngredient[] fluidInputs, ItemStack itemOutput) {
		super(recipeID);
		INPUT_ITEMS = itemInputs;
		INPUT_FLUIDS = fluidInputs;
		ITEM_OUTPUT = itemOutput;
    }

    public FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems,
    		FluidIngredient[] inputFluids, ItemStack itemOutput, ProbableItem[] itemBiproducts) {
		super(recipeID, itemBiproducts);
		INPUT_ITEMS = inputItems;
		INPUT_FLUIDS = inputFluids;
		ITEM_OUTPUT = itemOutput;
    }
    
    public FluidItem2ItemRecipe(CountableIngredient[] inputItems, FluidIngredient[] inputFluids, 
    		ItemStack itemOutput, ProbableFluid[] fluidBiproducts, ResourceLocation recipeID) {
		super(fluidBiproducts, recipeID);
		INPUT_ITEMS = inputItems;
		INPUT_FLUIDS = inputFluids;
		ITEM_OUTPUT = itemOutput;
    }
    
    public FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, 
    		ItemStack itemOutput, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
		super(recipeID, itemBiproducts, fluidBiproducts);
		INPUT_ITEMS = inputItems;
		INPUT_FLUIDS = inputFluids;
		ITEM_OUTPUT = itemOutput;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
    	Pair<List<Integer>, Boolean> itemPair = areItemsValid(getCountedIngredients(), ((ComponentInventory)pr.getHolder().getComponent(ComponentType.Inventory)).getInputContents().get(pr.getProcessorNumber()));
		if(itemPair.getSecond()) {
			Pair<List<Integer>, Boolean> fluidPair = areFluidsValid(getFluidIngredients(), ((AbstractFluidHandler<?>)pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
	    	if(fluidPair.getSecond()) {
	    		setItemArrangement(pr.getProcessorNumber(), itemPair.getFirst());
	    		setFluidArrangement(fluidPair.getFirst());
	    		return true;
	    	}
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
    	for(Ingredient ing : INPUT_ITEMS) {
    		list.add(ing);
    	}
    	for(Ingredient ing : INPUT_FLUIDS) {
    		list.add(ing);
    	}
    	return list;
    }
    
    public List<FluidIngredient> getFluidIngredients(){
    	List<FluidIngredient> list = new ArrayList<>();
    	for(FluidIngredient ing : INPUT_FLUIDS) {
    		list.add(ing);
    	}
    	return list;
    }
    
    public List<CountableIngredient> getCountedIngredients(){
		List<CountableIngredient> list = new ArrayList<>();
		for(CountableIngredient ing : INPUT_ITEMS) {
			list.add(ing);
		}
		return list;
	}

}
