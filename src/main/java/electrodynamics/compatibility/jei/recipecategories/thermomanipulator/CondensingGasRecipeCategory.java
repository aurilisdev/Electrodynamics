package electrodynamics.compatibility.jei.recipecategories.thermomanipulator;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.fluiditem2fluid.specificmachines.ChemicalMixerRecipe;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.PsuedoGas2FluidRecipe;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.fluidgauge.FluidGaugeObject;
import electrodynamics.compatibility.jei.utils.label.types.PowerLabelWrapperElectroRecipe;
import electrodynamics.compatibility.jei.utils.label.types.TimeLabelWrapperElectroRecipe;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CondensingGasRecipeCategory extends AbstractRecipeCategory<PsuedoGas2FluidRecipe> {

	private static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 64);

	//private static final DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(57, 16);
	//private static final BucketSlotWrapper INPUT_BUCKET_SLOT = new BucketSlotWrapper(57, 36);
	//private static final BucketSlotWrapper OUTPUT_BUCKET_SLOT = new BucketSlotWrapper(88, 36);

	//private static final ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_1 = new ArrowRightAnimatedWrapper(30, 17);
	//private static final ArrowRightAnimatedWrapper ANIM_RIGHT_ARROW_2 = new ArrowRightAnimatedWrapper(80, 17);
	//private static final ArrowLeftAnimatedWrapper ANIM_LEFT_ARROW = new ArrowLeftAnimatedWrapper(30, 37);

	private static final FluidGaugeObject IN_GAUGE = new FluidGaugeObject(10, 5, 5000);
	private static final FluidGaugeObject OUT_GAUGE = new FluidGaugeObject(108, 5, 5000);

	private static final PowerLabelWrapperElectroRecipe POWER_LABEL = new PowerLabelWrapperElectroRecipe(2, 55, 240);
	private static final TimeLabelWrapperElectroRecipe TIME_LABEL = new TimeLabelWrapperElectroRecipe(130, 55);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.chemicalmixer.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer));

	public static final ResourceLocation UID = new ResourceLocation(References.ID, RECIPE_GROUP);

	public static final RecipeType<PsuedoGas2FluidRecipe> RECIPE_TYPE = RecipeType.create(References.ID, ChemicalMixerRecipe.RECIPE_GROUP, PsuedoGas2FluidRecipe.class);
	
	public CondensingGasRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		// TODO Auto-generated constructor stub
	}
	
	

}
