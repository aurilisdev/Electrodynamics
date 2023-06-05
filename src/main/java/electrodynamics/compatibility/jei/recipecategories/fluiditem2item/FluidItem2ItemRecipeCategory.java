package electrodynamics.compatibility.jei.recipecategories.fluiditem2item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.prefab.utilities.CapabilityUtils;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class FluidItem2ItemRecipeCategory<T extends FluidItem2ItemRecipe> extends ElectrodynamicsRecipeCategory<T> {

	/*
	 * DOCUMENTATION NOTES:
	 * 
	 * > Items supercede bucket slots in order > All biproducts will be included with the outputSlots field > All fluid bucket output slots will be incled with the outputSlots field
	 */

	protected FluidItem2ItemRecipeCategory(IGuiHelper guiHelper, String recipeGroup, ItemStack inputMachine, BackgroundWrapper bWrap, Class<T> recipeClass, int animTime) {

		super(guiHelper, recipeGroup, inputMachine, bWrap, recipeClass, animTime);
	}

	@Override
	public List<List<FluidStack>> getFluidInputs(ElectrodynamicsRecipe electro) {
		FluidItem2ItemRecipe recipe = (FluidItem2ItemRecipe) electro;
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
		FluidItem2ItemRecipe recipe = (FluidItem2ItemRecipe) electro;
		List<List<ItemStack>> ingredients = new ArrayList<>();

		for (CountableIngredient ing : recipe.getCountedIngredients()) {
			ingredients.add(ing.fetchCountedStacks());
		}

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
		FluidItem2ItemRecipe recipe = (FluidItem2ItemRecipe) electro;
		List<ItemStack> outputItems = new ArrayList<>();

		outputItems.add(recipe.getResultItem());

		if (recipe.hasItemBiproducts()) {
			outputItems.addAll(Arrays.asList(recipe.getFullItemBiStacks()));
		}

		if (recipe.hasFluidBiproducts()) {
			for (ProbableFluid stack : recipe.getFluidBiproducts()) {
				ItemStack temp = new ItemStack(stack.getFullStack().getFluid().getBucket(), 1);
				CapabilityUtils.fillFluidItem(temp, stack.getFullStack(), FluidAction.EXECUTE);
				outputItems.add(temp);
			}
		}
		return outputItems;
	}

}
