package electrodynamics.compatability.jei.recipecategories;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ElectrodynamicsRecipeCategory<T extends ElectrodynamicsRecipe> implements IRecipeCategory<T> {

    private String RECIPE_GROUP;
    private ResourceLocation GUI_TEXTURE;

    private IDrawable BACKGROUND;
    private IDrawable ICON;

    private int[] GUI_TEXTURE_SPECS;

    private ItemStack INPUT_MACHINE = null;

    private Class<T> RECIPE_CATEGORY_CLASS;

    private int Y_HEIGHT;
    private int ARROW_SMELT_TIME;

    public ElectrodynamicsRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
	    int[] guiTextureSize, Class<T> recipeCategoryClass, int yHeight, int arrowSmeltTime) {

	RECIPE_GROUP = recipeGroup;
	GUI_TEXTURE = new ResourceLocation(modID, guiTexture);
	GUI_TEXTURE_SPECS = guiTextureSize;

	INPUT_MACHINE = inputMachine;

	RECIPE_CATEGORY_CLASS = recipeCategoryClass;

	ICON = guiHelper.createDrawableIngredient(INPUT_MACHINE);
	BACKGROUND = guiHelper.createDrawable(GUI_TEXTURE, GUI_TEXTURE_SPECS[0], GUI_TEXTURE_SPECS[1], GUI_TEXTURE_SPECS[2], GUI_TEXTURE_SPECS[3]);

	Y_HEIGHT = yHeight;
	ARROW_SMELT_TIME = arrowSmeltTime;
    }

    @Override
    public Class<T> getRecipeClass() {
	return RECIPE_CATEGORY_CLASS;
    }

    @Override
    public String getTitle() {
	return new TranslationTextComponent("gui.jei.category." + RECIPE_GROUP).getString();
    }

    @Override
    public IDrawable getBackground() {
	return BACKGROUND;
    }

    @Override
    public IDrawable getIcon() {
	return ICON;
    }

    public ResourceLocation getGuiTexture() {
	return GUI_TEXTURE;
    }

    public String getRecipeGroup() {
	return RECIPE_GROUP;
    }

    public int getYHeight() {
	return Y_HEIGHT;
    }

    public int getArrowSmeltTime() {
	return ARROW_SMELT_TIME;
    }

}
