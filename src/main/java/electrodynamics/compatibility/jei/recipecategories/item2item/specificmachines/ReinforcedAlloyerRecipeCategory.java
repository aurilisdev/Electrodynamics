package electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.compatibility.jei.ElectrodynamicsJEIPlugin;
import electrodynamics.compatibility.jei.recipecategories.item2item.Item2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BigItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.BiproductPercentWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ReinforcedAlloyerRecipeCategory extends Item2ItemRecipeCategory {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 58);

    private static DefaultItemSlotWrapper INPUT_SLOT_1 = new DefaultItemSlotWrapper(17, 11);
    private static DefaultItemSlotWrapper INPUT_SLOT_2 = new DefaultItemSlotWrapper(17, 30);
    private static BigItemSlotWrapper OUTPUT_SLOT = new BigItemSlotWrapper(69, 16);
    private static DefaultItemSlotWrapper BIPRODUCT_SLOT = new DefaultItemSlotWrapper(100, 20);

    private static ArrowRightAnimatedWrapper ANIM_ARROW = new ArrowRightAnimatedWrapper(41, 23);

    private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 49);
    private static BiproductPercentWrapper ITEM_LABEL = new BiproductPercentWrapper(100, 40, false);


	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "reinforced_alloyer";

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.reinforcedalloyer));

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ReinforcedAlloyerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, ANIM_TIME);
		ElectrodynamicsJEIPlugin.addDO2OClickArea(UID);
		setInputSlots(guiHelper, INPUT_SLOT_1, INPUT_SLOT_2);
		setOutputSlots(guiHelper, OUTPUT_SLOT, BIPRODUCT_SLOT);
		setAnimatedArrows(guiHelper, ANIM_ARROW);
		setLabels(POWER_LABEL, ITEM_LABEL);
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

}
