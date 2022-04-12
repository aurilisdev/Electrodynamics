package electrodynamics.compatibility.jei.recipecategories.fluid2fluid.specificmachines;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolyticSeparatorRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.compatibility.jei.recipecategories.fluid2fluid.Fluid2FluidRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ElectrolyticSeparatorRecipeCategory extends Fluid2FluidRecipeCategory<ElectrolyticSeparatorRecipe> {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static BucketSlotWrapper IN_BUCKET_SLOT = new BucketSlotWrapper(26, 36);
	private static BucketSlotWrapper OUTPUT_BUCKET_SLOT = new BucketSlotWrapper(66, 36);
	private static BucketSlotWrapper BIPRODUCT_BUCKET_SLOT = new BucketSlotWrapper(106, 36);

	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(24, 17);
	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(64, 17);

	private static DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(8, 5, 5000);
	private static DefaultFluidGaugeWrapper OUT_GAUGE = new DefaultFluidGaugeWrapper(48, 5, 5000);
	private static DefaultFluidGaugeWrapper BIPRODUCT_GAUGE = new DefaultFluidGaugeWrapper(88, 5, 5000);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 55, Constants.ELECTROLYTICSEPARATOR_USAGE_PER_TICK, 240);

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = SubtypeMachine.electrolyticseparator.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.getSafeBlock(SubtypeMachine.electrolyticseparator));

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);
	
	public static final RecipeType<ElectrolyticSeparatorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ElectrolyticSeparatorRecipe.RECIPE_GROUP, ElectrolyticSeparatorRecipe.class);

	public ElectrolyticSeparatorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ElectrolyticSeparatorRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, IN_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_BUCKET_SLOT, BIPRODUCT_BUCKET_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setFluidOutputs(guiHelper, OUT_GAUGE, BIPRODUCT_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL);
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}
	
	@Override
	public RecipeType<ElectrolyticSeparatorRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
