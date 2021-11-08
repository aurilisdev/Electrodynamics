package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

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
import net.minecraft.client.gui.Font;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public abstract class ModFurnaceRecipeCategory implements IRecipeCategory<SmeltingRecipe> {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private int[] guiBackgroundCoords;
    private int[] processingArrowCoords;
    private int[] inputItemOffset;
    private int[] outputItemOffset;
    private int[] processingArrowOffset;

    private int smeltTime;
    private int textYHeight;

    private String modId = "";
    private String recipeGroup = "";
    private ResourceLocation guiTexture;

    private IDrawable background;
    private IDrawable icon;

    private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    private StartDirection startDirection;

    protected ModFurnaceRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
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
	 * third array is offset of input item
	 * 
	 * fourth array is offset of output item
	 * 
	 * fifth array is offset of processing arrow
	 * 
	 * 
	 * array has following structure[xStart,yStart]
	 */

	modId = modID;
	this.recipeGroup = recipeGroup;
	this.guiTexture = new ResourceLocation(modId, guiTexture);

	this.smeltTime = smeltTime;

	guiBackgroundCoords = inputCoordinates.get(0);
	processingArrowCoords = inputCoordinates.get(1);
	inputItemOffset = inputCoordinates.get(2);
	outputItemOffset = inputCoordinates.get(3);
	processingArrowOffset = inputCoordinates.get(4);

	startDirection = arrowStartDirection;
	this.textYHeight = textYHeight;

	icon = guiHelper.createDrawableIngredient(inputMachine);
	background = guiHelper.createDrawable(this.guiTexture, guiBackgroundCoords[0], guiBackgroundCoords[1], guiBackgroundCoords[2],
		guiBackgroundCoords[3]);

	cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
	    @Override
	    public IDrawableAnimated load(Integer cookTime) {
		return guiHelper.drawableBuilder(ModFurnaceRecipeCategory.this.guiTexture, processingArrowCoords[0], processingArrowCoords[1],
			processingArrowCoords[2], processingArrowCoords[3]).buildAnimated(cookTime, startDirection, false);
	    }
	});

    }

    @Override
    public Class<? extends SmeltingRecipe> getRecipeClass() {
	return SmeltingRecipe.class;
    }

    @Override
    public Component getTitle() {
	return new TranslatableComponent("gui.jei.category." + recipeGroup);
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
    public void setIngredients(SmeltingRecipe recipe, IIngredients ingredients) {
	NonNullList<Ingredient> inputs = NonNullList.create();
	inputs.addAll(recipe.getIngredients());

	ingredients.setInputIngredients(inputs);
	ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SmeltingRecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(INPUT_SLOT, true, inputItemOffset[0], inputItemOffset[1]);
	guiItemStacks.init(OUTPUT_SLOT, false, outputItemOffset[0], outputItemOffset[1]);

	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(SmeltingRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow();
	arrow.draw(matrixStack, processingArrowOffset[0], processingArrowOffset[1]);

	drawSmeltTime(matrixStack, textYHeight);
    }

    protected IDrawableAnimated getArrow() {
	return cachedArrows.getUnchecked(smeltTime);
    }

    protected void drawSmeltTime(PoseStack matrixStack, int y) {

	int smeltTimeSeconds = smeltTime / 20;
	TranslatableComponent timeString = new TranslatableComponent("gui.jei.category." + recipeGroup + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	Font fontRenderer = minecraft.font;
	float stringWidth = fontRenderer.width(timeString);
	fontRenderer.draw(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);

    }

}
