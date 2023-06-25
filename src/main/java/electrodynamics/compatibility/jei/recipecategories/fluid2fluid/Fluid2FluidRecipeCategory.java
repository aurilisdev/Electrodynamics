package electrodynamics.compatibility.jei.recipecategories.fluid2fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.prefab.utilities.CapabilityUtils;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class Fluid2FluidRecipeCategory<T extends ElectrodynamicsRecipe> extends AbstractRecipeCategory<T> {

	protected Fluid2FluidRecipeCategory(IGuiHelper guiHelper, String recipeGroup, ItemStack inputMachine, BackgroundObject wrapper, RecipeType<T> recipeType, int animationTime) {
		super(guiHelper, recipeGroup, inputMachine, wrapper, recipeType, animationTime);
	}

	@Override
	public List<List<FluidStack>> getFluidInputs(ElectrodynamicsRecipe electro) {
		Fluid2FluidRecipe recipe = (Fluid2FluidRecipe) electro;
		List<List<FluidStack>> ingredients = new ArrayList<>();
		for (FluidIngredient ing : recipe.getFluidIngredients()) {
			List<FluidStack> fluids = new ArrayList<>();
			for (FluidStack stack : ing.getMatchingFluids()) {
				if (!ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString().toLowerCase(Locale.ROOT).contains("flow")) {
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
				CapabilityUtils.fillFluidItem(bucket, stack, FluidAction.EXECUTE);
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
		CapabilityUtils.fillFluidItem(bucket, recipe.getFluidRecipeOutput(), FluidAction.EXECUTE);
		outputItems.add(bucket);

		if (recipe.hasFluidBiproducts()) {
			for (ProbableFluid stack : recipe.getFluidBiproducts()) {
				ItemStack temp = new ItemStack(stack.getFullStack().getFluid().getBucket(), 1);
				CapabilityUtils.fillFluidItem(temp, stack.getFullStack(), FluidAction.EXECUTE);
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
