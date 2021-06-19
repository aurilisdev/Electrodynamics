package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
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

public abstract class DO2ORecipeCategory extends ElectrodynamicsRecipeCategory<DO2ORecipe> {

    public static final int INPUT_1_SLOT = 0;
    public static final int INPUT_2_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    private int[] PROCESSING_ARROW_COORDS;
    private int[] INPUT_1_OFFSET;
    private int[] INPUT_2_OFFSET;
    private int[] OUTPUT_OFFSET;
    private int[] PROCESSING_ARROW_OFFSET;

    private LoadingCache<Integer, IDrawableAnimated> CACHED_ARROWS;
    private StartDirection START_DIRECTION;

    protected DO2ORecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
	    ArrayList<int[]> inputCoordinates, int smeltTime, StartDirection arrowStartDirection, int textYHeight) {
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
	 * third array is offset of first item
	 * 
	 * fourth array is offset of second item
	 * 
	 * fifth array is offset of output item
	 * 
	 * sixth array is offset of processing arrow
	 * 
	 * 
	 * array has following structure[xStart,yStart]
	 */

	super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates.get(0), DO2ORecipe.class, textYHeight, smeltTime);

	PROCESSING_ARROW_COORDS = inputCoordinates.get(1);
	INPUT_1_OFFSET = inputCoordinates.get(2);
	INPUT_2_OFFSET = inputCoordinates.get(3);
	OUTPUT_OFFSET = inputCoordinates.get(4);
	PROCESSING_ARROW_OFFSET = inputCoordinates.get(5);

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
    public void setIngredients(DO2ORecipe recipe, IIngredients ingredients) {
	List<List<ItemStack>> inputs = new ArrayList<>();
	inputs.add(((CountableIngredient) recipe.getIngredients().get(0)).fetchCountedStacks());
	inputs.add(((CountableIngredient) recipe.getIngredients().get(1)).fetchCountedStacks());
	ingredients.setInputLists(VanillaTypes.ITEM, inputs);
	ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, DO2ORecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(INPUT_1_SLOT, true, INPUT_1_OFFSET[0], INPUT_1_OFFSET[1]);
	guiItemStacks.init(INPUT_2_SLOT, true, INPUT_2_OFFSET[0], INPUT_2_OFFSET[1]);

	guiItemStacks.init(OUTPUT_SLOT, false, OUTPUT_OFFSET[0], OUTPUT_OFFSET[1]);

	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(DO2ORecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow(recipe);
	arrow.draw(matrixStack, PROCESSING_ARROW_OFFSET[0], PROCESSING_ARROW_OFFSET[1]);

	drawSmeltTime(recipe, matrixStack, getYHeight());
    }

    protected IDrawableAnimated getArrow(DO2ORecipe recipe) {
	return CACHED_ARROWS.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(DO2ORecipe recipe, MatrixStack matrixStack, int y) {
	int smeltTimeSeconds = getArrowSmeltTime() / 20;

	TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category." + getRecipeGroup() + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;
	int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
	fontRenderer.func_243248_b(matrixStack, timeString, getBackground().getWidth() - stringWidth, y, 0xFF808080);
    }
}
