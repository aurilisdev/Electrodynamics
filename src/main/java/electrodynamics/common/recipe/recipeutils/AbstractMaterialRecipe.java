package electrodynamics.common.recipe.recipeutils;

import java.util.Collections;
import java.util.List;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public abstract class AbstractMaterialRecipe extends ElectrodynamicsRecipe {

	public AbstractMaterialRecipe(String recipeGroup, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
		super(recipeGroup, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
	}

	@Override
	public ItemStack assemble(RecipeWrapper container, RegistryAccess registryAccess) {
		return getItemOutputNoAccess();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
		return getItemOutputNoAccess();
	}

	public FluidStack getFluidRecipeOutput() {
		return FluidStack.EMPTY;
	}

	public GasStack getGasRecipeOutput() {
		return GasStack.EMPTY;
	}

	public List<FluidIngredient> getFluidIngredients() {
		return Collections.emptyList();
	}

	public List<GasIngredient> getGasIngredients() {
		return Collections.emptyList();
	}

	public ItemStack getItemOutputNoAccess() {
		return ItemStack.EMPTY;
	}

}
