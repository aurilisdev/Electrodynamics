package electrodynamics.compatibility.jei.recipecategories.fluid2fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.prefab.utilities.CapabilityUtils;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class Fluid2FluidRecipeCategory<T extends ElectrodynamicsRecipe> extends ElectrodynamicsRecipeCategory<T> {

	public Fluid2FluidRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine, BackgroundWrapper wrapper, Class<T> recipeCategoryClass, int animationTime) {
		super(guiHelper, modID, recipeGroup, inputMachine, wrapper, recipeCategoryClass, animationTime);
	}

	@Override
	public List<List<FluidStack>> getFluidInputs(ElectrodynamicsRecipe electro) {
		Fluid2FluidRecipe recipe = (Fluid2FluidRecipe) electro;
		List<List<FluidStack>> ingredients = new ArrayList<>();
		for (FluidIngredient ing : recipe.getFluidIngredients()) {
			List<FluidStack> fluids = new ArrayList<>();
			for (FluidStack stack : ing.getMatchingFluids()) {
				if (!stack.getFluid().getRegistryName().toString().toLowerCase().contains("flow")) {
					fluids.add(stack);
				}
			}
			ingredients.add(fluids);
		}
		return ingredients;
	}

	@Override
	public List<List<ItemStack>> getItemInputs(ElectrodynamicsRecipe electro) {
		Fluid2FluidRecipe recipe = (Fluid2FluidRecipe) electro;
		List<List<ItemStack>> ingredients = new ArrayList<>();

		for (FluidIngredient ing : recipe.getFluidIngredients()) {
			List<ItemStack> buckets = new ArrayList<>();
			for (FluidStack stack : ing.getMatchingFluids()) {
				ItemStack bucket = new ItemStack(stack.getFluid().getBucket(), 1);
				CapabilityUtils.fill(bucket, stack);
				buckets.add(bucket);
			}
			ingredients.add(buckets);
		}

		return ingredients;
	}

	@Override
	public List<ItemStack> getItemOutputs(ElectrodynamicsRecipe electro) {
		Fluid2FluidRecipe recipe = (Fluid2FluidRecipe) electro;
		List<ItemStack> outputItems = new ArrayList<>();

		ItemStack bucket = new ItemStack(recipe.getFluidRecipeOutput().getFluid().getBucket(), 1);
		CapabilityUtils.fill(bucket, recipe.getFluidRecipeOutput());
		outputItems.add(bucket);

		if (recipe.hasFluidBiproducts()) {
			for (ProbableFluid stack : recipe.getFluidBiproducts()) {
				ItemStack temp = new ItemStack(stack.getFullStack().getFluid().getBucket(), 1);
				CapabilityUtils.fill(temp, stack.getFullStack());
				outputItems.add(temp);
			}
		}
		return outputItems;
	}

	@Override
	public List<FluidStack> getFluidOutputs(ElectrodynamicsRecipe electro) {
		Fluid2FluidRecipe recipe = (Fluid2FluidRecipe) electro;
		List<FluidStack> outputFluids = new ArrayList<>();
		outputFluids.add(recipe.getFluidRecipeOutput());
		if (recipe.hasFluidBiproducts()) {
			outputFluids.addAll(Arrays.asList(recipe.getFullFluidBiStacks()));
		}
		return outputFluids;
	}

}
