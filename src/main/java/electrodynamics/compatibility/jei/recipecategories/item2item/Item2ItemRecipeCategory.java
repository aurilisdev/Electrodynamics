package electrodynamics.compatibility.jei.recipecategories.item2item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.world.item.ItemStack;

public abstract class Item2ItemRecipeCategory extends ElectrodynamicsRecipeCategory<Item2ItemRecipe> {

    /*
     * DOCUMENTATION NOTES:
     * 
     * > Output items supercede buckets in position > All biproducts will be
     * included with the outputSlots field > All fluid bucket output slots will be
     * incled with the outputSlots field
     */

    protected Item2ItemRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine, BackgroundWrapper bWrap,
	    int animTime) {
	super(guiHelper, modID, recipeGroup, inputMachine, bWrap, Item2ItemRecipe.class, animTime);
    }

    @Override
    public void setIngredients(Item2ItemRecipe recipe, IIngredients ingredients) {
	List<List<ItemStack>> inputs = new ArrayList<>();
	for (CountableIngredient ing : recipe.getCountedIngredients()) {
	    inputs.add(ing.fetchCountedStacks());
	}
	ingredients.setInputLists(VanillaTypes.ITEM, inputs);

	ingredients.setOutputs(VanillaTypes.ITEM, getItemOutputs(recipe));

	if (recipe.hasFluidBiproducts()) {
	    ingredients.setOutputs(VanillaTypes.FLUID, Arrays.asList(recipe.getFullFluidBiStacks()));
	}

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Item2ItemRecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
	setItemInputs(guiItemStacks);
	setItemOutputs(guiItemStacks);
	guiItemStacks.set(ingredients);

	if (recipe.hasFluidBiproducts()) {
	    IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
	    setFluidOutputs(guiFluidStacks, recipe, 1, null);
	    guiFluidStacks.set(ingredients);
	}

    }

    @Override
    public void draw(Item2ItemRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {

	drawInputSlots(matrixStack);
	drawOutputSlots(matrixStack);
	drawStaticArrows(matrixStack);
	if (recipe.hasFluidBiproducts()) {
	    drawFluidOutputs(matrixStack);
	}
	drawAnimatedArrows(matrixStack);

	addDescriptions(matrixStack);
    }

    private static List<ItemStack> getItemOutputs(Item2ItemRecipe recipe) {
	List<ItemStack> outputs = new ArrayList<>();
	outputs.add(recipe.getResultItem());

	if (recipe.hasItemBiproducts()) {
	    outputs.addAll(Arrays.asList(recipe.getFullItemBiStacks()));
	}

	if (recipe.hasFluidBiproducts()) {
	    for (ProbableFluid fluid : recipe.getFluidBiproducts()) {
		ItemStack canister = new ItemStack(fluid.getFullStack().getFluid().getBucket(), 1);
		CapabilityUtils.fill(canister, fluid.getFullStack());
		outputs.add(canister);
	    }
	}

	return outputs;
    }

}
