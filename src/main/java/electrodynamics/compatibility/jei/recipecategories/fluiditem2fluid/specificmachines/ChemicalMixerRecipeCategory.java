package electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.FluidItem2FluidRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.gui.types.fluidgauge.FluidGaugeObject;
import electrodynamics.compatibility.jei.utils.label.types.PowerLabelWrapperElectroRecipe;
import electrodynamics.compatibility.jei.utils.label.types.TimeLabelWrapperElectroRecipe;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;

public class ChemicalMixerRecipeCategory extends FluidItem2FluidRecipeCategory<ChemicalMixerRecipe> {

	private static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 64);

	private static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 57, 16, RecipeIngredientRole.INPUT);
	private static final ItemSlotObject INPUT_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 57, 36, RecipeIngredientRole.INPUT);
	private static final ItemSlotObject OUTPUT_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 88, 36, RecipeIngredientRole.OUTPUT);

	private static final ArrowAnimatedObject ANIM_RIGHT_ARROW_1 = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 30, 17, StartDirection.LEFT);
	private static final ArrowAnimatedObject ANIM_RIGHT_ARROW_2 = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 80, 17, StartDirection.LEFT);
	private static final ArrowAnimatedObject ANIM_LEFT_ARROW = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_LEFT, 30, 37, StartDirection.RIGHT);

	private static final FluidGaugeObject IN_GAUGE = new FluidGaugeObject(10, 5, 5000);
	private static final FluidGaugeObject OUT_GAUGE = new FluidGaugeObject(108, 5, 5000);

	private static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 55, 240);
	private static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 55);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.chemicalmixer.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer));

	public static final RecipeType<ChemicalMixerRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ChemicalMixerRecipe.RECIPE_GROUP, ChemicalMixerRecipe.class);

	public ChemicalMixerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, INPUT_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_BUCKET_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setFluidOutputs(guiHelper, OUT_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

}
