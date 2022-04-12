package electrodynamics.compatibility.jei.recipecategories.item2item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.prefab.utilities.CapabilityUtils;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.world.item.ItemStack;

public abstract class Item2ItemRecipeCategory<T extends Item2ItemRecipe> extends ElectrodynamicsRecipeCategory<T> {

	/*
	 * DOCUMENTATION NOTES:
	 * 
	 * > Output items supercede buckets in position > All biproducts will be included with the outputSlots field > All fluid bucket output slots will be incled with the outputSlots field
	 */

	protected Item2ItemRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine, BackgroundWrapper bWrap, Class<T> recipeClass, int animTime) {
		super(guiHelper, modID, recipeGroup, inputMachine, bWrap, recipeClass, animTime);
	}

	@Override
	public List<List<ItemStack>> getItemInputs(ElectrodynamicsRecipe electro) {
		Item2ItemRecipe recipe = (Item2ItemRecipe) electro;
		List<List<ItemStack>> inputs = new ArrayList<>();
		recipe.getCountedIngredients().forEach(h -> inputs.add(h.fetchCountedStacks()));
		return inputs;
	}

	@Override
	public List<ItemStack> getItemOutputs(ElectrodynamicsRecipe electro) {
		Item2ItemRecipe recipe = (Item2ItemRecipe) electro;
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
