package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

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
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public abstract class O2ORecipeCategory extends ElectrodynamicsRecipeCategory<O2ORecipe> {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private int[] processingArrowCoords;
    private int[] processingArrowOffset;
    private int[] inputOffset;
    private int[] outputOffset;

    private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private StartDirection startDirection;

    protected O2ORecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
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

	processingArrowCoords = inputCoordinates.get(1);
	inputOffset = inputCoordinates.get(2);
	outputOffset = inputCoordinates.get(3);
	processingArrowOffset = inputCoordinates.get(4);

	startDirection = arrowStartDirection;

	cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
	    @Override
	    public IDrawableAnimated load(Integer cookTime) {
		return guiHelper.drawableBuilder(getGuiTexture(), processingArrowCoords[0], processingArrowCoords[1], processingArrowCoords[2],
			processingArrowCoords[3]).buildAnimated(cookTime, startDirection, false);
	    }
	});

    }

    @Override
    public void setIngredients(O2ORecipe recipe, IIngredients ingredients) {
	List<List<ItemStack>> temp = new ArrayList<>();
	temp.add(((CountableIngredient) recipe.getIngredients().get(0)).fetchCountedStacks());
	ingredients.setInputLists(VanillaTypes.ITEM, temp);
	ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, O2ORecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(INPUT_SLOT, true, inputOffset[0], inputOffset[1]);
	guiItemStacks.init(OUTPUT_SLOT, false, outputOffset[0], outputOffset[1]);

	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(O2ORecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow();
	arrow.draw(matrixStack, processingArrowOffset[0], processingArrowOffset[1]);

	drawSmeltTime(matrixStack, getYHeight());
    }

    protected IDrawableAnimated getArrow() {
	return cachedArrows.getUnchecked(getArrowSmeltTime());
    }

    protected void drawSmeltTime(PoseStack matrixStack, int y) {
	int smeltTimeSeconds = getArrowSmeltTime() / 20;
	TranslatableComponent timeString = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	Font fontRenderer = minecraft.font;
	float stringWidth = fontRenderer.width(timeString);
	fontRenderer.draw(matrixStack, timeString, getBackground().getWidth() - stringWidth, y, 0xFF808080);
    }

}
