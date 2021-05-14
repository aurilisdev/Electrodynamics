package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class O2OProcessingRecipeCategory implements IRecipeCategory<O2OProcessingRecipe> {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private String MOD_ID = "";
    private String RECIPE_GROUP = "";

    private int[] GUIBackgroundCoords;
    private int[] ProcessingArrowCoords;
    private int[] InputOffset;
    private int[] OutputOffset;
    private int[] ProcessingArrowOffset;

    public int ARROW_SMELT_TIME;

    private IDrawable background;
    private IDrawable icon;

    private LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    private ItemStack INPUT_MACHINE = null;

    private ResourceLocation GUI_TEXTURE;

    private StartDirection startDirection;

    private int Y_HEIGHT;

    // private static final Logger logger =
    // LogManager.getLogger(ElectrodynamicsPatches.MOD_ID);

    public O2OProcessingRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
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
	MOD_ID = modID;
	RECIPE_GROUP = recipeGroup;
	GUI_TEXTURE = new ResourceLocation(MOD_ID, guiTexture);
	INPUT_MACHINE = inputMachine;

	ARROW_SMELT_TIME = smeltTime;

	GUIBackgroundCoords = inputCoordinates.get(0);
	ProcessingArrowCoords = inputCoordinates.get(1);
	InputOffset = inputCoordinates.get(2);
	OutputOffset = inputCoordinates.get(3);
	ProcessingArrowOffset = inputCoordinates.get(4);

	icon = guiHelper.createDrawableIngredient(INPUT_MACHINE);
	background = guiHelper.createDrawable(GUI_TEXTURE, GUIBackgroundCoords[0], GUIBackgroundCoords[1], GUIBackgroundCoords[2],
		GUIBackgroundCoords[3]);

	Y_HEIGHT = textYHeight;
	startDirection = arrowStartDirection;

	cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
	    @Override
	    public IDrawableAnimated load(Integer cookTime) {
		return guiHelper.drawableBuilder(GUI_TEXTURE, ProcessingArrowCoords[0], ProcessingArrowCoords[1], ProcessingArrowCoords[2],
			ProcessingArrowCoords[3]).buildAnimated(cookTime, startDirection, false);
	    }
	});

    }

    @Override
    public Class<? extends O2OProcessingRecipe> getRecipeClass() {
	return O2OProcessingRecipe.class;
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
    public void setIngredients(O2OProcessingRecipe recipe, IIngredients ingredients) {
	NonNullList<Ingredient> inputs = NonNullList.create();
	inputs.addAll(getIngredients(recipe));

	ingredients.setInputIngredients(inputs);
	ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, O2OProcessingRecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(INPUT_SLOT, true, InputOffset[0], InputOffset[1]);
	guiItemStacks.init(OUTPUT_SLOT, false, OutputOffset[0], OutputOffset[1]);

	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(O2OProcessingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow(recipe);
	arrow.draw(matrixStack, ProcessingArrowOffset[0], ProcessingArrowOffset[1]);

	drawSmeltTime(recipe, matrixStack, Y_HEIGHT);
    }

    public NonNullList<Ingredient> getIngredients(O2OProcessingRecipe recipe) {
	Ingredient ingredient1 = Ingredient.fromStacks(recipe.getInput());
	NonNullList<Ingredient> ingredients = NonNullList.create();
	ingredients.add(ingredient1);
	return ingredients;
    }

    protected IDrawableAnimated getArrow(O2OProcessingRecipe recipe) {
	return cachedArrows.getUnchecked(ARROW_SMELT_TIME);
    }

    protected void drawSmeltTime(O2OProcessingRecipe recipe, MatrixStack matrixStack, int y) {

	int smeltTimeSeconds = ARROW_SMELT_TIME / 20;
	TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category." + RECIPE_GROUP + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	FontRenderer fontRenderer = minecraft.fontRenderer;
	int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
	fontRenderer.func_243248_b(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);

    }
}
