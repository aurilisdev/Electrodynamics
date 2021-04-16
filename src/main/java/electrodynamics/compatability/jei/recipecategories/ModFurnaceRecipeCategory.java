package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;
import electrodynamics.api.References;

import net.minecraft.item.crafting.FurnaceRecipe;
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

public abstract class ModFurnaceRecipeCategory implements IRecipeCategory<FurnaceRecipe>{
	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 2;
	
	private String MOD_ID = "";
	private String RECIPE_GROUP = "";
	
	private int[] GUIBackgroundCoords;
	private int[] ProcessingArrowCoords;
	private int[] InputOffset;
	private int[] OutputOffset;
	private int[] ProcessingArrowOffset;
	
	private int ARROW_SMELT_TIME;
	
	private IDrawable background;
	private IDrawable icon;
	
	private LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	private StartDirection startDirection;
	
	private int Y_HEIGHT;
	
	private ItemStack INPUT_MACHINE = null;
	
	private ResourceLocation GUI_TEXTURE;

	
	//private static final Logger logger = LogManager.getLogger(ElectrodynamicsPatches.MOD_ID);
	
	public ModFurnaceRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, 
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
		 * third array is offset of input item
		 * 
		 * fourth array is offset of output item
		 * 
		 * fifth array is offset of processing arrow
		 * 
		 * 
		 * array has following structure[xStart,yStart]
		 */
		
		
		this.MOD_ID = modID;
		this.RECIPE_GROUP = recipeGroup;
		this.GUI_TEXTURE = new ResourceLocation(MOD_ID,guiTexture);
		this.INPUT_MACHINE = inputMachine;
		
		this.ARROW_SMELT_TIME = smeltTime;
		
		GUIBackgroundCoords = inputCoordinates.get(0);
		ProcessingArrowCoords = inputCoordinates.get(1);
		InputOffset = inputCoordinates.get(2);
		OutputOffset = inputCoordinates.get(3);
		ProcessingArrowOffset = inputCoordinates.get(4);
		
		this.startDirection = arrowStartDirection;
		this.Y_HEIGHT = textYHeight;
		
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
	public Class<? extends FurnaceRecipe> getRecipeClass() {
		return FurnaceRecipe.class;
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
	public void setIngredients(FurnaceRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(recipe.getIngredients());
		
		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FurnaceRecipe recipe, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(INPUT_SLOT, true, InputOffset[0], InputOffset[1]);
		guiItemStacks.init(OUTPUT_SLOT, false, OutputOffset[0], OutputOffset[1]);
		
		guiItemStacks.set(ingredients);
		
	}
	
	
	@Override
	public void draw(FurnaceRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(matrixStack, ProcessingArrowOffset[0], ProcessingArrowOffset[1]);

		drawSmeltTime(recipe, matrixStack, Y_HEIGHT);
	}
	
	
	protected IDrawableAnimated getArrow(FurnaceRecipe recipe) 
	{
		return this.cachedArrows.getUnchecked(ARROW_SMELT_TIME);
	}
	
	
	protected void drawSmeltTime(FurnaceRecipe recipe, MatrixStack matrixStack, int y) 
	{

		int smeltTimeSeconds = ARROW_SMELT_TIME / 20;
		TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category."+RECIPE_GROUP+".info.power", smeltTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
		fontRenderer.func_243248_b(matrixStack,timeString,background.getWidth() - stringWidth,y,0xFF808080);
		
	}
	
}
