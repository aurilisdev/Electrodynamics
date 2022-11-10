package electrodynamics.compatibility.jei.recipecategories.item2item.specificmachines;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.recipe.categories.item2item.specificmachines.MineralCrusherRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.compatibility.jei.ElectrodynamicsJEIPlugin;
import electrodynamics.compatibility.jei.recipecategories.item2item.Item2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BigItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.BiproductPercentWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import electrodynamics.registers.DeferredRegisters;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MineralCrusherRecipeCategory extends Item2ItemRecipeCategory<MineralCrusherRecipe> {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 58);

	private static DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(17, 20);
	private static BigItemSlotWrapper OUTPUT_SLOT = new BigItemSlotWrapper(69, 16);
	private static DefaultItemSlotWrapper BIPRODUCT_SLOT = new DefaultItemSlotWrapper(100, 20);

	private static ArrowRightAnimatedWrapper ANIM_ARROW = new ArrowRightAnimatedWrapper(41, 23);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 48, Constants.MINERALCRUSHER_USAGE_PER_TICK, 240);
	private static BiproductPercentWrapper ITEM_LABEL = new BiproductPercentWrapper(100, 40, false);

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = SubtypeMachine.mineralcrusher.tag() + "0";

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.getSafeBlock(SubtypeMachine.mineralcrusher));

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public static final RecipeType<MineralCrusherRecipe> RECIPE_TYPE = RecipeType.create(References.ID, MineralCrusherRecipe.RECIPE_GROUP, MineralCrusherRecipe.class);

	public MineralCrusherRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, MineralCrusherRecipe.class, ANIM_TIME);
		ElectrodynamicsJEIPlugin.addO2OClickArea(RECIPE_TYPE);
		setInputSlots(guiHelper, INPUT_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT, BIPRODUCT_SLOT);
		setAnimatedArrows(guiHelper, ANIM_ARROW);
		setLabels(POWER_LABEL, ITEM_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<MineralCrusherRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

}
