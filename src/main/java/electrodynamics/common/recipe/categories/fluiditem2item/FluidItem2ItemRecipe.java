package electrodynamics.common.recipe.categories.fluiditem2item;

import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class FluidItem2ItemRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private CountableIngredient ITEM_INPUT;
    private FluidIngredient FLUID_INPUT;
    private ItemStack ITEM_OUTPUT;

    public FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput) {
	super(recipeID);
	ITEM_INPUT = itemInput;
	FLUID_INPUT = fluidInput;
	ITEM_OUTPUT = itemOutput;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (ITEM_INPUT.testStack(pr.getInput())) {
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    List<Fluid> inputFluids = fluid.getValidInputFluids();
	    for (int i = 0; i < inputFluids.size(); i++) {

		FluidTank tank = fluid.getTankFromFluid(inputFluids.get(i), true);

		if (tank != null && tank.getFluid().getFluid().isEquivalentTo(FLUID_INPUT.getFluidStack().getFluid())
			&& tank.getFluidAmount() >= FLUID_INPUT.getFluidStack().getAmount()) {
		    return true;
		}
	    }
	}
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
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.from(null, ITEM_INPUT, FLUID_INPUT);
    }

}
