package electrodynamics.compatability.jei.recipecategories.fluid2item;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatability.jei.utils.gui.arrows.animated.ArrowLeftAnimatedWrapper;
import electrodynamics.compatability.jei.utils.gui.arrows.animated.ArrowRightBigAnimatedWrapper;
import electrodynamics.compatability.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatability.jei.utils.gui.fluid.DefaultFluidGaugeWrapper;
import electrodynamics.compatability.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatability.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatability.jei.utils.label.PowerLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ChemicalCrystallizerRecipeCategory extends Fluid2ItemRecipeCategory {

    // JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132,64);
	
	private static BucketSlotWrapper IN_BUCKET_SLOT = new BucketSlotWrapper(58, 36);
	
	private static DefaultItemSlotWrapper OUT_SLOT = new DefaultItemSlotWrapper(100, 16);
	
	private static ArrowRightBigAnimatedWrapper ANIM_RIGHT_ARROW = new ArrowRightBigAnimatedWrapper(32, 17);
	private static ArrowLeftAnimatedWrapper ANIM_LEFT_ARROW = new ArrowLeftAnimatedWrapper(32, 37);
	
	private static DefaultFluidGaugeWrapper IN_GAUGE = new DefaultFluidGaugeWrapper(14, 5, 5000);
	
	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(55, BACK_WRAP);

    private static int ANIM_TIME = 50;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "chemical_crystallizer";

    public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer));

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);
    
    public ChemicalCrystallizerRecipeCategory(IGuiHelper guiHelper) {
    	super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ANIM_TIME);
    	setInputSlots(guiHelper, IN_BUCKET_SLOT);
    	setOutputSlots(guiHelper, OUT_SLOT);
    	setFluidInputs(guiHelper, IN_GAUGE);
    	setAnimatedArrows(guiHelper, ANIM_LEFT_ARROW, ANIM_RIGHT_ARROW);
    	setLabels(POWER_LABEL);
    }

    @Override
    public ResourceLocation getUid() {
    	return UID;
    }

}
