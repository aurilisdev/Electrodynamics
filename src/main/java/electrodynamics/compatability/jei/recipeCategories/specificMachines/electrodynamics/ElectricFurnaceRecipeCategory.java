package electrodynamics.compatability.jei.recipeCategories.specificMachines.electrodynamics;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipeCategories.ModFurnaceRecipeCategory;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ElectricFurnaceRecipeCategory extends ModFurnaceRecipeCategory{

	//JEI Window Parameters
	private static int[] GUIBackground = {0,0,132,58};
	private static int[] ProcessingArrowLocation = {132,0,24,17};
	
	private static int[] InputItemOffset = {22,21};
	private static int[] OutputItemOffset = {82,22};
	private static int[] ProcessingArrowOffset = {46,23};
	

	
	private static String modID =  References.ID;
	private static String recipeGroup = "electric_furnace";
	private static String guiTexture = "textures/gui/jei/electric_furnace.png";
	private static ItemStack inputMachine = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace));
	private static ArrayList<int[]> inputCoordinates = 
			new ArrayList<>(Arrays.asList(
										  GUIBackground,
										  ProcessingArrowLocation,
										  InputItemOffset,
										  OutputItemOffset,
										  ProcessingArrowOffset
			));
	private static int smeltTime = 50;
	public static ResourceLocation UID = new ResourceLocation(modID,recipeGroup);
	
	private static StartDirection startDirection = IDrawableAnimated.StartDirection.LEFT;
	private static int textYHeight = 48;
	
	public ElectricFurnaceRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime,startDirection,textYHeight);
	}
	
	@Override
	public ResourceLocation getUid() {
		return UID;
	}

}
