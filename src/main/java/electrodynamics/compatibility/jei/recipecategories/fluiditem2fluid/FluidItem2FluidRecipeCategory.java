package electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.prefab.utilities.CapabilityUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class FluidItem2FluidRecipeCategory extends ElectrodynamicsRecipeCategory<FluidItem2FluidRecipe> {

	/*
	 * DOCUMENTATION NOTES:
	 * 
	 * > Items supercede bucket slots in order > All biproducts will be included with the outputSlots field > All fluid bucket output slots will be incled with the outputSlots field
	 */

	protected FluidItem2FluidRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine, BackgroundWrapper bWrap,
			int animTime) {

		super(guiHelper, modID, recipeGroup, inputMachine, bWrap, FluidItem2FluidRecipe.class, animTime);
	}

	@Override
	public void setIngredients(FluidItem2FluidRecipe recipe, IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, getItemInputs(recipe));
		ingredients.setInputLists(VanillaTypes.FLUID, getFluidInputs(recipe));
		ingredients.setOutputs(VanillaTypes.ITEM, getItemOutputs(recipe));
		ingredients.setOutputs(VanillaTypes.FLUID, getFluidOutputs(recipe));
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FluidItem2FluidRecipe recipe, IIngredients ingredients) {

		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

		setItemInputs(guiItemStacks);
		setFluidInputs(guiFluidStacks, recipe.getFluidIngredients());

		setItemOutputs(guiItemStacks);
		setFluidOutputs(guiFluidStacks, recipe);

		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);

	}

	@Override
	public void draw(FluidItem2FluidRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {

		drawInputSlots(matrixStack);
		drawOutputSlots(matrixStack);
		drawStaticArrows(matrixStack);
		drawFluidInputs(matrixStack);
		drawFluidOutputs(matrixStack);
		drawAnimatedArrows(matrixStack);

		addDescriptions(matrixStack, recipe);
	}

	private static List<List<FluidStack>> getFluidInputs(FluidItem2FluidRecipe recipe) {
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

	private static List<List<ItemStack>> getItemInputs(FluidItem2FluidRecipe recipe) {
		List<List<ItemStack>> ingredients = new ArrayList<>();

		for (CountableIngredient ing : recipe.getCountedIngredients()) {
			ingredients.add(ing.fetchCountedStacks());
		}

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

	private static List<ItemStack> getItemOutputs(FluidItem2FluidRecipe recipe) {

		List<ItemStack> outputItems = new ArrayList<>();

		if (recipe.hasItemBiproducts()) {
			outputItems.addAll(Arrays.asList(recipe.getFullItemBiStacks()));
		}

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

	private static List<FluidStack> getFluidOutputs(FluidItem2FluidRecipe recipe) {
		List<FluidStack> outputFluids = new ArrayList<>();
		outputFluids.add(recipe.getFluidRecipeOutput());
		if (recipe.hasFluidBiproducts()) {
			outputFluids.addAll(Arrays.asList(recipe.getFullFluidBiStacks()));
		}
		return outputFluids;
	}

}