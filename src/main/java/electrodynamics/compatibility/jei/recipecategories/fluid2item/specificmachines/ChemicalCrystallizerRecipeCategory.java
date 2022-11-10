package electrodynamics.compatibility.jei.recipecategories.fluid2item.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluid2item.specificmachines.ChemicalCrystalizerRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.compatibility.jei.recipecategories.fluid2item.Fluid2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowLeftAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightBigAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import electrodynamics.registers.DeferredRegisters;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ChemicalCrystallizerRecipeCategory extends Fluid2ItemRecipeCategory<ChemicalCrystalizerRecipe> {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 64);

	private static BucketSlotWrapper IN_BUCKET_SLOT = new BucketSlotWrapper(58, 36);

	private static DefaultItemSlotWrapper OUT_SLOT = new DefaultItemSlotWrapper(100, 16);

	private static ArrowRightBigAnimatedWrapper ANIM_RIGHT_ARROW = new ArrowRightBigAnimatedWrapper(32, 17);
	private static ArrowLeftAnimatedWrapper ANIM_LEFT_ARROW = new ArrowLeftAnimatedWrapper(32, 37);

	private static DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(14, 5, 5000);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 55, Constants.CHEMICALCRYSTALLIZER_USAGE_PER_TICK, 240);

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = SubtypeMachine.chemicalcrystallizer.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.getSafeBlock(SubtypeMachine.chemicalcrystallizer));

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public static final RecipeType<ChemicalCrystalizerRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ChemicalCrystalizerRecipe.RECIPE_GROUP, ChemicalCrystalizerRecipe.class);

	public ChemicalCrystallizerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ChemicalCrystalizerRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, IN_BUCKET_SLOT);
		setOutputSlots(guiHelper, OUT_SLOT);
		setFluidInputs(guiHelper, IN_GAUGE);
		setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW);
		setLabels(POWER_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<ChemicalCrystalizerRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
