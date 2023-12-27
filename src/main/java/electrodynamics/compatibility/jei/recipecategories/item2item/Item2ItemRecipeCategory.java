package electrodynamics.compatibility.jei.recipecategories.item2item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.RecipeType;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.prefab.utilities.CapabilityUtils;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class Item2ItemRecipeCategory<T extends Item2ItemRecipe> extends AbstractRecipeCategory<T> {

	/*
	 * DOCUMENTATION NOTES:
	 * 
	 * > Output items supercede buckets in position > All biproducts will be included with the outputSlots field > All fluid bucket output slots will be incled with the outputSlots field
	 */

	public Item2ItemRecipeCategory(IGuiHelper guiHelper, ITextComponent title, ItemStack inputMachine, BackgroundObject bWrap, RecipeType<T> recipeType, int animTime) {
		super(guiHelper, title, inputMachine, bWrap, recipeType, animTime);
	}

	@Override
	public List<List<ItemStack>> getItemInputs(Item2ItemRecipe recipe) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		recipe.getCountedIngredients().forEach(h -> inputs.add(h.fetchCountedStacks()));
		return inputs;
	}

	@Override
	public List<ItemStack> getItemOutputs(Item2ItemRecipe recipe) {
		List<ItemStack> outputs = new ArrayList<>();
		outputs.add(recipe.getResultItem());

		if (recipe.hasItemBiproducts()) {
			outputs.addAll(Arrays.asList(recipe.getFullItemBiStacks()));
		}

		if (recipe.hasFluidBiproducts()) {
			for (ProbableFluid fluid : recipe.getFluidBiproducts()) {
				ItemStack canister = new ItemStack(fluid.getFullStack().getFluid().getBucket(), 1);
				if (CapabilityUtils.hasFluidItemCap(canister)) {

					IFluidHandlerItem handler = CapabilityUtils.getFluidHandlerItem(canister);

					handler.fill(fluid.getFullStack(), FluidAction.EXECUTE);

					canister = handler.getContainer();

				}
				outputs.add(canister);
			}
		}
		return outputs;
	}

}
