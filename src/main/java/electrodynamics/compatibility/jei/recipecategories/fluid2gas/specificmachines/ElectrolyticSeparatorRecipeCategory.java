package electrodynamics.compatibility.jei.recipecategories.fluid2gas.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluid2gas.specificmachines.ElectrolyticSeparatorRecipe;
import electrodynamics.compatibility.jei.recipecategories.fluid2gas.Fluid2GasRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.gas.DefaultGasGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import electrodynamics.compatibility.jei.utils.label.TimeLabelWrapper;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ElectrolyticSeparatorRecipeCategory extends Fluid2GasRecipeCategory<ElectrolyticSeparatorRecipe> {

	private static final BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static final DefaultItemSlotWrapper IN_BUCKET_SLOT = new DefaultItemSlotWrapper(26, 36);
	private static final DefaultItemSlotWrapper OUTPUT_BUCKET_SLOT = new DefaultItemSlotWrapper(66, 36);
	private static final DefaultItemSlotWrapper BIPRODUCT_BUCKET_SLOT = new DefaultItemSlotWrapper(106, 36);

	private static final ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(24, 17);
	private static final ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(64, 17);

	private static final DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(8, 5, 5000);
	private static final DefaultGasGaugeWrapper OUT_GAUGE = new DefaultGasGaugeWrapper(48, 5, 5000);
	private static final DefaultGasGaugeWrapper BIPRODUCT_GAUGE = new DefaultGasGaugeWrapper(88, 5, 5000);

	private static final PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 55, 240);
	private static final TimeLabelWrapper TIME_LABEL = new TimeLabelWrapper(130, 55);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.electrolyticseparator.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator));

	public static final ResourceLocation UID = new ResourceLocation(References.ID, RECIPE_GROUP);

	public static final RecipeType<ElectrolyticSeparatorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ElectrolyticSeparatorRecipe.RECIPE_GROUP, ElectrolyticSeparatorRecipe.class);

	public ElectrolyticSeparatorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ElectrolyticSeparatorRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, IN_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_BUCKET_SLOT, BIPRODUCT_BUCKET_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setGasOutputs(guiHelper, OUT_GAUGE, BIPRODUCT_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<ElectrolyticSeparatorRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
