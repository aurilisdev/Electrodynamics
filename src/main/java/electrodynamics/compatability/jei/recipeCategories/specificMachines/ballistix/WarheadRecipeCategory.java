package electrodynamics.compatability.jei.recipeCategories.specificMachines.ballistix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipeCategories.DO2OProcessingRecipeCategory;
import electrodynamics.compatability.jei.recipeCategories.psuedoRecipes.PsuedoRecipes;

import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class WarheadRecipeCategory extends DO2OProcessingRecipeCategory{

	//JEI Window Parameters
	private static int[] GUIBackground = {0,0,132,58};
	private static int[] ProcessingArrowLocation = {0,0,10,10};
	
	private static int[] Input1ItemOffset = {89,7};
	private static int[] Input2ItemOffset = {89,33};
	private static int[] OutputItemOffset = {0,0};
	private static int[] ProcessingArrowOffset = {0,0};
	
	
	private static String modID =  References.ID;
	private static String recipeGroup = "warhead_template";
	private static String guiTexture = "textures/gui/jei/warhead_template_gui.png";
	private static ItemStack inputMachine = new ItemStack(ballistix.DeferredRegisters.blockMissileSilo);
	private static ArrayList<int[]> inputCoordinates = 
			new ArrayList<>(Arrays.asList(
					 					  GUIBackground,
					 					  ProcessingArrowLocation,
					 					  Input1ItemOffset,
					 					  Input2ItemOffset,
					 					  OutputItemOffset,
					 					  ProcessingArrowOffset
			));
	private static int smeltTime = 50;
	private static int textYHeight =48;
	private static IDrawableAnimated.StartDirection arrowStartDirection = IDrawableAnimated.StartDirection.LEFT;
	
	public static ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	public WarheadRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime,arrowStartDirection,textYHeight);
	}
	
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}
	
	
	@Override
	public void setIngredients(DO2OProcessingRecipe recipe, IIngredients ingredients) {

		ingredients.setInputLists(VanillaTypes.ITEM,recipeInput(recipe));
	
	}

	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DO2OProcessingRecipe recipe, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(INPUT_2_SLOT, true, Input1ItemOffset[0], Input1ItemOffset[1]);
		guiItemStacks.init(INPUT_1_SLOT, true, Input2ItemOffset[0], Input2ItemOffset[1]);
		
		guiItemStacks.set(ingredients);
		
	}
	
	
	@Override
	public void draw(DO2OProcessingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) 
	{
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(matrixStack, ProcessingArrowOffset[0], ProcessingArrowOffset[1]);

		drawSmeltTime(recipe, matrixStack, textYHeight);
	}
	
	
	@Override
	protected void drawSmeltTime(DO2OProcessingRecipe recipe, MatrixStack matrixStack, int y) 
	{
		int smeltTimeSeconds = smeltTime / 20;
		
		TranslationTextComponent missileString = new TranslationTextComponent("gui.jei.category."+recipeGroup+ ".info.missile", smeltTimeSeconds);
		TranslationTextComponent explosiveString = new TranslationTextComponent("gui.jei.category."+recipeGroup+ ".info.explosive", smeltTimeSeconds);
		
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontRenderer = minecraft.fontRenderer;
		
		int missileWidth = fontRenderer.getStringPropertyWidth(missileString);
		int explosiveWidth = fontRenderer.getStringPropertyWidth(explosiveString);
		
		fontRenderer.func_243248_b(matrixStack,missileString,GUIBackground[2] - missileWidth - 46,y-37,0xFF333333);
		fontRenderer.func_243248_b(matrixStack,explosiveString,GUIBackground[2] - explosiveWidth - 46,y-10,0xFF333333);
	}
	
	
	private List<List<ItemStack>> recipeInput(DO2OProcessingRecipe recipe){
		
		List<List<ItemStack>> inputSlots = new ArrayList<>();
		inputSlots.add(PsuedoRecipes.BALLISTIX_ITEMS.get(0));
		inputSlots.add(PsuedoRecipes.BALLISTIX_ITEMS.get(1));
		
		return inputSlots;
		
	}
	
	


}
