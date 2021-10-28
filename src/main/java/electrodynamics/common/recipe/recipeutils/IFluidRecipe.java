package electrodynamics.common.recipe.recipeutils;

import javax.annotation.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IFluidRecipe extends IElectrodynamicsRecipe {

    @Override
    default ItemStack assemble(RecipeWrapper inv) {
	return new ItemStack(Items.DIRT, 1);

    }

    @Override
    default ItemStack getResultItem() {
	return new ItemStack(Items.DIRT, 1);
    }

    @Nullable
    default FluidStack getFluidRecipeOutput() {
	return FluidStack.EMPTY;
    }

}
