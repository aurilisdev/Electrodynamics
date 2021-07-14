package electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.recipecategories.Fluid2ItemRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ChemicalCrystallizerRecipeCategory extends Fluid2ItemRecipeCategory {

    // JEI Window Parameters
    private static int[] GUI_BACKGROUND = { 0, 0, 132, 64 };
    private static int[] MAJOR_PROCESSING_ARROW_LOCATION = { 0, 64, 64, 15 };
    private static int[] MINOR_PROCESSING_ARROW_LOCATION = { 0, 79, 20, 15 };

    private static int[] INPUT_FLUID_BUCKET_OFFSET = { 56, 36 };
    private static int[] INPUT_FLUID_TANK = { 15, 52, 12, 47, 5000 };
    private static int[] OUTPUT_ITEM_OFFSET = { 100, 16 };

    private static int[] MAJOR_PROCESSING_ARROW_OFFSET = { 32, 17 };
    private static int[] MINOR_PROCESSING_ARROW_OFFSET = { 32, 37 };

    private static int[] FLUID_BAR_LOCATION = { 0, 0, 0, 0 };

    private static int TEXT_Y_HEIGHT = 48;
    private static int SMELT_TIME = 50;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "chemical_crystallizer";
    private static String GUI_TEXTURE = "textures/gui/jei/liq_to_sol_recipe_gui.png";

    public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer));

    private static StartDirection MAJOR_ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.LEFT;
    private static StartDirection MINOR_ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.RIGHT;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    private static ArrayList<int[]> INPUT_COORDINATES = new ArrayList<>(
	    Arrays.asList(GUI_BACKGROUND, MAJOR_PROCESSING_ARROW_LOCATION, MINOR_PROCESSING_ARROW_LOCATION, INPUT_FLUID_BUCKET_OFFSET,
		    INPUT_FLUID_TANK, OUTPUT_ITEM_OFFSET, MAJOR_PROCESSING_ARROW_OFFSET, MINOR_PROCESSING_ARROW_OFFSET, FLUID_BAR_LOCATION));

    public ChemicalCrystallizerRecipeCategory(IGuiHelper guiHelper) {

	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, INPUT_COORDINATES, TEXT_Y_HEIGHT, MAJOR_ARROW_START_DIRECTION,
		MINOR_ARROW_START_DIRECTION, SMELT_TIME);
    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

}
