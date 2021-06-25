package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class O2ORecipeCategory extends ElectrodynamicsRecipeCategory<O2ORecipe> {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private int[] PROCESSING_ARROW_COORDS;
    private int[] INPUT_OFFSET;
    private int[] OUTPUT_OFFSET;
    private int[] PROCESSING_ARROW_OFFSET;

    private LoadingCache<Integer, IDrawableAnimated> CACHED_ARROWS;
    private StartDirection START_DIRECTION;

    public O2ORecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
	    ArrayList<int[]> inputCoordinates, int smeltTime, int textYHeight, IDrawableAnimated.StartDirection arrowStartDirection) {

	/*
	 * INPUT COORDIANTES Layout
	 * 
	 * first array is gui background
	 * 
	 * second array is processing arrow
	 * 
	 * array has following structure [startX,startY,xOffsec,yOffset]
	 * 
	 * 
	 * 
	 * 
	 * third array is offset of input item
	 * 
	 * fourth array is offset of output item
	 * 
	 * fifth array is offset of processing arrow
	 * 
	 * 
	 * array has following structure[xStart,yStart]
	 */

	super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates.get(0), O2ORecipe.class, textYHeight, smeltTime);

	PROCESSING_ARROW_COORDS = inputCoordinates.get(1);
	INPUT_OFFSET = inputCoordinates.get(2);
	OUTPUT_OFFSET = inputCoordinates.get(3);
	PROCESSING_ARROW_OFFSET = inputCoordinates.get(4);

	START_DIRECTION = arrowStartDirection;

	CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
	    @Override
	    public IDrawableAnimated load(Integer cookTime) {
		return guiHelper.drawableBuilder(getGuiTexture(), PROCESSING_ARROW_COORDS[0], PROCESSING_ARROW_COORDS[1], PROCESSING_ARROW_COORDS[2],
			PROCESSING_ARROW_COORDS[3]).buildAnimated(cookTime, START_DIRECTION, false);
	    }
	});

    }

    @Override
    public void setIngredients(O2ORecipe recipe, IIngredients ingredients) {
	List<List<ItemStack>> temp = new ArrayList<>();
	temp.add(((CountableIngredient) recipe.getIngredients().get(0)).fetchCountedStacks());
	ingredients.setInputLists(VanillaTypes.ITEM, temp);
	ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, O2ORecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(INPUT_SLOT, true, INPUT_OFFSET[0], INPUT_OFFSET[1]);
	guiItemStacks.init(OUTPUT_SLOT, false, OUTPUT_OFFSET[0], OUTPUT_OFFSET[1]);

	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(O2ORecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow(recipe);
	arrow.draw(matrixStack, PROCESSING_ARROW_OFFSET[0], PROCESSING_ARROW_OFFSET[1]);

	drawSmeltTime(recipe, matrixStack, getYHeight());
    }

    protected IDrawableAnimated getArrow(O2ORecipe recipe) {
	return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(O2ORecipe recipe, MatrixStack matrixStack, int y) {

	int smeltTimeSeconds = getArrowSmeltTime() / 20;
	TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;
	int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
	fontRenderer.func_243248_b(matrixStack, timeString, getBackground().getWidth() - stringWidth, y, 0xFF808080);

    }

}
