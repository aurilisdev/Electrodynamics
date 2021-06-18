package electrodynamics.common.recipe.categories.fluid2fluid;

import electrodynamics.common.inventory.invutils.FluidRecipeWrapper;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public abstract class Fluid2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe{

	private FluidIngredient INPUT_FLUID;
	private FluidStack OUTPUT_FLUID;
	
	
	public Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient inputFluid, FluidStack outputFluid) {
		super(recipeID);
		this.INPUT_FLUID = inputFluid;
		this.OUTPUT_FLUID = outputFluid;
	}

	/*
	@Override
	public boolean matches(FluidRecipeWrapper inv, World worldIn) {
		if(this.INPUT_FLUID.testFluid(inv.getInputTankInSlot(0).getFluid())) {
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
		return this.OUTPUT_FLUID;
	}

	@Override
	public FluidStack getFluidRecipeOutput() {
		return this.OUTPUT_FLUID;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients(){
		return NonNullList.from(null, INPUT_FLUID);
	}
	
}
