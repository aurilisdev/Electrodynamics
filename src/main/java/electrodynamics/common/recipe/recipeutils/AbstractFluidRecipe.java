package electrodynamics.common.recipe.recipeutils;

import java.util.List;

import javax.annotation.Nullable;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractFluidRecipe extends ElectrodynamicsRecipe {

	protected AbstractFluidRecipe(ResourceLocation recipeID, double experience, int ticks, double usagePerTick) {
		super(recipeID, experience, ticks, usagePerTick);
	}

	protected AbstractFluidRecipe(ResourceLocation recipeID, ProbableItem[] itemBiproducts, double experience, int ticks, double usagePerTick) {
		super(recipeID, itemBiproducts, experience, ticks, usagePerTick);
	}

	protected AbstractFluidRecipe(ProbableFluid[] fluidBiproducts, ResourceLocation recipeID, double experience, int ticks, double usagePerTick) {
		super(fluidBiproducts, recipeID, experience, ticks, usagePerTick);
	}

	protected AbstractFluidRecipe(ResourceLocation recipeID, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, double experience, int ticks, double usagePerTick) {
		super(recipeID, itemBiproducts, fluidBiproducts, experience, ticks, usagePerTick);
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return new ItemStack(Items.DIRT, 1);
	}

	@Override
	public ItemStack getResultItem() {
		return new ItemStack(Items.DIRT, 1);
	}

	@Nullable
	public FluidStack getFluidRecipeOutput() {
		return FluidStack.EMPTY;
	}

	public abstract List<FluidIngredient> getFluidIngredients();

}
