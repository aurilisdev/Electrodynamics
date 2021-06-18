package electrodynamics.common.recipe.categories.fluid2item;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class Fluid2ItemRecipe extends ElectrodynamicsRecipe implements IFluidRecipe{

	private FluidIngredient FLUID_INPUT;
	private ItemStack ITEM_OUTPUT;
	
	public Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient fluidInput, ItemStack itemOutput) {
		super(recipeID);
		this.FLUID_INPUT = fluidInput;
		this.ITEM_OUTPUT = itemOutput;
	}

	/*
	@Override
	public boolean matches(FluidRecipeWrapper inv, World worldIn) {
		if(this.FLUID_INPUT.testFluid(inv.getInputTankInSlot(0).getFluid())) {
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
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return ITEM_OUTPUT;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ITEM_OUTPUT;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients(){
		return NonNullList.from(null, FLUID_INPUT);
	}
}
