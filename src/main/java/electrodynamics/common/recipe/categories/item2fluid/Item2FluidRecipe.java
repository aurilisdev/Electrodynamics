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

    private CountableIngredient ITEM_INPUT;
    private FluidStack FLUID_OUTPUT;

    public Item2FluidRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidStack fluidOutput) {
	super(recipeID);
	ITEM_INPUT = itemInput;
	FLUID_OUTPUT = fluidOutput;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	return false;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
	return FLUID_OUTPUT;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, ITEM_INPUT);
    }

}
