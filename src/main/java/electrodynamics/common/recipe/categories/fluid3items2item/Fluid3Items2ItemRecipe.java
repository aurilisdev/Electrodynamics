package electrodynamics.common.recipe.categories.fluid3items2item;

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

public abstract class Fluid3Items2ItemRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    protected FluidIngredient INPUT_FLUID;
    protected CountableIngredient INPUT_ITEM1;
    protected CountableIngredient INPUT_ITEM2;
    protected CountableIngredient INPUT_ITEM3;

    protected ItemStack OUTPUT_ITEM;

    protected Fluid3Items2ItemRecipe(ResourceLocation location, FluidIngredient iNPUT_FLUID, CountableIngredient iNPUT_ITEM1,
	    CountableIngredient iNPUT_ITEM2, CountableIngredient iNPUT_ITEM3, ItemStack oUTPUT_ITEM) {
	super(location);
	INPUT_FLUID = iNPUT_FLUID;
	INPUT_ITEM1 = iNPUT_ITEM1;
	INPUT_ITEM2 = iNPUT_ITEM2;
	INPUT_ITEM3 = iNPUT_ITEM3;
	OUTPUT_ITEM = oUTPUT_ITEM;
    }

    @Override
    @SuppressWarnings("java:S1066")
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (INPUT_ITEM1.testStack(pr.getInput()) || INPUT_ITEM1.testStack(pr.getSecondInput()) || INPUT_ITEM1.testStack(pr.getThirdInput())) {
	    if (INPUT_ITEM2.testStack(pr.getInput()) || INPUT_ITEM2.testStack(pr.getSecondInput()) || INPUT_ITEM2.testStack(pr.getThirdInput())) {
		if (INPUT_ITEM3.testStack(pr.getInput()) || INPUT_ITEM3.testStack(pr.getSecondInput()) || INPUT_ITEM3.testStack(pr.getThirdInput())) {
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
	    }
	}
	return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
	return OUTPUT_ITEM;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
	return OUTPUT_ITEM;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.from(null, INPUT_ITEM1, INPUT_ITEM2, INPUT_ITEM3, INPUT_FLUID);
    }

}
