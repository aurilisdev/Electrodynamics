package electrodynamics.compatibility.jei.recipecategories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.ArrowAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.fluid.GenericFluidGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.gas.GenericGasGaugeWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.BucketSlotWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.GenericItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.ingredients.ElectrodynamicsJeiTypes;
import electrodynamics.compatibility.jei.utils.ingredients.IngredientRendererGasStack;
import electrodynamics.compatibility.jei.utils.ingredients.IngredientRendererGasStack.GasGaugeTextureWrapper;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import electrodynamics.compatibility.jei.utils.label.BiproductPercentWrapper;
import electrodynamics.prefab.tile.components.utils.IComponentFluidHandler;
import electrodynamics.prefab.tile.components.utils.IComponentGasHandler;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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
import net.minecraftforge.fluids.FluidStack;

public abstract class ElectrodynamicsRecipeCategory<T extends ElectrodynamicsRecipe> implements IRecipeCategory<T> {

	private int animationTime;

	private String recipeGroup;

	private IDrawable background;
	private IDrawable icon;

	private Class<T> recipeCategoryClass;

	public AbstractLabelWrapper[] labels;

	public int itemBiLabelFirstIndex;

	private LoadingCache<Integer, List<IDrawableAnimated>> animatedArrowDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> staticArrowDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> inputSlotDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> outputSlotDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> fluidInputDawables;
	private LoadingCache<Integer, List<IDrawableStatic>> fluidOutputDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> gasInputDrawables;
	private LoadingCache<Integer, List<IDrawableStatic>> gasOutputDrawables;

	private GenericItemSlotWrapper[] inputSlotWrappers = new GenericItemSlotWrapper[0];
	private GenericItemSlotWrapper[] outputSlotWrappers = new GenericItemSlotWrapper[0];
	private GenericFluidGaugeWrapper[] fluidInputWrappers = new GenericFluidGaugeWrapper[0];
	private GenericFluidGaugeWrapper[] fluidOutputWrappers = new GenericFluidGaugeWrapper[0];
	private GenericGasGaugeWrapper[] gasInputWrappers = new GenericGasGaugeWrapper[0];
	private GenericGasGaugeWrapper[] gasOutputWrappers = new GenericGasGaugeWrapper[0];
	private ArrowAnimatedWrapper[] animatedArrowWrappers = new ArrowAnimatedWrapper[0];
	private ScreenObjectWrapper[] staticArrowWrappers = new ScreenObjectWrapper[0];

	public ElectrodynamicsRecipeCategory(IGuiHelper guiHelper, String recipeGroup, ItemStack inputMachine, BackgroundWrapper wrapper, Class<T> recipeCategoryClass, int animationTime) {

		this.recipeGroup = recipeGroup;

		this.recipeCategoryClass = recipeCategoryClass;

		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, inputMachine);
		background = guiHelper.createDrawable(wrapper.getTexture(), wrapper.getTextX(), wrapper.getTextY(), wrapper.getWidth(), wrapper.getHeight());

		this.animationTime = animationTime;
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
	public void draw(ElectrodynamicsRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		drawInputSlots(matrixStack);
		drawOutputSlots(matrixStack);
		drawStaticArrows(matrixStack);
		drawFluidInputs(matrixStack);
		drawFluidOutputs(matrixStack);
		drawGasInputs(matrixStack);
		drawGasOutputs(matrixStack);
		drawAnimatedArrows(matrixStack);

		addDescriptions(matrixStack, recipe);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ElectrodynamicsRecipe recipe, IFocusGroup focuses) {
		setItemInputs(getItemInputs(recipe), builder);
		setFluidInputs(getFluidInputs(recipe), builder);
		setItemOutputs(getItemOutputs(recipe), builder);
		setFluidOutputs(getFluidOutputs(recipe), builder);
		setGasInputs(getGasInputs(recipe), builder);
		setGasOutputs(getGasOutputs(recipe), builder);
	}

	public String getRecipeGroup() {
		return recipeGroup;
	}

