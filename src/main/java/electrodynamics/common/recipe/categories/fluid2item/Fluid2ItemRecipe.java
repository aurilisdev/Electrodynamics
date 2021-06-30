package electrodynamics.common.recipe.categories.fluid2item;

import java.util.ArrayList;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
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

public abstract class Fluid2ItemRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private FluidIngredient INPUT_FLUID;
    private ItemStack ITEM_OUTPUT;

    public Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient fluidInput, ItemStack itemOutput) {
	super(recipeID);
	INPUT_FLUID = fluidInput;
	ITEM_OUTPUT = itemOutput;
    }

    /*
     * @Override public boolean matches(FluidRecipeWrapper inv, World worldIn) {
     * if(this.FLUID_INPUT.testFluid(inv.getInputTankInSlot(0).getFluid())) { return
     * true; } return false; }
     */
    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
    	ComponentFluidHandler fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    ArrayList<Fluid> inputFluids = fluid.getInputFluids();
	    for (int i = 0; i < inputFluids.size(); i++) {
			FluidTank tank = fluid.getTankFromFluid(inputFluids.get(i));
			if (tank != null && tank.getFluid().getFluid().isEquivalentTo(INPUT_FLUID.getFluidStack().getFluid())
				&& tank.getFluidAmount() >= INPUT_FLUID.getFluidStack().getAmount()) {
			    return true;
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
	return NonNullList.from(null, INPUT_FLUID);
    }
}
