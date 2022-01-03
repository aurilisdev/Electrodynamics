package electrodynamics.compatibility.jei.recipecategories.fluid2fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
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

public abstract class Fluid2FluidRecipeCategory extends ElectrodynamicsRecipeCategory<Fluid2FluidRecipe> {

	public Fluid2FluidRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine, BackgroundWrapper wrapper,
			int animationTime) {
		super(guiHelper, modID, recipeGroup, inputMachine, wrapper, Fluid2FluidRecipe.class, animationTime);
	}

	@Override
	public void setIngredients(Fluid2FluidRecipe recipe, IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, getItemInputs(recipe));
		ingredients.setInputLists(VanillaTypes.FLUID, getFluidInputs(recipe));
		ingredients.setOutputs(VanillaTypes.ITEM, getItemOutputs(recipe));
		ingredients.setOutputs(VanillaTypes.FLUID, getFluidOutputs(recipe));
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Fluid2FluidRecipe recipe, IIngredients ingredients) {

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
	public void draw(Fluid2FluidRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {

		drawInputSlots(matrixStack);
		drawOutputSlots(matrixStack);
		drawStaticArrows(matrixStack);
		drawFluidInputs(matrixStack);
		drawFluidOutputs(matrixStack);
		drawAnimatedArrows(matrixStack);

		addDescriptions(matrixStack, recipe);
	}

	private static List<List<FluidStack>> getFluidInputs(Fluid2FluidRecipe recipe) {
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

	private static List<List<ItemStack>> getItemInputs(Fluid2FluidRecipe recipe) {
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

	private static List<ItemStack> getItemOutputs(Fluid2FluidRecipe recipe) {
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

	private static List<FluidStack> getFluidOutputs(Fluid2FluidRecipe recipe) {
		List<FluidStack> outputFluids = new ArrayList<>();
		outputFluids.add(recipe.getFluidRecipeOutput());
		if (recipe.hasFluidBiproducts()) {
			outputFluids.addAll(Arrays.asList(recipe.getFullFluidBiStacks()));
		}
		return outputFluids;
	}

}
