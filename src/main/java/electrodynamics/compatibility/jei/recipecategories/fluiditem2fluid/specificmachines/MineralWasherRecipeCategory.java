package electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.MineralWasherRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.FluidItem2FluidRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowLeftAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MineralWasherRecipeCategory extends FluidItem2FluidRecipeCategory<MineralWasherRecipe> {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(57, 16);
	private static BucketSlotWrapper INPUT_BUCKET_SLOT = new BucketSlotWrapper(57, 36);
	private static BucketSlotWrapper OUTPUT_BUCKET_SLOT = new BucketSlotWrapper(88, 36);

	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(30, 17);
	private static ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(80, 17);
	private static ArrowLeftAnimatedWrapper ANIM_LEFT_ARROW = new ArrowLeftAnimatedWrapper(30, 37);

	private static DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(10, 5, 5000);
	private static DefaultFluidGaugeWrapper OUT_GAUGE = new DefaultFluidGaugeWrapper(108, 5, 5000);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 55, Constants.MINERALCRUSHER_USAGE_PER_TICK, 480);

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = SubtypeMachine.mineralwasher.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralwasher));

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public static final RecipeType<MineralWasherRecipe> RECIPE_TYPE = RecipeType.create(References.ID, MineralWasherRecipe.RECIPE_GROUP, MineralWasherRecipe.class);

	public MineralWasherRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, MineralWasherRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, INPUT_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_BUCKET_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setFluidOutputs(guiHelper, OUT_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<MineralWasherRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
