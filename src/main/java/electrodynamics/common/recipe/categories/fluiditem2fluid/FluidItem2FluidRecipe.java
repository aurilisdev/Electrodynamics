package electrodynamics.common.recipe.categories.fluiditem2fluid;

import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class FluidItem2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private FluidIngredient INPUT_FLUID;
    private CountableIngredient INPUT_ITEM;

    private FluidStack OUTPUT_FLUID;

    protected FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid) {
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
	    for (Fluid inputFluid : inputFluids) {
		FluidTank tank = fluid.getTankFromFluid(inputFluid, true);
		if (tank != null && tank.getFluid().getFluid().isSame(INPUT_FLUID.getFluidStack().getFluid())
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
	return NonNullList.of(null, INPUT_ITEM, INPUT_FLUID);
    }

}
