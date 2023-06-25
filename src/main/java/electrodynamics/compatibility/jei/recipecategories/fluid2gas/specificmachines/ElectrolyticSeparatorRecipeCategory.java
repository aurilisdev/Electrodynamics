package electrodynamics.compatibility.jei.recipecategories.fluid2gas.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluid2gas.specificmachines.ElectrolyticSeparatorRecipe;
import electrodynamics.compatibility.jei.recipecategories.fluid2gas.Fluid2GasRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.gui.types.fluidgauge.FluidGaugeObject;
import electrodynamics.compatibility.jei.utils.gui.types.gasgauge.DefaultGasGaugeWrapper;
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

public class ElectrolyticSeparatorRecipeCategory extends Fluid2GasRecipeCategory<ElectrolyticSeparatorRecipe> {

	private static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 64);

	private static final ItemSlotObject IN_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 26, 36, RecipeIngredientRole.INPUT);
	private static final ItemSlotObject OUTPUT_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 66, 36, RecipeIngredientRole.OUTPUT);
	private static final ItemSlotObject BIPRODUCT_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 106, 36, RecipeIngredientRole.OUTPUT);

	private static final ArrowAnimatedObject ANIM_RIGHT_ARROW_1 = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 24, 17, StartDirection.LEFT);
	private static final ArrowAnimatedObject ANIM_RIGHT_ARROW_2 = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 64, 17, StartDirection.LEFT);

	private static final FluidGaugeObject IN_GAUGE = new FluidGaugeObject(8, 5, 5000);
	private static final DefaultGasGaugeWrapper OUT_GAUGE = new DefaultGasGaugeWrapper(48, 5, 5000);
	private static final DefaultGasGaugeWrapper BIPRODUCT_GAUGE = new DefaultGasGaugeWrapper(88, 5, 5000);

	private static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 55, 240);
	private static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 55);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.electrolyticseparator.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator));

	public static final RecipeType<ElectrolyticSeparatorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ElectrolyticSeparatorRecipe.RECIPE_GROUP, ElectrolyticSeparatorRecipe.class);

	public ElectrolyticSeparatorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		setInputSlots(guiHelper, IN_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_BUCKET_SLOT, BIPRODUCT_BUCKET_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setGasOutputs(guiHelper, OUT_GAUGE, BIPRODUCT_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

}
