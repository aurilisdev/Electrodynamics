package electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.item2item.specificmachines.LatheRecipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import voltaic.compatibility.jei.VoltaicJEIPlugin;
import voltaic.compatibility.jei.recipecategories.item2item.Item2ItemRecipeCategory;
import voltaic.compatibility.jei.utils.RecipeType;
import voltaic.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import voltaic.compatibility.jei.utils.gui.types.BackgroundObject;
import voltaic.compatibility.jei.utils.gui.types.ItemSlotObject;
import voltaic.compatibility.jei.utils.label.types.BiproductPercentWrapperElectroRecipe;
import voltaic.compatibility.jei.utils.label.types.PowerLabelWrapperElectroRecipe;
import voltaic.compatibility.jei.utils.label.types.TimeLabelWrapperElectroRecipe;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;

public class LatheRecipeCategory extends Item2ItemRecipeCategory<LatheRecipe> {

    public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 58);

    public static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 17, 20, true);
    public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.BIG, 69, 16, false);
    public static final ItemSlotObject BIPRODUCT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 100, 20, false);

    public static final ArrowAnimatedObject ANIM_ARROW = new ArrowAnimatedObject(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, 41, 23, StartDirection.LEFT);

    public static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 48, 240);
    public static final BiproductPercentWrapperElectroRecipe ITEM_LABEL = new BiproductPercentWrapperElectroRecipe(100, 40, BiproductPercentWrapperElectroRecipe.BiproductType.ITEM, 0);
    public static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 48);

    public static final int ANIM_TIME = 50;

    public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lathe));

    public static final RecipeType<LatheRecipe> RECIPE_TYPE = RecipeType.create(Electrodynamics.ID, LatheRecipe.RECIPE_GROUP, LatheRecipe.class);

    public LatheRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ElectroTextUtils.jeiTranslated(LatheRecipe.RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
        VoltaicJEIPlugin.addO2OCategory(RECIPE_TYPE);
        setInputSlots(guiHelper, INPUT_SLOT);
        setOutputSlots(guiHelper, OUTPUT_SLOT, BIPRODUCT_SLOT);
        setAnimatedArrows(guiHelper, ANIM_ARROW);
        setLabels(POWER_LABEL, ITEM_LABEL, TIME_LABEL);
    }

}
