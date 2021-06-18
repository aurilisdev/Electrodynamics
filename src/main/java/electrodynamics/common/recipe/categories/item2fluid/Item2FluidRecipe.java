package electrodynamics.common.recipe.categories.item2fluid;

import electrodynamics.common.inventory.invutils.FluidRecipeWrapper;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public abstract class Item2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe{

	private CountableIngredient ITEM_INPUT;
	private FluidStack FLUID_OUTPUT;
	
	public Item2FluidRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidStack fluidOutput) {
		super(recipeID);
		this.ITEM_INPUT = itemInput;
		this.FLUID_OUTPUT = fluidOutput;
	}
	
	/*
	@Override
	public boolean matches(FluidRecipeWrapper inv, World worldIn) {
		if(this.ITEM_INPUT.test(inv.getStackInSlot(0))){
			return true;
		}
		return false;
	}
	*/
	@Override
	public boolean matchesRecipe(ComponentProcessor pr) {
		return false;
	}
	
	@Override
	public FluidStack getFluidCraftingResult(FluidRecipeWrapper inv) {
		return this.FLUID_OUTPUT;
	}
	
	@Override
	public FluidStack getFluidRecipeOutput() {
		return this.FLUID_OUTPUT;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients(){
		return NonNullList.from(null, ITEM_INPUT);
	}
	
}
