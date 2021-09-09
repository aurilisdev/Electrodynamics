package electrodynamics.common.recipe.categories.fluiditem2fluid;

import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class FluidItem2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private FluidIngredient INPUT_FLUID;
    private CountableIngredient INPUT_ITEM;

    private FluidStack OUTPUT_FLUID;

    public FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid) {
	super(recipeID);
	INPUT_ITEM = inputItem;
	INPUT_FLUID = inputFluid;
	OUTPUT_FLUID = outputFluid;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {

	if (INPUT_ITEM.testStack(pr.getInput())) {
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    List<Fluid> inputFluids = fluid.getValidInputFluids();
	    for (int i = 0; i < inputFluids.size(); i++) {
		FluidTank tank = fluid.getTankFromFluid(inputFluids.get(i), true);
		if (tank != null && tank.getFluid().getFluid().isEquivalentTo(INPUT_FLUID.getFluidStack().getFluid())
			&& tank.getFluidAmount() >= INPUT_FLUID.getFluidStack().getAmount()) {
		    return true;
		}
	    }

	}
	return false;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
	return OUTPUT_FLUID;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.from(null, INPUT_ITEM, INPUT_FLUID);
    }

}
