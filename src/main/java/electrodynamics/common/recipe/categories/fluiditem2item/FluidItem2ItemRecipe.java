package electrodynamics.common.recipe.categories.fluiditem2item;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class FluidItem2ItemRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private CountableIngredient ITEM_INPUT;
    private FluidIngredient FLUID_INPUT;
    private ItemStack ITEM_OUTPUT;

    protected FluidItem2ItemRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput) {
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
	    for (Fluid inputFluid : inputFluids) {

		FluidTank tank = fluid.getTankFromFluid(inputFluid, true);

		if (tank != null && tank.getFluid().getFluid().isSame(FLUID_INPUT.getFluidStack().getFluid())
			&& tank.getFluidAmount() >= FLUID_INPUT.getFluidStack().getAmount()) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
	return ITEM_OUTPUT;
    }

    @Override
    public ItemStack getResultItem() {
	return ITEM_OUTPUT;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.of(null, ITEM_INPUT, FLUID_INPUT);
    }

}
