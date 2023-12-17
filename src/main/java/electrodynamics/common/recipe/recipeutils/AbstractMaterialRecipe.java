package electrodynamics.common.recipe.recipeutils;

import java.util.Collections;
import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractMaterialRecipe extends ElectrodynamicsRecipe {

	public AbstractMaterialRecipe(ResourceLocation recipeID, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts) {
		super(recipeID, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	public FluidStack getFluidRecipeOutput() {
		return FluidStack.EMPTY;
	}

	public List<FluidIngredient> getFluidIngredients() {
		return Collections.emptyList();
	}

}
