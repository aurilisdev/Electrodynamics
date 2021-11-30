package electrodynamics.common.recipe.categories.fluid2fluid;

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
import net.minecraftforge.fluids.FluidStack;

public abstract class Fluid2FluidRecipe extends AbstractFluidRecipe {

    private FluidIngredient[] INPUT_FLUIDS;
    private FluidStack OUTPUT_FLUID;

    public Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid) {
		super(recipeID);
		INPUT_FLUIDS = inputFluids;
		OUTPUT_FLUID = outputFluid;
    }
    
    public Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid,
    	ProbableItem[] itemBiproducts) {
		super(recipeID, itemBiproducts);	
		INPUT_FLUIDS = inputFluids;
		OUTPUT_FLUID = outputFluid;
	}
    	
	public Fluid2FluidRecipe(FluidIngredient[] inputFluids, FluidStack outputFluid, ProbableFluid[] fluidBiproducts,
		ResourceLocation recipeID) {
		super(fluidBiproducts, recipeID);
		INPUT_FLUIDS = inputFluids;
		OUTPUT_FLUID = outputFluid;
	}
	
	public Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient[] inputFluids, FluidStack outputFluid,
		ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
		super(recipeID, itemBiproducts, fluidBiproducts);
		INPUT_FLUIDS = inputFluids;
		OUTPUT_FLUID = outputFluid;
	}

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
    	Pair<List<Integer>, Boolean> pair = areFluidsValid(getFluidIngredients(), ((AbstractFluidHandler<?>)pr.getHolder().getComponent(ComponentType.FluidHandler)).getInputTanks());
    	if(pair.getSecond()) {
    		setFluidArrangement(pair.getFirst());
    		return true;
    	}
    	return false;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
    	return OUTPUT_FLUID;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
    	NonNullList<Ingredient> ings = NonNullList.create();
    	for(FluidIngredient ing : INPUT_FLUIDS) {
    		ings.add(ing);
    	}
    	return ings;
    }
    
    public List<FluidIngredient> getFluidIngredients(){
    	List<FluidIngredient> list = new ArrayList<>();
    	for(FluidIngredient ing : INPUT_FLUIDS) {
    		list.add(ing);
    	}
    	return list;
    }

}
