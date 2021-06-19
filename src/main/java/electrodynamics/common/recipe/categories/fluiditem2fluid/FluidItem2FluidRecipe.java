package electrodynamics.common.recipe.categories.fluiditem2fluid;

import java.util.ArrayList;

import electrodynamics.common.inventory.invutils.FluidRecipeWrapper;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class FluidItem2FluidRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private FluidIngredient INPUT_FLUID;
    private CountableIngredient ITEM_INPUT;
    private FluidStack OUTPUT_FLUID;

    public FluidItem2FluidRecipe(ResourceLocation recipeID, CountableIngredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid) {
	super(recipeID);
	ITEM_INPUT = inputItem;
	INPUT_FLUID = inputFluid;
	OUTPUT_FLUID = outputFluid;
    }

    /*
     * @Override public boolean matches(FluidRecipeWrapper inv, World worldIn) {
     * if(this.ITEM_INPUT.testStack(inv.getStackInSlot(0))) {
     * if(this.INPUT_FLUID.testFluid(inv.getInputTankInSlot(0).getFluid())){ return
     * true; } } return false; }
     */
    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (ITEM_INPUT.testStack(pr.getInput())) {
	    ComponentFluidHandler fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    ArrayList<Fluid> inputFluids = fluid.getInputFluids();
	    for (int i = 0; i < inputFluids.size(); i++) {
		FluidTank tank = fluid.getTankFromFluid(inputFluids.get(i));

		if (tank != null && tank.getFluid().getFluid().isEquivalentTo(INPUT_FLUID.getFluidStack().getFluid())
			&& tank.getFluidAmount() >= INPUT_FLUID.getFluidStack().getAmount()) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public FluidStack getFluidCraftingResult(FluidRecipeWrapper inv) {
	return OUTPUT_FLUID;
    }

    @Override
    public FluidStack getFluidRecipeOutput() {
	return OUTPUT_FLUID;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.from(null, ITEM_INPUT, INPUT_FLUID);
    }

}
