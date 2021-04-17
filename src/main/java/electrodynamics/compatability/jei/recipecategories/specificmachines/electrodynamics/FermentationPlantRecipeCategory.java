package electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.References;
import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.recipecategories.SolAndLiqToLiquidRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FermentationPlantRecipeCategory extends SolAndLiqToLiquidRecipeCategory{
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
		

		
	private static String modID =  References.ID;
	private static String recipeGroup = "fermenting";
	private static String guiTexture = "textures/gui/jei/sol_and_liq_to_liq_recipe_gui.png";
	private static ItemStack inputMachine = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalmixer));
	private static ArrayList<int[]> inputCoordinates = 
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
	private static int smeltTime = 50;
	
	private static StartDirection majorArrowStartDirection = IDrawableAnimated.StartDirection.LEFT;
	private static StartDirection minorArrowStartDirection = IDrawableAnimated.StartDirection.RIGHT;
	
	private static int textYHeight = 48;
	
	public static ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	public FermentationPlantRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper,modID,recipeGroup,guiTexture,inputMachine,inputCoordinates,smeltTime, majorArrowStartDirection,
				minorArrowStartDirection,textYHeight);
	}
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}
}
