package electrodynamics.common.recipe.categories.fluiditem2item;

import java.util.ArrayList;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
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

    /*
     * @Override public boolean matches(FluidRecipeWrapper inv, World worldIn) {
     * if(this.ITEM_INPUT.testStack(inv.getStackInSlot(0))) {
     * if(this.FLUID_INPUT.testFluid(inv.getInputTankInSlot(0).getFluid())) { return
     * true; } } return false; }
     */
    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (ITEM_INPUT.testStack(pr.getInput())) {
	    ComponentFluidHandler fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    ArrayList<Fluid> inputFluids = fluid.getInputFluids();
	    for (int i = 0; i < inputFluids.size(); i++) {

		FluidTank tank = fluid.getTankFromFluid(inputFluids.get(i));

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
