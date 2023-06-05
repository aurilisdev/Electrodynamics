package electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.compatibility.jei.recipecategories.fluiditem2fluid.FluidItem2FluidRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowLeftAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import electrodynamics.compatibility.jei.utils.label.TimeLabelWrapper;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ChemicalMixerRecipeCategory extends FluidItem2FluidRecipeCategory<ChemicalMixerRecipe> {

	private static final BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static final DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(57, 16);
	private static final BucketSlotWrapper INPUT_BUCKET_SLOT = new BucketSlotWrapper(57, 36);
	private static final BucketSlotWrapper OUTPUT_BUCKET_SLOT = new BucketSlotWrapper(88, 36);

	private static final ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(30, 17);
	private static final ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(80, 17);
	private static final ArrowLeftAnimatedWrapper ANIM_LEFT_ARROW = new ArrowLeftAnimatedWrapper(30, 37);

	private static final DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(10, 5, 5000);
	private static final DefaultFluidGaugeWrapper OUT_GAUGE = new DefaultFluidGaugeWrapper(108, 5, 5000);

	private static final PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 55, 240);
	private static final TimeLabelWrapper TIME_LABEL = new TimeLabelWrapper(130, 55);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.chemicalmixer.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer));

	public static final ResourceLocation UID = new ResourceLocation(References.ID, RECIPE_GROUP);

	public static final RecipeType<ChemicalMixerRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ChemicalMixerRecipe.RECIPE_GROUP, ChemicalMixerRecipe.class);

	public ChemicalMixerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ChemicalMixerRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, INPUT_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUTPUT_BUCKET_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setFluidOutputs(guiHelper, OUT_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW_1, ANIM_RIGHT_ARROW_2);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<ChemicalMixerRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
