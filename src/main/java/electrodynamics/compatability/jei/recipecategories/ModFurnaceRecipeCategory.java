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

    private int[] GUI_BACKGROUND_COORDS;
    private int[] PROCESSING_ARROW_COORDS;
    private int[] INPUT_ITEM_OFFSET;
    private int[] OUTPUT_ITEM_OFFSET;
    private int[] PROCESSING_ARROW_OFFSET;

    private int SMELT_TIME;
    private int TEXT_Y_HEIGHT;

    private String MOD_ID = "";
    private String RECIPE_GROUP = "";
    private ResourceLocation GUI_TEXTURE;

    private IDrawable BACKGROUND;
    private IDrawable ICON;

    private LoadingCache<Integer, IDrawableAnimated> CACHED_ARROWS;
    private StartDirection START_DIRECTION;

    private ItemStack INPUT_MACHINE;

    public ModFurnaceRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, String guiTexture, ItemStack inputMachine,
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

	MOD_ID = modID;
	RECIPE_GROUP = recipeGroup;
	GUI_TEXTURE = new ResourceLocation(MOD_ID, guiTexture);
	INPUT_MACHINE = inputMachine;

	SMELT_TIME = smeltTime;

	GUI_BACKGROUND_COORDS = inputCoordinates.get(0);
	PROCESSING_ARROW_COORDS = inputCoordinates.get(1);
	INPUT_ITEM_OFFSET = inputCoordinates.get(2);
	OUTPUT_ITEM_OFFSET = inputCoordinates.get(3);
	PROCESSING_ARROW_OFFSET = inputCoordinates.get(4);

	START_DIRECTION = arrowStartDirection;
	TEXT_Y_HEIGHT = textYHeight;

	ICON = guiHelper.createDrawableIngredient(INPUT_MACHINE);
	BACKGROUND = guiHelper.createDrawable(GUI_TEXTURE, GUI_BACKGROUND_COORDS[0], GUI_BACKGROUND_COORDS[1], GUI_BACKGROUND_COORDS[2],
		GUI_BACKGROUND_COORDS[3]);

	CACHED_ARROWS = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
	    @Override
	    public IDrawableAnimated load(Integer cookTime) {
		return guiHelper.drawableBuilder(GUI_TEXTURE, PROCESSING_ARROW_COORDS[0], PROCESSING_ARROW_COORDS[1], PROCESSING_ARROW_COORDS[2],
			PROCESSING_ARROW_COORDS[3]).buildAnimated(cookTime, START_DIRECTION, false);
	    }
	});

    }

    @Override
    public Class<? extends SmeltingRecipe> getRecipeClass() {
	return SmeltingRecipe.class;
    }

    @Override
    public Component getTitle() {
	return new TranslatableComponent("gui.jei.category." + RECIPE_GROUP);
    }

    @Override
    public IDrawable getBackground() {
	return BACKGROUND;
    }

    @Override
    public IDrawable getIcon() {
	return ICON;
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

	guiItemStacks.init(INPUT_SLOT, true, INPUT_ITEM_OFFSET[0], INPUT_ITEM_OFFSET[1]);
	guiItemStacks.init(OUTPUT_SLOT, false, OUTPUT_ITEM_OFFSET[0], OUTPUT_ITEM_OFFSET[1]);

	guiItemStacks.set(ingredients);

    }

    @Override
    public void draw(SmeltingRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
	IDrawableAnimated arrow = getArrow(recipe);
	arrow.draw(matrixStack, PROCESSING_ARROW_OFFSET[0], PROCESSING_ARROW_OFFSET[1]);

	drawSmeltTime(recipe, matrixStack, TEXT_Y_HEIGHT);
    }

    protected IDrawableAnimated getArrow(SmeltingRecipe recipe) {
	return CACHED_ARROWS.getUnchecked(SMELT_TIME);
    }

    protected void drawSmeltTime(SmeltingRecipe recipe, PoseStack matrixStack, int y) {

	int smeltTimeSeconds = SMELT_TIME / 20;
	TranslatableComponent timeString = new TranslatableComponent("gui.jei.category." + RECIPE_GROUP + ".info.power", smeltTimeSeconds);
	Minecraft minecraft = Minecraft.getInstance();
	Font fontRenderer = minecraft.font;
	int stringWidth = fontRenderer.width(timeString);
	fontRenderer.draw(matrixStack, timeString, BACKGROUND.getWidth() - stringWidth, y, 0xFF808080);

    }

}
