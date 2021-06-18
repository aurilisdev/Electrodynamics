package electrodynamics.common.recipe.recipeutils;

import javax.annotation.Nullable;

import electrodynamics.common.inventory.invutils.FluidRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IFluidRecipe extends IElectrodynamicsRecipe{

	@Override
	default ItemStack getCraftingResult(RecipeWrapper inv) {
		return new ItemStack(Items.DIRT,1);
		
	}

	@Override
	default ItemStack getRecipeOutput() {
		return new ItemStack(Items.DIRT,1);
	}
	
	@Nullable
	default boolean matches(FluidRecipeWrapper inv, World worldIn) {
		return false;
	}
	
	@Nullable
	default FluidStack getFluidCraftingResult(FluidRecipeWrapper inv) {
		return null;
	}
	
	@Nullable
	default FluidStack getFluidRecipeOutput() {
		return null;
	}
	
}
