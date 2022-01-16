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

	protected AbstractFluidRecipe(ResourceLocation recipeID, double experience) {
		super(recipeID, experience);
	}

	protected AbstractFluidRecipe(ResourceLocation recipeID, ProbableItem[] itemBiproducts, double experience) {
		super(recipeID, itemBiproducts, experience);
	}

	protected AbstractFluidRecipe(ProbableFluid[] fluidBiproducts, ResourceLocation recipeID, double experience) {
		super(fluidBiproducts, recipeID, experience);
	}

	protected AbstractFluidRecipe(ResourceLocation recipeID, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproducts, double experience) {
		super(recipeID, itemBiproducts, fluidBiproducts, experience);
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
