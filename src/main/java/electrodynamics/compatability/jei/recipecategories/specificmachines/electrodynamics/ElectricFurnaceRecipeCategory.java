package electrodynamics.compatability.jei.recipecategories.specificmachines.electrodynamics;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.recipecategories.ModFurnaceRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ElectricFurnaceRecipeCategory extends ModFurnaceRecipeCategory {

    // JEI Window Parameters
    private static int[] GUI_BACKGROUND = { 0, 0, 132, 58 };
    private static int[] PROCESSING_ARROW_LOCATION = { 132, 0, 24, 17 };

    private static int[] INPUT_ITEM_OFFSET = { 22, 21 };
    private static int[] OUTPUT_ITEM_OFFSET = { 82, 22 };
    private static int[] PROCESSING_ARROW_OFFSET = { 46, 23 };

    private static int SMELT_TIME = 50;
    private static int TEXT_Y_HEIGHT = 48;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "electric_furnace";
    private static String GUI_TEXTURE = "textures/gui/jei/electric_furnace.png";

    public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace));

    private static StartDirection START_DIRECTION = IDrawableAnimated.StartDirection.LEFT;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    private static ArrayList<int[]> INPUT_COORDINATES = new ArrayList<>(
	    Arrays.asList(GUI_BACKGROUND, PROCESSING_ARROW_LOCATION, INPUT_ITEM_OFFSET, OUTPUT_ITEM_OFFSET, PROCESSING_ARROW_OFFSET));

    public ElectricFurnaceRecipeCategory(IGuiHelper guiHelper) {
	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, INPUT_COORDINATES, SMELT_TIME, START_DIRECTION, TEXT_Y_HEIGHT);
    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

}
