package electrodynamics.compatibility.jei.recipecategories.modfurnace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.GenericItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public abstract class ModFurnaceRecipeCategory<T extends AbstractCookingRecipe> implements IRecipeCategory<T> {

	private AbstractLabelWrapper[] labels;

	private LoadingCache<Integer, List<IDrawableAnimated>> animatedArrowDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> staticArrowDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> inputSlotDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> outputSlotDrawables;

	private GenericItemSlotWrapper[] inputSlotWrappers = new GenericItemSlotWrapper[0];
	private GenericItemSlotWrapper[] outputSlotWrappers = new GenericItemSlotWrapper[0];
	private ArrowAnimatedWrapper[] animatedArrowWrappers = new ArrowAnimatedWrapper[0];
	private ScreenObjectWrapper[] staticArrowWrappers = new ScreenObjectWrapper[0];

	private int animationTime;

	private String recipeGroup;

	private IDrawable background;
	private IDrawable icon;

	private Class<T> recipeCategoryClass;

	public ModFurnaceRecipeCategory(IGuiHelper guiHelper, String recipeGroup, ItemStack inputMachine, BackgroundWrapper wrapper, Class<T> recipeClass, int animTime) {

		animationTime = animTime;

		this.recipeGroup = recipeGroup;

		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, inputMachine);
		background = guiHelper.createDrawable(wrapper.getTexture(), wrapper.getTextX(), wrapper.getTextY(), wrapper.getWidth(), wrapper.getHeight());

		recipeCategoryClass = recipeClass;
	}

	public Class<T> getRecipeClass() {
		return recipeCategoryClass;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("container." + recipeGroup);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AbstractCookingRecipe recipe, IFocusGroup focuses) {
		GenericItemSlotWrapper wrapper;
		List<List<ItemStack>> inputs = getItemInputs(recipe);
		for (int i = 0; i < inputSlotWrappers.length; i++) {
			wrapper = inputSlotWrappers[i];
			builder.addSlot(RecipeIngredientRole.INPUT, wrapper.itemXStart(), wrapper.itemYStart()).addItemStacks(inputs.get(i));
		}
		List<ItemStack> outputs = getItemOutputs(recipe);
		for (int i = 0; i < outputSlotWrappers.length; i++) {
			wrapper = outputSlotWrappers[i];
			builder.addSlot(RecipeIngredientRole.OUTPUT, wrapper.itemXStart(), wrapper.itemYStart()).addItemStack(outputs.get(i));
		}
	}

	@Override
	public void draw(AbstractCookingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		List<IDrawableStatic> inputSlots = inputSlotDrawables.getUnchecked(animationTime);
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < inputSlots.size(); i++) {
			image = inputSlots.get(i);
			wrapper = inputSlotWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}

		List<IDrawableStatic> outputSlots = outputSlotDrawables.getUnchecked(animationTime);
		for (int i = 0; i < outputSlots.size(); i++) {
			image = outputSlots.get(i);
			wrapper = outputSlotWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}

		List<IDrawableStatic> staticArrows = staticArrowDrawables.getUnchecked(animationTime);
		for (int i = 0; i < staticArrows.size(); i++) {
			image = staticArrows.get(i);
			wrapper = this.staticArrowWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
		List<IDrawableAnimated> arrows = animatedArrowDrawables.getUnchecked(animationTime);
		IDrawableAnimated arrow;
		for (int i = 0; i < arrows.size(); i++) {
			arrow = arrows.get(i);
			wrapper = animatedArrowWrappers[i];
			arrow.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}

		addDescriptions(matrixStack, recipe);
	}

	// in case we decide to do something wacky with a furnace
	public abstract List<List<ItemStack>> getItemInputs(AbstractCookingRecipe recipe);

	public abstract List<ItemStack> getItemOutputs(AbstractCookingRecipe recipe);

	public void addDescriptions(PoseStack stack, AbstractCookingRecipe recipe) {
		Font fontRenderer = Minecraft.getInstance().font;
		for (AbstractLabelWrapper wrap : labels) {
			Component text = wrap.getComponent(this, recipe);
			if (wrap.xIsEnd()) {
				fontRenderer.draw(stack, text, wrap.getXPos() - fontRenderer.width(text.getVisualOrderText()), wrap.getYPos(), wrap.getColor());
			} else {
				fontRenderer.draw(stack, text, wrap.getXPos(), wrap.getYPos(), wrap.getColor());
			}
		}
	}

	public void setInputSlots(IGuiHelper guiHelper, GenericItemSlotWrapper... inputSlots) {
		inputSlotWrappers = inputSlots;
		inputSlotDrawables = CacheBuilder.newBuilder().maximumSize(inputSlots.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			@Override
			public List<IDrawableStatic> load(Integer time) {
				List<IDrawableStatic> slots = new ArrayList<>();
				for (ScreenObjectWrapper slot : inputSlots) {
					slots.add(guiHelper.drawableBuilder(slot.getTexture(), slot.getTextX(), slot.getTextY(), slot.getWidth(), slot.getHeight()).build());
				}
				return slots;
			}
		});
	}

	public void setOutputSlots(IGuiHelper guiHelper, GenericItemSlotWrapper... outputSlots) {
		outputSlotWrappers = outputSlots;
		outputSlotDrawables = CacheBuilder.newBuilder().maximumSize(outputSlots.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			@Override
			public List<IDrawableStatic> load(Integer time) {
				List<IDrawableStatic> slots = new ArrayList<>();
				for (ScreenObjectWrapper slot : outputSlots) {
					slots.add(guiHelper.drawableBuilder(slot.getTexture(), slot.getTextX(), slot.getTextY(), slot.getWidth(), slot.getHeight()).build());
				}
				return slots;
			}
		});
	}

	public void setStaticArrows(IGuiHelper guiHelper, ScreenObjectWrapper... arrows) {
		if (staticArrowWrappers.length == 0) {
			staticArrowWrappers = arrows;
		} else {
			staticArrowWrappers = ArrayUtils.addAll(staticArrowWrappers, arrows);
		}
		staticArrowDrawables = CacheBuilder.newBuilder().maximumSize(staticArrowWrappers.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			@Override
			public List<IDrawableStatic> load(Integer time) {
				List<IDrawableStatic> arrows = new ArrayList<>();
				for (ScreenObjectWrapper arrow : staticArrowWrappers) {
					arrows.add(guiHelper.drawableBuilder(arrow.getTexture(), arrow.getTextX(), arrow.getTextY(), arrow.getWidth(), arrow.getHeight()).build());
				}
				return arrows;
			}
		});
	}

	public void setAnimatedArrows(IGuiHelper guiHelper, ArrowAnimatedWrapper... arrows) {
		ScreenObjectWrapper[] temp = new ScreenObjectWrapper[arrows.length];
		for (int i = 0; i < arrows.length; i++) {
			temp[i] = arrows[i].getStaticArrow();
		}
		setStaticArrows(guiHelper, temp);
		animatedArrowWrappers = arrows;
		animatedArrowDrawables = CacheBuilder.newBuilder().maximumSize(animatedArrowWrappers.length).build(new CacheLoader<Integer, List<IDrawableAnimated>>() {
			@Override
			public List<IDrawableAnimated> load(Integer time) {
				List<IDrawableAnimated> arrows = new ArrayList<>();
				for (ArrowAnimatedWrapper arrow : animatedArrowWrappers) {
					arrows.add(guiHelper.drawableBuilder(arrow.getTexture(), arrow.getTextX(), arrow.getTextY(), arrow.getWidth(), arrow.getHeight()).buildAnimated(time, arrow.getStartDirection(), false));
				}

				return arrows;
			}
		});
	}

	public void setLabels(AbstractLabelWrapper... labels) {
		this.labels = labels;
	}

}
