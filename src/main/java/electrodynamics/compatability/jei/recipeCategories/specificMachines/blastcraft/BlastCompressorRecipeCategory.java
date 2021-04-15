package electrodynamics.compatability.jei.recipeCategories.specificMachines.blastcraft;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipeCategories.O2OProcessingRecipeCategory;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import blastcraft.DeferredRegisters;

public class BlastCompressorRecipeCategory extends O2OProcessingRecipeCategory{

	//JEI Window Parameters
	private static int[] GUIBackground = {0,0,132,58};
	private static int[] ProcessingArrowLocation = {132,0,24,17};
	
	private static int[] InputItemOffset = {22,21};
	private static int[] OutputItemOffset = {82,22};
	private static int[] ProcessingArrowOffset = {46,23};
	
	
	private static String modID =  References.ID;
	private static String recipeGroup = "blast_compressor";
	private static String guiTexture = "textures/gui/jei/o2o_recipe_gui.png";
	private static ItemStack inputMachine = new ItemStack(DeferredRegisters.blockBlastCompressor.getBlock());
	private static ArrayList<int[]> inputCoordinates = 
			new ArrayList<>(Arrays.asList(
										  GUIBackground,
										  ProcessingArrowLocation,
										  InputItemOffset,
										  OutputItemOffset,
										  ProcessingArrowOffset
			));
	private static int smeltTime = 50;
	private static int textYHeight =48;
	private static IDrawableAnimated.StartDirection arrowStartDirection = IDrawableAnimated.StartDirection.LEFT;
	
	public static ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	public BlastCompressorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime,textYHeight,arrowStartDirection);
	}
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}

}
