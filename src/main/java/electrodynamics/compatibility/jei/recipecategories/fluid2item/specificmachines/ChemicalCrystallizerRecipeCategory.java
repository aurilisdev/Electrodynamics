package electrodynamics.compatibility.jei.recipecategories.fluid2item.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import electrodynamics.compatibility.jei.recipecategories.fluid2item.Fluid2ItemRecipeCategory;
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

public class ChemicalCrystallizerRecipeCategory extends Fluid2ItemRecipeCategory<ChemicalCrystalizerRecipe> {

	private static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 64);

	private static final ItemSlotObject IN_BUCKET_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 58, 36, RecipeIngredientRole.INPUT);

	private static final ItemSlotObject OUT_SLOT = new ItemSlotObject(SlotType.NORMAL, IconType.FLUID_DARK, 100, 16, RecipeIngredientRole.OUTPUT);

	private static final ArrowAnimatedObject ANIM_RIGHT_ARROW = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT_BIG, 32, 17, StartDirection.LEFT);
	private static final ArrowAnimatedObject ANIM_LEFT_ARROW = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_LEFT, 32, 37, StartDirection.RIGHT);

	private static final FluidGaugeObject IN_GAUGE = new FluidGaugeObject(14, 5, 5000);

	private static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 55, 240);
	private static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 55);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.chemicalcrystallizer.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer));

	public static final RecipeType<ChemicalCrystalizerRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ChemicalCrystalizerRecipe.RECIPE_GROUP, ChemicalCrystalizerRecipe.class);

	public ChemicalCrystallizerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		setInputSlots(guiHelper, IN_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUT_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

}
