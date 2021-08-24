package electrodynamics.common.recipe.recipeutils;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IFluidRecipe extends IElectrodynamicsRecipe {

    @Override
    default ItemStack getCraftingResult(RecipeWrapper inv) {
    	return new ItemStack(Items.DIRT, 1);

    }

    @Override
    default ItemStack getRecipeOutput() {
    	return new ItemStack(Items.DIRT, 1);
    }

    @Nullable
    default FluidStack getFluidRecipeOutput() {
    	return FluidStack.EMPTY;
    }

}
