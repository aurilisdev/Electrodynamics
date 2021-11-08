package electrodynamics.common.recipe.categories.item2fluid;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public abstract class Item2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private CountableIngredient input;
    private FluidStack output;

    protected Item2FluidRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidStack fluidOutput) {
	super(recipeID);
	input = itemInput;
	output = fluidOutput;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	return false;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
	return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, input);
    }

}
