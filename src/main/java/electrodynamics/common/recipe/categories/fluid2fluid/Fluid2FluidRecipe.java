package electrodynamics.common.recipe.categories.fluid2fluid;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public abstract class Fluid2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private FluidIngredient INPUT_FLUID;
    private FluidStack OUTPUT_FLUID;

    public Fluid2FluidRecipe(ResourceLocation recipeID, FluidIngredient inputFluid, FluidStack outputFluid) {
	super(recipeID);
	INPUT_FLUID = inputFluid;
	OUTPUT_FLUID = outputFluid;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	return false;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
	return OUTPUT_FLUID;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, INPUT_FLUID);
    }

}
