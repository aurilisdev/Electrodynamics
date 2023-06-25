package electrodynamics.compatibility.jei.recipecategories.modfurnace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.Constants;
import electrodynamics.compatibility.jei.recipecategories.utils.AbstractRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.label.types.PowerLabelWrapperConstant;
import electrodynamics.compatibility.jei.utils.label.types.TimeLabelWrapperConstant;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;

public class ElectricArcFurnaceRecipeCategory extends AbstractRecipeCategory<BlastingRecipe> {

	private static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 58);

	private static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 22, 20, RecipeIngredientRole.INPUT);
	private static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(SlotType.BIG, 83, 16, RecipeIngredientRole.OUTPUT);

	private static final ArrowAnimatedObject ANIM_ARROW = new ArrowAnimatedObject(ProgressBars.PROGRESS_ARROW_RIGHT, 50, 23, StartDirection.LEFT);
	private static final ScreenObject FLAME = new ScreenObject(ProgressTextures.FLAME_ON, 5, 23);

	private static final PowerLabelWrapperConstant POWER_LABEL = new PowerLabelWrapperConstant(2, 48, Constants.ELECTRICARCFURNACE_USAGE_PER_TICK, 120);
	private static final TimeLabelWrapperConstant TIME_LABEL = new TimeLabelWrapperConstant(130, 48, Constants.ELECTRICARCFURNACE_REQUIRED_TICKS);

	private static final int ANIM_TIME = 50;

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnace));

	private static final String RECIPE_GROUP = SubtypeMachine.electricarcfurnace.tag();

	public static final RecipeType<BlastingRecipe> RECIPE_TYPE = RecipeType.create(ModIds.MINECRAFT_ID, "mod_blasting", BlastingRecipe.class);

	public ElectricArcFurnaceRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setScreenObjects(guiHelper, FLAME);
		setAnimatedArrows(guiHelper, ANIM_ARROW);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

	@Override
	public List<List<ItemStack>> getItemInputs(BlastingRecipe recipe) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		for (Ingredient ing : recipe.getIngredients()) {
			inputs.add(Arrays.asList(ing.getItems()));
		}
		return inputs;
	}

	@Override
	public List<ItemStack> getItemOutputs(BlastingRecipe recipe) {
		List<ItemStack> outputs = new ArrayList<>();
		outputs.add(recipe.getResultItem());
		return outputs;
	}

}
