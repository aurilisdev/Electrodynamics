package electrodynamics.compatibility.jei.recipecategories.modfurnace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import voltaic.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaic.compatibility.jei.utils.gui.ScreenObject;
import voltaic.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import voltaic.compatibility.jei.utils.gui.types.BackgroundObject;
import voltaic.compatibility.jei.utils.gui.types.ItemSlotObject;
import voltaic.compatibility.jei.utils.label.types.PowerLabelWrapperConstant;
import voltaic.compatibility.jei.utils.label.types.TimeLabelWrapperConstant;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;

public class ElectricFurnaceRecipeCategory extends AbstractRecipeCategory<SmeltingRecipe> {

    public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 58);

    public static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 22, 20,
	    RecipeIngredientRole.INPUT);
    public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.BIG, 83, 16,
	    RecipeIngredientRole.OUTPUT);

    public static final ArrowAnimatedObject ANIM_ARROW = new ArrowAnimatedObject(
	    ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, 50, 23, StartDirection.LEFT);
    public static final ScreenObject FLAME = new ScreenObject(ScreenComponentProgress.ProgressTextures.FLAME_ON, 5, 23);

    public static final PowerLabelWrapperConstant POWER_LABEL = new PowerLabelWrapperConstant(2, 48,
	    ElectrodynamicsConfig.INSTANCE.ELECTRICFURNACE_USAGE_PER_TICK.get(), 120);
    public static final TimeLabelWrapperConstant TIME_LABEL = new TimeLabelWrapperConstant(130, 48,
	    ElectrodynamicsConfig.INSTANCE.ELECTRICFURNACE_REQUIRED_TICKS.get());

    public static final int ANIM_TIME = 50;

    public static final String RECIPE_GROUP = "smelting";

    public static ItemStack INPUT_MACHINE = new ItemStack(
	    ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnace));

    public static final RecipeType<SmeltingRecipe> RECIPE_TYPE = RecipeType.create(Electrodynamics.ID, RECIPE_GROUP,
	    SmeltingRecipe.class);

    public ElectricFurnaceRecipeCategory(IGuiHelper guiHelper) {
	super(guiHelper, ElectroTextUtils.jeiTranslated(RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE,
		ANIM_TIME);
	setInputSlots(guiHelper, INPUT_SLOT);
	setOutputSlots(guiHelper, OUTPUT_SLOT);
	setScreenObjects(guiHelper, FLAME);
	setAnimatedArrows(guiHelper, ANIM_ARROW);
	setLabels(POWER_LABEL, TIME_LABEL);
    }

    @Override
    public List<List<ItemStack>> getItemInputs(SmeltingRecipe recipe) {
	List<List<ItemStack>> inputs = new ArrayList<>();
	for (Ingredient ing : recipe.getIngredients()) {
	    inputs.add(Arrays.asList(ing.getItems()));
	}
	return inputs;
    }

    @Override
    public List<ItemStack> getItemOutputs(SmeltingRecipe recipe) {
	List<ItemStack> outputs = new ArrayList<>();
	outputs.add(recipe.getResultItem(null));
	return outputs;
    }

}
