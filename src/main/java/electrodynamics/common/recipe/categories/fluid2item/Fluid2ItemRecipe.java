package electrodynamics.common.recipe.categories.fluid2item;

import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
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

public abstract class Fluid2ItemRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    private FluidIngredient INPUT_FLUID;
    private ItemStack ITEM_OUTPUT;

    protected Fluid2ItemRecipe(ResourceLocation recipeID, FluidIngredient fluidInput, ItemStack itemOutput) {
	super(recipeID);
	INPUT_FLUID = fluidInput;
	ITEM_OUTPUT = itemOutput;
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	List<Fluid> inputFluids = fluid.getValidInputFluids();
	for (Fluid inputFluid : inputFluids) {
	    FluidTank tank = fluid.getTankFromFluid(inputFluid, true);
	    if (tank != null && tank.getFluid().getFluid().isSame(INPUT_FLUID.getFluidStack().getFluid())
		    && tank.getFluidAmount() >= INPUT_FLUID.getFluidStack().getAmount()) {
		return true;
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
	return NonNullList.of(null, INPUT_FLUID);
    }
}
