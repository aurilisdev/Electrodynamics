/*
 * This is here to prep the plugin for switching to JSONs. For now it's just kinda here vibing.
 * 
 */
package electrodynamics.compatability.jei.recipecategories;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ElectrodynamicsRecipeCategory implements IRecipeCategory<ElectrodynamicsRecipe>{
	
	public String RECIPE_GROUP;
	public ResourceLocation GUI_TEXTURE;
	
	public IDrawable background;
	private IDrawable icon;
	
	public int[] GUI_TEXTURE_SPECS;
	
	private ItemStack INPUT_MACHINE = null;
	
	
	public ElectrodynamicsRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, 
			String guiTexture, ItemStack inputMachine, int[] guiTextureSize) {
		
		this.RECIPE_GROUP = recipeGroup;
		this.GUI_TEXTURE = new ResourceLocation(modID,guiTexture);
		this.GUI_TEXTURE_SPECS = guiTextureSize;
		
		this.INPUT_MACHINE = inputMachine;
		
		this.icon = guiHelper.createDrawableIngredient(INPUT_MACHINE);
		this.background = guiHelper.createDrawable(GUI_TEXTURE,GUI_TEXTURE_SPECS[0],GUI_TEXTURE_SPECS[1],
				GUI_TEXTURE_SPECS[2],GUI_TEXTURE_SPECS[3]);
		
	}
	
	@Override
	public Class<? extends ElectrodynamicsRecipe> getRecipeClass() {
		return ElectrodynamicsRecipe.class;
	}

	
	@Override
	public String getTitle() {
		return new TranslationTextComponent("gui.jei.category." + RECIPE_GROUP).getString();
	}

	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public IDrawable getIcon() {
		return icon;
	}
	
	@Override
	public void setIngredients(ElectrodynamicsRecipe recipe, IIngredients ingredients) {;}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ElectrodynamicsRecipe recipe, IIngredients ingredients) {;}
	
	@Override
	public void draw(ElectrodynamicsRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {;}
	
	/**
	 * Returns a list of Ingredients representing the inputs for the recipe
	 * 
	 * @param recipe the recipe being initialized
	 * @return a List of Ingredients
	 */
	public List<Ingredient> getIngredients(ElectrodynamicsRecipe recipe){
		return null;
	}
	
	/**
	 * Returns a List of Lists of Ingredients
	 * Allows for cycling of items in some specialized recipes
	 * 
	 * @param recipe the recipe being initialized
	 * @return a List of List Ingredient
	 */
	public List<List<Ingredient>> getIngredientLists(ElectrodynamicsRecipe recipe){
		return null;
	}
	
	/**
	 * Returns a List of ItemStack representing the outputs for the recipe
	 * 
	 * @param recipe the recipe being initialized
	 * @return a List of ItemStack
	 */
	public List<ItemStack> getOutputs(ElectrodynamicsRecipe recipe){
		return null;
	}
	
	/**
	 * Returns a List of List ItemStack representing the outputs for the recipe
	 * Allows cycling of output items in some specialized recipes
	 * 
	 * @param recipe the recipe being initialized
	 * @return a List of ItemStack
	 */
	public List<List<ItemStack>> getOutputLists(ElectrodynamicsRecipe recipe){
		return null;
	}

}
