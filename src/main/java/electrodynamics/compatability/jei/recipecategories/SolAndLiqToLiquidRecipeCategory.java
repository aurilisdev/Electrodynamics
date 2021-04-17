package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoSolAndLiqToLiquidRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
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
import net.minecraftforge.fluids.FluidStack;

public abstract class SolAndLiqToLiquidRecipeCategory implements IRecipeCategory<PsuedoSolAndLiqToLiquidRecipe>{
	public static final int ITEM_INPUT_SLOT = 0;
	public static final int INPUT_FLUID_SLOT = 1;
	public static final int INPUT_FLUID_STACK_SLOT = 2;
	public static final int OUTPUT_FLUID_SLOT = 3;
	
	private int[] GUIBackgroundCoords;
	private int[] MajorProcessingArrowCoords;
	private int[] MinorProcessingArrowCoords;
	
	private int[] InputItemOffset;
	private int[] InputFluidBucketOffset;
	private int[] InputFluidTank;
	private int[] OutputFluidTank;
	
	private int[] MajorProcessingArrowOffset;
	private int[] MinorProcessingArrowOffset;
	
	private int[] RightTankFluidBar;
	
	private String RECIPE_GROUP;
	
	private int ARROW_SMELT_TIME;
	
	private IDrawable background;
	private IDrawable icon;
	
	private LoadingCache<Integer, ArrayList<IDrawableAnimated>> cachedArrows;
	private LoadingCache<Integer,IDrawableStatic> cachedFluidBars;
	
	private ResourceLocation GUI_TEXTURE;
	
	private StartDirection majorArrowStartDirection;
	private StartDirection minorArrowStartDirection;
	
	private int Y_HEIGHT;
	
	private IDrawableStatic outputFluidBar;

	
	//private static final Logger logger = LogManager.getLogger(Electrodynamics);
	
