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
import electrodynamics.compatibility.jei.utils.label.TimeLabelWrapper;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class ElectricFurnaceRecipeCategory extends ModFurnaceRecipeCategory<SmeltingRecipe> {

	private static final BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 58);

	private static final DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(22, 20);
	private static final BigItemSlotWrapper OUTPUT_SLOT = new BigItemSlotWrapper(83, 16);

	private static final ArrowRightAnimatedWrapper ANIM_ARROW = new ArrowRightAnimatedWrapper(50, 23);
	private static final FlameStaticWrapper FLAME = new FlameStaticWrapper(5, 23);

	private static final PowerLabelWrapper POWER_LABEL = new PowerLabelWrapper(2, 48, Constants.ELECTRICFURNACE_USAGE_PER_TICK, 120);
	private static final TimeLabelWrapper TIME_LABEL = new TimeLabelWrapper(130, 48, Constants.ELECTRICFURNACE_REQUIRED_TICKS);

	private static final int ANIM_TIME = 50;

	private static final String RECIPE_GROUP = SubtypeMachine.electricfurnace.tag();

	public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace));

	public static final ResourceLocation UID = new ResourceLocation(References.ID, RECIPE_GROUP);

	public static final RecipeType<SmeltingRecipe> RECIPE_TYPE = RecipeType.create(ModIds.MINECRAFT_ID, "smelting", SmeltingRecipe.class);

	public ElectricFurnaceRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, SmeltingRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setStaticArrows(guiHelper, FLAME);
		setAnimatedArrows(guiHelper, ANIM_ARROW);
		setLabels(POWER_LABEL, TIME_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<SmeltingRecipe> getRecipeType() {
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
