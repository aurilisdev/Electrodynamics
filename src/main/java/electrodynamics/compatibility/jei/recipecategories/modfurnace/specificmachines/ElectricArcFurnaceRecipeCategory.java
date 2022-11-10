package electrodynamics.compatibility.jei.recipecategories.modfurnace.specificmachines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.Constants;
import electrodynamics.compatibility.jei.recipecategories.modfurnace.ModFurnaceRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowRightAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.stat.FlameStaticWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BigItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.PowerLabelWrapper;
import electrodynamics.registers.DeferredRegisters;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;

public class ElectricArcFurnaceRecipeCategory extends ModFurnaceRecipeCategory<BlastingRecipe> {

	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 58);

	private static DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(22, 20);
	private static BigItemSlotWrapper OUTPUT_SLOT = new BigItemSlotWrapper(83, 16);

	private static ArrowRightAnimatedWrapper ANIM_ARROW = new ArrowRightAnimatedWrapper(50, 23);
	private static FlameStaticWrapper FLAME = new FlameStaticWrapper(5, 23);

	private static PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 48, Constants.ELECTRICARCFURNACE_USAGE_PER_TICK, 120);

	private static int ANIM_TIME = 50;

	public static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.getSafeBlock(SubtypeMachine.electricarcfurnace));

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = SubtypeMachine.electricarcfurnace.tag() + "0";

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public static final RecipeType<BlastingRecipe> RECIPE_TYPE = RecipeType.create(ModIds.MINECRAFT_ID, "mod_blasting", BlastingRecipe.class);

	public ElectricArcFurnaceRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, BlastingRecipe.class, ANIM_TIME, Constants.ELECTRICARCFURNACE_USAGE_PER_TICK, 120);
		setInputSlots(guiHelper, INPUT_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setStaticArrows(guiHelper, FLAME);
		setAnimatedArrows(guiHelper, ANIM_ARROW);
		setLabels(POWER_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<BlastingRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

	@Override
	public List<List<ItemStack>> getItemInputs(AbstractCookingRecipe recipe) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		for (Ingredient ing : recipe.getIngredients()) {
			inputs.add(Arrays.asList(ing.getItems()));
		}
		return inputs;
	}

	@Override
	public List<ItemStack> getItemOutputs(AbstractCookingRecipe recipe) {
		List<ItemStack> outputs = new ArrayList<>();
		outputs.add(recipe.getResultItem());
		return outputs;
	}

}