	public SolAndLiqToLiquidRecipeCategory(IGuiHelper guiHelper,String modID, String recipeGroup, 
			String guiTexture, ItemStack inputMachine, ArrayList<int[]> inputCoordinates, int smeltTime,
			StartDirection majorArrowDirection, StartDirection minorArrowDirection, int textYHeight) {
		
		/*
		 * INPUT COORDIANTES Layout
		 * 
		 * first array is gui background
		 * 
		 * second array is first processing arrow
		 * 
		 * third array is second processing arrow
		 * 
		 * array has following structure [startX,startY,xOffsec,yOffset]
		 * 
		 * 
		 * 
		 * 
		 * fourth array is offset of input item
		 * 
		 * fifth array is offset of input fluid bucket 
		 * 
		 * sixth array is offset of input fluid tank [startx,endy,width,height, maxcapacity]
		 * 
		 * seventh array is offset of output fluid tank [startx,endy,width,height, maxcapacity]
		 * 
		 * 
		 *
		 *
		 * eighth array is offset of Major Processing arrow
		 * ninth array is offset of Minor Processing arrow
		 * 
		 * 10th array is the output tank's fluid bar [startx,starty,width,height]
		 * 
		 * 
		 */
		this.RECIPE_GROUP = recipeGroup;
		this.ARROW_SMELT_TIME = smeltTime;
		this.GUI_TEXTURE = new ResourceLocation(modID,guiTexture);
		
		
		this.GUIBackgroundCoords = inputCoordinates.get(0);
		this.MajorProcessingArrowCoords = inputCoordinates.get(1);
		this.MinorProcessingArrowCoords = inputCoordinates.get(2);
		
		this.InputItemOffset = inputCoordinates.get(3);
		this.InputFluidBucketOffset = inputCoordinates.get(4);
		this.InputFluidTank = inputCoordinates.get(5);
		this.OutputFluidTank = inputCoordinates.get(6);
		
		this.MajorProcessingArrowOffset = inputCoordinates.get(7);
		this.MinorProcessingArrowOffset = inputCoordinates.get(8);
		
		this.RightTankFluidBar = inputCoordinates.get(9);
		
		this.majorArrowStartDirection = majorArrowDirection;
		this.minorArrowStartDirection = minorArrowDirection;
		
		this.Y_HEIGHT = textYHeight;
		
		icon = guiHelper.createDrawableIngredient(inputMachine);
		background = guiHelper.createDrawable(GUI_TEXTURE,GUIBackgroundCoords[0],GUIBackgroundCoords[1]
				,GUIBackgroundCoords[2],GUIBackgroundCoords[3]);
		

		this.cachedArrows = CacheBuilder.newBuilder()
				.maximumSize(25)
				.build(new CacheLoader<Integer, ArrayList<IDrawableAnimated>>() 
				{

					@Override
					public ArrayList<IDrawableAnimated> load(Integer cookTime) {
						
						IDrawableAnimated largeArrow = guiHelper.drawableBuilder(
								GUI_TEXTURE, 
								MajorProcessingArrowCoords[0],
								MajorProcessingArrowCoords[1], 
								MajorProcessingArrowCoords[2], 
								MajorProcessingArrowCoords[3]
								).buildAnimated(cookTime, majorArrowStartDirection, false);
						
						IDrawableAnimated smallArrow = guiHelper.drawableBuilder(
								GUI_TEXTURE, 
								MinorProcessingArrowCoords[0], 
								MinorProcessingArrowCoords[1], 
								MinorProcessingArrowCoords[2], 
								MinorProcessingArrowCoords[3]
								).buildAnimated(cookTime, minorArrowStartDirection, false);
						
						IDrawableAnimated[] arrows = {largeArrow,smallArrow};
						return new ArrayList<IDrawableAnimated>(Arrays.asList(arrows));
					}
				});
		
		//Support for fluids without textures
		this.cachedFluidBars = CacheBuilder.newBuilder()
				.maximumSize(25)
				.build(new CacheLoader<Integer, IDrawableStatic>() 
				{

					@Override
					public IDrawableStatic load(Integer cookTime) {
						
						outputFluidBar = guiHelper.drawableBuilder(
								GUI_TEXTURE,
								RightTankFluidBar[0],
								RightTankFluidBar[1],
								RightTankFluidBar[2],
								RightTankFluidBar[3]
								).build();
						
						return outputFluidBar;
					}
		});

				
	}

	
	@Override
	public Class<? extends PsuedoSolAndLiqToLiquidRecipe> getRecipeClass() {
		return PsuedoSolAndLiqToLiquidRecipe.class;
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
	public void setIngredients(PsuedoSolAndLiqToLiquidRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(getIngredients(recipe));
		
		ingredients.setInputIngredients(inputs);
		ingredients.setInputs(VanillaTypes.FLUID, getFluids(recipe));
		ingredients.setOutput(VanillaTypes.FLUID, recipe.FLUID_BUCKET_OUTPUT);
	}

	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PsuedoSolAndLiqToLiquidRecipe recipe, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		guiItemStacks.init(ITEM_INPUT_SLOT, true, InputItemOffset[0], InputItemOffset[1]);
		guiItemStacks.init(INPUT_FLUID_SLOT, true, InputFluidBucketOffset[0], InputFluidBucketOffset[1]);
		
		int fluidInputAmount = recipe.FLUID_STACK_INPUT.getAmount();
		int fluidOutputAmount = recipe.FLUID_BUCKET_OUTPUT.getAmount();
		
		int leftHeightOffset = (int)(Math.ceil((fluidInputAmount/(float)InputFluidTank[4]) * InputFluidTank[3]));
		int leftStartY = InputFluidTank[1] - leftHeightOffset + 1; 
		
		int rightHeightOffset = (int)(Math.ceil((fluidOutputAmount/(float)OutputFluidTank[4]) * OutputFluidTank[3]));
		int rightStartY = OutputFluidTank[1] - rightHeightOffset + 1;
		
		
		
		guiFluidStacks.init(INPUT_FLUID_STACK_SLOT, true, 
				InputFluidTank[0], 
				leftStartY,
				InputFluidTank[2],
				leftHeightOffset,
				fluidInputAmount
				,true,null);
		guiFluidStacks.init(OUTPUT_FLUID_SLOT, false, 
				OutputFluidTank[0], 
				rightStartY,
				OutputFluidTank[2],
				rightHeightOffset,
				fluidOutputAmount,
				true,null);
		
		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
		
	}
	
	
	@Override
	public void draw(PsuedoSolAndLiqToLiquidRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		
		int fluidOutputAmount = recipe.FLUID_BUCKET_OUTPUT.getAmount();
		
		int rightHeightOffset = (int)(Math.ceil((fluidOutputAmount/(float)OutputFluidTank[4]) * OutputFluidTank[3]));
		int rightBarStartHeight = OutputFluidTank[1] - OutputFluidTank[3] + 1;
		
		getFluidBar(recipe).draw(matrixStack, 
				OutputFluidTank[0],
				rightBarStartHeight,
				OutputFluidTank[3] - rightHeightOffset, 
				0,0,0);
				
		ArrayList<IDrawableAnimated> arrows = getArrows(recipe);
		
		arrows.get(0).draw(matrixStack, MajorProcessingArrowOffset[0], MajorProcessingArrowOffset[1]);
		arrows.get(1).draw(matrixStack, MinorProcessingArrowOffset[0], MinorProcessingArrowOffset[1]);

		drawSmeltTime(recipe, matrixStack, Y_HEIGHT);
	}
	
	
	protected ArrayList<IDrawableAnimated> getArrows(PsuedoSolAndLiqToLiquidRecipe recipe) 
	{
		return this.cachedArrows.getUnchecked(ARROW_SMELT_TIME);
	}
	
	
	protected IDrawableStatic getFluidBar(PsuedoSolAndLiqToLiquidRecipe recipe) {
		return this.cachedFluidBars.getUnchecked(ARROW_SMELT_TIME);
	}
	
	
	protected void drawSmeltTime(PsuedoSolAndLiqToLiquidRecipe recipe, MatrixStack matrixStack, int y) 
	{
		int smeltTimeSeconds = ARROW_SMELT_TIME / 20;
		TranslationTextComponent outputString = new TranslationTextComponent("gui.jei.category."+RECIPE_GROUP+".info.power", smeltTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		int outputWidth = fontRenderer.getStringPropertyWidth(outputString);
		fontRenderer.func_243248_b(matrixStack,outputString, background.getWidth() - outputWidth,y+ 8, 0xFF808080);
			
	}
	
	
	public NonNullList<FluidStack> getFluids(PsuedoSolAndLiqToLiquidRecipe recipe){
		NonNullList<FluidStack> fluids = NonNullList.create();
		fluids.add(recipe.FLUID_STACK_INPUT);
		return fluids;
	}
	
	
	public NonNullList<Ingredient> getIngredients(PsuedoSolAndLiqToLiquidRecipe recipe){
		NonNullList<Ingredient> ingredients = NonNullList.create();
		ingredients.add(recipe.ITEM_INPUT);
		ingredients.add(recipe.FLUID_BUCKET_INPUT);

		return ingredients;
	}
}
