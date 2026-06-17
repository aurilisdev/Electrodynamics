package electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.item2item.specificmachines.ReinforcedAlloyerRecipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import voltaic.compatibility.jei.VoltaicJEIPlugin;
import voltaic.compatibility.jei.recipecategories.item2item.Item2ItemRecipeCategory;
import voltaic.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import voltaic.compatibility.jei.utils.gui.types.BackgroundObject;
import voltaic.compatibility.jei.utils.gui.types.ItemSlotObject;
import voltaic.compatibility.jei.utils.label.types.BiproductPercentWrapperElectroRecipe;
import voltaic.compatibility.jei.utils.label.types.PowerLabelWrapperElectroRecipe;
import voltaic.compatibility.jei.utils.label.types.TimeLabelWrapperElectroRecipe;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;

public class ReinforcedAlloyerRecipeCategory extends Item2ItemRecipeCategory<ReinforcedAlloyerRecipe> {

    public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 58);

    public static final ItemSlotObject INPUT_SLOT_1 = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 17, 11,
	    RecipeIngredientRole.INPUT);
    public static final ItemSlotObject INPUT_SLOT_2 = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 17, 30,
	    RecipeIngredientRole.INPUT);
    public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.BIG, 69, 16,
	    RecipeIngredientRole.OUTPUT);
    public static final ItemSlotObject BIPRODUCT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 100, 20,
	    RecipeIngredientRole.OUTPUT);

    public static final ArrowAnimatedObject ANIM_ARROW = new ArrowAnimatedObject(
	    ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, 41, 23, StartDirection.LEFT);

    public static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 49, 960);
    public static final BiproductPercentWrapperElectroRecipe ITEM_LABEL = new BiproductPercentWrapperElectroRecipe(100,
	    40, BiproductPercentWrapperElectroRecipe.BiproductType.ITEM, 0);
    public static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 49);

    public static final int ANIM_TIME = 50;

    public static ItemStack INPUT_MACHINE = new ItemStack(
	    ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.reinforcedalloyer));

    public static final RecipeType<ReinforcedAlloyerRecipe> RECIPE_TYPE = RecipeType.create(Electrodynamics.ID,
	    ReinforcedAlloyerRecipe.RECIPE_GROUP, ReinforcedAlloyerRecipe.class);

    public ReinforcedAlloyerRecipeCategory(IGuiHelper guiHelper) {
	super(guiHelper, ElectroTextUtils.jeiTranslated(ReinforcedAlloyerRecipe.RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP,
		RECIPE_TYPE, ANIM_TIME);
	VoltaicJEIPlugin.addDO2OCategory(RECIPE_TYPE);
	setInputSlots(guiHelper, INPUT_SLOT_1, INPUT_SLOT_2);
	setOutputSlots(guiHelper, OUTPUT_SLOT, BIPRODUCT_SLOT);
	setAnimatedArrows(guiHelper, ANIM_ARROW);
	setLabels(POWER_LABEL, ITEM_LABEL, TIME_LABEL);
    }

}
