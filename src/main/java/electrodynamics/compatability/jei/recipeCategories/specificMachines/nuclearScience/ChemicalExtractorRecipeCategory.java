package electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipeCategories.SolAndLiqToSolidRecipeCategory;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nuclearscience.DeferredRegisters;

public class ChemicalExtractorRecipeCategory extends SolAndLiqToSolidRecipeCategory{
	//JEI Window Parameters
	private static final int[] GUIBackground = {0,0,132,64};
	private static final int[] MajorProcessingArrowLocation = {0, 65, 68, 15};
	private static final int[] MinorProcessingArrowLocation = {0, 80, 20, 15};
	
	private static final int[] InputItemOffset = {58, 16};
	private static final int[] InputFluidBucketOffset = {58, 36};
	private static final int[] InputFluidTank = {11, 52,12,47,5000};
	private static final int[] OutputItemOffset = {104,16};
		
	private static final int[] MajorProcessingArrowOffset = {32, 17};
	private static final int[] MinorProcessingArrowOffset = {32, 37};
		

		
	private static final String modID =  References.ID;
	private static final String recipeGroup = "chemical_extractor";
	private static final String guiTexture = "textures/gui/jei/sol_and_liq_to_sol_recipe_gui.png";
	private static final ItemStack inputMachine = new ItemStack(DeferredRegisters.blockChemicalExtractor);
	private static final ArrayList<int[]> inputCoordinates = 
				new ArrayList<>(Arrays.asList(
											  GUIBackground,
											  MajorProcessingArrowLocation,
											  MinorProcessingArrowLocation,
											  InputItemOffset,
											  InputFluidBucketOffset,
											  InputFluidTank,
											  OutputItemOffset,
											  MajorProcessingArrowOffset,
											  MinorProcessingArrowOffset
				));
	private static final int smeltTime = 50;
	
	private static StartDirection majorArrowStartDirection = IDrawableAnimated.StartDirection.LEFT;
	private static StartDirection minorArrowStartDirection = IDrawableAnimated.StartDirection.RIGHT;
	
	private static int textYHeight = 48;
	
	public static final ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	public ChemicalExtractorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper,modID,recipeGroup,guiTexture,inputMachine,inputCoordinates,smeltTime, majorArrowStartDirection,
				minorArrowStartDirection,textYHeight);
	}
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}
	


}
