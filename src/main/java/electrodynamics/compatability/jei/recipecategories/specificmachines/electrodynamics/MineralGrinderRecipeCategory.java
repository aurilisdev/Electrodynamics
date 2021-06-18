package electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.recipecategories.O2ORecipeCategory;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MineralGrinderRecipeCategory extends O2ORecipeCategory {

    // JEI Window Parameters
    private static int[] GUIBackground = { 0, 0, 132, 58 };
    private static int[] ProcessingArrowLocation = { 132, 0, 24, 17 };

    private static int[] InputItemOffset = { 22, 21 };
    private static int[] OutputItemOffset = { 82, 22 };
    private static int[] ProcessingArrowOffset = { 46, 23 };

    private static String modID = References.ID;
    private static String recipeGroup = "mineral_grinder";
    private static String guiTexture = "textures/gui/jei/o2o_recipe_gui.png";
    private static ItemStack inputMachine = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralgrinder));
    private static ArrayList<int[]> inputCoordinates = new ArrayList<>(
	    Arrays.asList(GUIBackground, ProcessingArrowLocation, InputItemOffset, OutputItemOffset, ProcessingArrowOffset));
    private static int smeltTime = 50;
    private static int textYHeight = 48;
    private static IDrawableAnimated.StartDirection arrowStartDirection = IDrawableAnimated.StartDirection.LEFT;

    public static ResourceLocation UID = new ResourceLocation(modID, recipeGroup);

    public MineralGrinderRecipeCategory(IGuiHelper guiHelper) {
	super(guiHelper, modID, recipeGroup, guiTexture, inputMachine, inputCoordinates, smeltTime, textYHeight, arrowStartDirection);
    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

}