	public void addDescriptions(PoseStack stack, ElectrodynamicsRecipe recipe) {
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

	public int getAnimationTime() {
		return animationTime;
	}

	public void setLabels(AbstractLabelWrapper... labels) {
		this.labels = labels;
		AbstractLabelWrapper wrap;
		boolean firstItemBi = false;
		for (int i = 0; i < labels.length; i++) {
			wrap = labels[i];
			if (!firstItemBi && wrap instanceof BiproductPercentWrapper) {
				this.itemBiLabelFirstIndex = i;
				firstItemBi = true;
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

	public void setFluidInputs(IGuiHelper guiHelper, GenericFluidGaugeWrapper... gauges) {
		fluidInputWrappers = gauges;
		fluidInputDawables = CacheBuilder.newBuilder().maximumSize(fluidInputWrappers.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			@Override
			public List<IDrawableStatic> load(Integer time) {
				List<IDrawableStatic> gauges = new ArrayList<>();
				for (ScreenObjectWrapper gauge : fluidInputWrappers) {
					gauges.add(guiHelper.drawableBuilder(gauge.getTexture(), gauge.getTextX(), gauge.getTextY(), gauge.getWidth(), gauge.getHeight()).build());
				}
				return gauges;
			}
		});
	}

	public void setFluidOutputs(IGuiHelper guiHelper, GenericFluidGaugeWrapper... gauges) {
		fluidOutputWrappers = gauges;
		fluidOutputDrawables = CacheBuilder.newBuilder().maximumSize(fluidOutputWrappers.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			@Override
			public List<IDrawableStatic> load(Integer time) {
				List<IDrawableStatic> gauges = new ArrayList<>();
				for (ScreenObjectWrapper gauge : fluidOutputWrappers) {
					gauges.add(guiHelper.drawableBuilder(gauge.getTexture(), gauge.getTextX(), gauge.getTextY(), gauge.getWidth(), gauge.getHeight()).build());
				}
				return gauges;
			}
		});
	}

	public void setGasInputs(IGuiHelper guiHelper, GenericGasGaugeWrapper... gauges) {
		gasInputWrappers = gauges;
		gasInputDrawables = CacheBuilder.newBuilder().maximumSize(gasInputWrappers.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {

			@Override
			public List<IDrawableStatic> load(Integer key) {
				List<IDrawableStatic> gauges = new ArrayList<>();

				for (GenericGasGaugeWrapper gauge : gasInputWrappers) {

					gauges.add(guiHelper.drawableBuilder(gauge.getTexture(), gauge.getTextX(), gauge.getTextY(), gauge.getWidth(), gauge.getHeight()).build());

				}

				return gauges;
			}

		});
	}

	public void setGasOutputs(IGuiHelper guiHelper, GenericGasGaugeWrapper... gauges) {
		gasOutputWrappers = gauges;
		gasOutputDrawables = CacheBuilder.newBuilder().maximumSize(gasOutputWrappers.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {

			@Override
			public List<IDrawableStatic> load(Integer key) {
				List<IDrawableStatic> gauges = new ArrayList<>();

				for (GenericGasGaugeWrapper gauge : gasOutputWrappers) {

					gauges.add(guiHelper.drawableBuilder(gauge.getTexture(), gauge.getTextX(), gauge.getTextY(), gauge.getWidth(), gauge.getHeight()).build());

				}

				return gauges;
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

	public void setItemInputs(List<List<ItemStack>> inputs, IRecipeLayoutBuilder builder) {
		GenericItemSlotWrapper wrapper;
		RecipeIngredientRole role;
		for (int i = 0; i < inputSlotWrappers.length; i++) {
			wrapper = inputSlotWrappers[i];
			role = wrapper instanceof BucketSlotWrapper ? RecipeIngredientRole.RENDER_ONLY : RecipeIngredientRole.INPUT;
			builder.addSlot(role, wrapper.itemXStart(), wrapper.itemYStart()).addItemStacks(inputs.get(i));
		}
	}

	public void setItemOutputs(List<ItemStack> outputs, IRecipeLayoutBuilder builder) {
		GenericItemSlotWrapper wrapper;
		RecipeIngredientRole role;
		for (int i = 0; i < outputSlotWrappers.length; i++) {
			wrapper = outputSlotWrappers[i];
			role = wrapper instanceof BucketSlotWrapper ? RecipeIngredientRole.RENDER_ONLY : RecipeIngredientRole.OUTPUT;
			if (i < outputs.size()) {
				builder.addSlot(role, wrapper.itemXStart(), wrapper.itemYStart()).addItemStack(outputs.get(i));
			}
		}
	}

	public void setFluidInputs(List<List<FluidStack>> inputs, IRecipeLayoutBuilder builder) {
		GenericFluidGaugeWrapper wrapper;
		RecipeIngredientRole role = RecipeIngredientRole.INPUT;
		FluidStack stack;
		for (int i = 0; i < fluidInputWrappers.length; i++) {
			wrapper = fluidInputWrappers[i];
			stack = inputs.get(i).get(0);

			int amt = stack.getAmount();

			int gaugeCap = wrapper.getAmount();

			if (amt > gaugeCap) {

				gaugeCap = (amt / IComponentFluidHandler.TANK_MULTIPLER) * IComponentFluidHandler.TANK_MULTIPLER + IComponentFluidHandler.TANK_MULTIPLER;

			}

			int height = (int) Math.ceil((float) amt / (float) gaugeCap * wrapper.getFluidTextHeight());

			builder.addSlot(role, wrapper.getFluidXPos(), wrapper.getFluidYPos() - height).setFluidRenderer(stack.getAmount(), false, wrapper.getFluidTextWidth(), height).addIngredients(ForgeTypes.FLUID_STACK, inputs.get(i));
		}
	}

	public void setFluidOutputs(List<FluidStack> outputs, IRecipeLayoutBuilder builder) {
		GenericFluidGaugeWrapper wrapper;
		RecipeIngredientRole role = RecipeIngredientRole.OUTPUT;
		FluidStack stack;
		for (int i = 0; i < fluidOutputWrappers.length; i++) {
			wrapper = fluidOutputWrappers[i];
			stack = outputs.get(i);

			int amt = stack.getAmount();

			int gaugeCap = wrapper.getAmount();

			if (amt > gaugeCap) {

				gaugeCap = (amt / IComponentFluidHandler.TANK_MULTIPLER) * IComponentFluidHandler.TANK_MULTIPLER + IComponentFluidHandler.TANK_MULTIPLER;

			}

			int height = (int) Math.ceil((float) amt / (float) gaugeCap * wrapper.getFluidTextHeight());
			builder.addSlot(role, wrapper.getFluidXPos(), wrapper.getFluidYPos() - height).setFluidRenderer(stack.getAmount(), false, wrapper.getFluidTextWidth(), height).addIngredient(ForgeTypes.FLUID_STACK, stack);
		}
	}

	public void setGasInputs(List<GasStack> inputs, IRecipeLayoutBuilder builder) {
		
		GenericGasGaugeWrapper wrapper;
		RecipeIngredientRole role = RecipeIngredientRole.INPUT;
		GasStack stack;
		GasGaugeTextureWrapper gasGaugeWrapper;
		for(int i = 0; i < gasInputWrappers.length; i++) {
			
			wrapper = gasInputWrappers[i];
			stack = inputs.get(i);
			
			double amt = stack.getAmount();
			
			double gaugeCap = wrapper.getAmount();
			
			if(amt > gaugeCap) {
				gaugeCap = (((int) Math.ceil(amt)) * IComponentGasHandler.TANK_MULTIPLIER) * IComponentGasHandler.TANK_MULTIPLIER + IComponentGasHandler.TANK_MULTIPLIER;
			}
			
			int height = (int) (Math.ceil(amt / gaugeCap * wrapper.getHeight()));
			
			int oneMinusHeight = wrapper.getHeight() - height;
			
			gasGaugeWrapper = new GasGaugeTextureWrapper(wrapper.getTexture(), 0, 0, wrapper.getGaugeOffset(), wrapper.getTextY(), wrapper.getWidth(), wrapper.getHeight());
			
			builder.addSlot(role, wrapper.getXPos(), wrapper.getYPos() + height).addIngredient(ElectrodynamicsJeiTypes.GAS_STACK, stack).setCustomRenderer(ElectrodynamicsJeiTypes.GAS_STACK, new IngredientRendererGasStack((int) gaugeCap, -oneMinusHeight + 1, 0, gasGaugeWrapper));
		}
		
	}

	public void setGasOutputs(List<GasStack> outputs, IRecipeLayoutBuilder builder) {
		
		GenericGasGaugeWrapper wrapper;
		RecipeIngredientRole role = RecipeIngredientRole.OUTPUT;
		GasStack stack;
		GasGaugeTextureWrapper gasGaugeWrapper;
		for(int i = 0; i < gasOutputWrappers.length; i++) {
			
			wrapper = gasOutputWrappers[i];
			stack = outputs.get(i);
			
			double amt = stack.getAmount();
			
			double gaugeCap = wrapper.getAmount();
			
			if(amt > gaugeCap) {
				gaugeCap = (((int) Math.ceil(amt)) * IComponentGasHandler.TANK_MULTIPLIER) * IComponentGasHandler.TANK_MULTIPLIER + IComponentGasHandler.TANK_MULTIPLIER;
			}
			
			int height = (int) (Math.ceil(amt / gaugeCap * (wrapper.getHeight() - 2)));
			
			int oneMinusHeight = wrapper.getHeight() - height;
			
			gasGaugeWrapper = new GasGaugeTextureWrapper(wrapper.getTexture(), -1, height - wrapper.getHeight(), wrapper.getGaugeOffset(), wrapper.getTextY(), wrapper.getWidth(), wrapper.getHeight());
			
			builder.addSlot(role, wrapper.getXPos() + 1, wrapper.getYPos() + wrapper.getHeight() - height).addIngredient(ElectrodynamicsJeiTypes.GAS_STACK, stack).setCustomRenderer(ElectrodynamicsJeiTypes.GAS_STACK, new IngredientRendererGasStack((int) gaugeCap, -oneMinusHeight + 1, height, gasGaugeWrapper));
		}
	}

	public void drawInputSlots(PoseStack matrixStack) {
		if (inputSlotDrawables == null) {
			return;
		}
		List<IDrawableStatic> inputSlots = inputSlotDrawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < inputSlots.size(); i++) {
			image = inputSlots.get(i);
			wrapper = inputSlotWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawOutputSlots(PoseStack matrixStack) {
		if (outputSlotDrawables == null) {
			return;
		}
		List<IDrawableStatic> outputSlots = outputSlotDrawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < outputSlots.size(); i++) {
			image = outputSlots.get(i);
			wrapper = outputSlotWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawFluidInputs(PoseStack matrixStack) {
		if (fluidInputDawables == null) {
			return;
		}
		List<IDrawableStatic> inFluidGauges = fluidInputDawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < inFluidGauges.size(); i++) {
			image = inFluidGauges.get(i);
			wrapper = fluidInputWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawFluidOutputs(PoseStack matrixStack) {
		if (fluidOutputDrawables == null) {
			return;
		}
		List<IDrawableStatic> fluidGauges = fluidOutputDrawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < fluidGauges.size(); i++) {
			image = fluidGauges.get(i);
			wrapper = fluidOutputWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawGasInputs(PoseStack matrixStack) {
		if (gasInputDrawables == null) {
			return;
		}
		List<IDrawableStatic> gasGauges = gasInputDrawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < gasGauges.size(); i++) {
			image = gasGauges.get(i);
			wrapper = gasInputWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawGasOutputs(PoseStack matrixStack) {
		if (gasOutputDrawables == null) {
			return;
		}
		List<IDrawableStatic> gasGauges = gasOutputDrawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < gasGauges.size(); i++) {
			image = gasGauges.get(i);
			wrapper = gasOutputWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawStaticArrows(PoseStack matrixStack) {
		if (staticArrowDrawables == null) {
			return;
		}
		List<IDrawableStatic> arrows = staticArrowDrawables.getUnchecked(getAnimationTime());
		IDrawableStatic image;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < arrows.size(); i++) {
			image = arrows.get(i);
			wrapper = staticArrowWrappers[i];
			image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public void drawAnimatedArrows(PoseStack matrixStack) {
		if (animatedArrowDrawables == null) {
			return;
		}
		List<IDrawableAnimated> arrows = animatedArrowDrawables.getUnchecked(getAnimationTime());
		IDrawableAnimated arrow;
		ScreenObjectWrapper wrapper;
		for (int i = 0; i < arrows.size(); i++) {
			arrow = arrows.get(i);
			wrapper = animatedArrowWrappers[i];
			arrow.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
	}

	public List<List<ItemStack>> getItemInputs(ElectrodynamicsRecipe electro) {
		return new ArrayList<>();
	}

	public List<ItemStack> getItemOutputs(ElectrodynamicsRecipe electro) {
		return new ArrayList<>();
	}

	public List<List<FluidStack>> getFluidInputs(ElectrodynamicsRecipe electro) {
		return new ArrayList<>();
	}

	public List<FluidStack> getFluidOutputs(ElectrodynamicsRecipe electro) {
		return new ArrayList<>();
	}

	public List<GasStack> getGasInputs(ElectrodynamicsRecipe electro) {
		return new ArrayList<>();
	}

	public List<GasStack> getGasOutputs(ElectrodynamicsRecipe electro) {
		return new ArrayList<>();
	}

}
