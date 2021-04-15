package electrodynamics.compatability.jei.recipeCategories;

import java.util.ArrayList;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
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


public abstract class DO2OProcessingRecipeCategory implements IRecipeCategory<DO2OProcessingRecipe>{

	public static final int INPUT_1_SLOT = 0;
	public static final int INPUT_2_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	private String RECIPE_GROUP;
	
	private int[] GUIBackgroundCoords;
	private int[] ProcessingArrowCoords;
	private int[] Input1Offset;
	private int[] Input2Offset;
	private int[] OutputOffset;
	private int[] ProcessingArrowOffset;
	
	private int ARROW_SMELT_TIME;
	
	private IDrawable background;
	private IDrawable icon;
	
	private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	private StartDirection startDirection;
	
	private ItemStack INPUT_MACHINE = null;
	
	private ResourceLocation GUI_TEXTURE;
	
	private int Y_HEIGHT;
	

	
	public DO2OProcessingRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, 
			String guiTexture, ItemStack inputMachine, ArrayList<int[]> inputCoordinates, int smeltTime,
			StartDirection arrowStartDirection, int textYHeight) {
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
		
		this.RECIPE_GROUP = recipeGroup;
		this.INPUT_MACHINE = inputMachine;
		
		this.ARROW_SMELT_TIME = smeltTime;
		
		this.GUIBackgroundCoords = inputCoordinates.get(0);
		this.ProcessingArrowCoords = inputCoordinates.get(1);
		this.Input1Offset = inputCoordinates.get(2);
		this.Input2Offset = inputCoordinates.get(3);
		this.OutputOffset = inputCoordinates.get(4);
		this.ProcessingArrowOffset = inputCoordinates.get(5);
		
		this.startDirection = arrowStartDirection;
		
		this.Y_HEIGHT = textYHeight;
		
		this.GUI_TEXTURE = new ResourceLocation(modID,guiTexture);
		
		this.icon = guiHelper.createDrawableIngredient(INPUT_MACHINE);
		this.background = guiHelper.createDrawable(GUI_TEXTURE,GUIBackgroundCoords[0],GUIBackgroundCoords[1],
				GUIBackgroundCoords[2],GUIBackgroundCoords[3]);
		
		
		

		this.cachedArrows = CacheBuilder.newBuilder()
				.maximumSize(25)
				.build(new CacheLoader<Integer, IDrawableAnimated>() 
				{
					@Override
					public IDrawableAnimated load(Integer cookTime) {
						return guiHelper.drawableBuilder(GUI_TEXTURE, ProcessingArrowCoords[0], ProcessingArrowCoords[1], 
								ProcessingArrowCoords[2], ProcessingArrowCoords[3])
							.buildAnimated(cookTime, startDirection, false);
					}
				});
	
	}

	
	@Override
	public Class<? extends DO2OProcessingRecipe> getRecipeClass() {
		return DO2OProcessingRecipe.class;
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
	public void setIngredients(DO2OProcessingRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(getIngredients(recipe));
		
		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DO2OProcessingRecipe recipe, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(INPUT_1_SLOT, true, Input1Offset[0], Input1Offset[1]);
		guiItemStacks.init(INPUT_2_SLOT, true, Input2Offset[0], Input2Offset[1]);
		
		guiItemStacks.init(OUTPUT_SLOT, false, OutputOffset[0], OutputOffset[1]);
		
		guiItemStacks.set(ingredients);
		
	}
	
	
	@Override
	public void draw(DO2OProcessingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(matrixStack, ProcessingArrowOffset[0], ProcessingArrowOffset[1]);

		drawSmeltTime(recipe, matrixStack, Y_HEIGHT);
	}
	
	
	private NonNullList<Ingredient> getIngredients(DO2OProcessingRecipe recipe){
		Ingredient ingredient1 = Ingredient.fromStacks(recipe.getInput1());
		Ingredient ingredient2 = Ingredient.fromStacks(recipe.getInput2());
		NonNullList<Ingredient> ingredients = NonNullList.create();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		return ingredients;
	}
	
	
	protected IDrawableAnimated getArrow(DO2OProcessingRecipe recipe) 
	{
		return this.cachedArrows.getUnchecked(ARROW_SMELT_TIME);
	}
	
	
	protected void drawSmeltTime(DO2OProcessingRecipe recipe, MatrixStack matrixStack, int y) 
	{
		int smeltTimeSeconds = ARROW_SMELT_TIME / 20;
		
		TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category."+RECIPE_GROUP+ ".info.power", smeltTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
		fontRenderer.func_243248_b(matrixStack,timeString,background.getWidth() - stringWidth,y,0xFF808080);
	}
}
