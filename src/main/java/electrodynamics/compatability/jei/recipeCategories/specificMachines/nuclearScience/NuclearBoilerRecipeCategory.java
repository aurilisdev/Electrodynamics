package electrodynamics.compatability.jei.recipeCategories.specificMachines.nuclearScience;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipeCategories.SolAndLiqToLiquidRecipeCategory;

import nuclearscience.DeferredRegisters;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class NuclearBoilerRecipeCategory extends SolAndLiqToLiquidRecipeCategory{

	//JEI Window Parameters
	private static int[] GUIBackground = {0,0,132,64};
	private static int[] MajorProcessingArrowLocation = {0, 64, 68, 15};
	private static int[] MinorProcessingArrowLocation = {0, 79, 20, 15};
	
	private static int[] InputItemOffset = {60, 16};
	private static int[] InputFluidBucketOffset = {60, 36};
	private static int[] InputFluidTank = {15, 52,12,47,5000};
	private static int[] OutputFluidTank = {109,52,12,47,5000};
		
	private static int[] MajorProcessingArrowOffset = {34, 17};
	private static int[] MinorProcessingArrowOffset = {34, 37};
	
	private static int[] FluidBarLocation = {0,0,0,0};
		

		
	private static final String modID =  References.ID;
	private static final String recipeGroup = "nuclear_boiler";
	private static final String guiTexture = "textures/gui/jei/sol_and_liq_to_liq_recipe_gui.png";
	private static final ItemStack inputMachine = new ItemStack(DeferredRegisters.blockChemicalBoiler);
	private static final ArrayList<int[]> inputCoordinates = 
				new ArrayList<>(Arrays.asList(
											  GUIBackground,
											  MajorProcessingArrowLocation,
											  MinorProcessingArrowLocation,
											  InputItemOffset,
											  InputFluidBucketOffset,
											  InputFluidTank,
											  OutputFluidTank,
											  MajorProcessingArrowOffset,
											  MinorProcessingArrowOffset,
											  FluidBarLocation
				));
	private static final int smeltTime = 50;
	public static final ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	private static StartDirection majorArrowStartDirection = IDrawableAnimated.StartDirection.LEFT;
	private static StartDirection minorArrowStartDirection = IDrawableAnimated.StartDirection.RIGHT;
	
	private static int textYHeight = 48;
	
	public NuclearBoilerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime, majorArrowStartDirection,
				minorArrowStartDirection,textYHeight);
	}
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}

}
