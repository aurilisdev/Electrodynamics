package electrodynamics.compatibility.jei.recipecategories.utils;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.gas.GasStack;
import electrodynamics.api.screen.ITexture;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.gui.types.fluidgauge.AbstractFluidGaugeObject;
import electrodynamics.compatibility.jei.utils.gui.types.gasgauge.AbstractGasGaugeObject;
import electrodynamics.compatibility.jei.utils.ingredients.ElectrodynamicsJeiTypes;
import electrodynamics.compatibility.jei.utils.ingredients.IngredientRendererGasStack;
import electrodynamics.compatibility.jei.utils.label.AbstractLabelWrapper;
import electrodynamics.compatibility.jei.utils.label.types.BiproductPercentWrapperElectroRecipe;
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
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class AbstractRecipeCategory<T> implements IRecipeCategory<T> {

	private int animationTime;

	private Component title;

	private IDrawable background;
	private IDrawable icon;

	private RecipeType<T> recipeType;

	public AbstractLabelWrapper[] labels = new AbstractLabelWrapper[0];

	public int itemBiLabelFirstIndex;

	private ArrayList<AnimatedWrapper> animatedDrawables = new ArrayList<>();
	private ArrayList<StaticWrapper> staticDrawables = new ArrayList<>();

	private SlotDataWrapper[] inputSlotWrappers = new SlotDataWrapper[0];
	private SlotDataWrapper[] outputSlotWrappers = new SlotDataWrapper[0];
	private AbstractFluidGaugeObject[] fluidInputWrappers = new AbstractFluidGaugeObject[0];
	private AbstractFluidGaugeObject[] fluidOutputWrappers = new AbstractFluidGaugeObject[0];
	private AbstractGasGaugeObject[] gasInputWrappers = new AbstractGasGaugeObject[0];
	private AbstractGasGaugeObject[] gasOutputWrappers = new AbstractGasGaugeObject[0];

	public AbstractRecipeCategory(IGuiHelper guiHelper, Component title, ItemStack inputMachine, BackgroundObject wrapper, RecipeType<T> recipeType, int animationTime) {

		this.title = title;

		this.recipeType = recipeType;

		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, inputMachine);

		ITexture texture = wrapper.getTexture();
		background = guiHelper.drawableBuilder(texture.getLocation(), wrapper.getX(), wrapper.getY(), wrapper.getWidth(), wrapper.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build();

		this.animationTime = animationTime;
	}

	@Override
	public RecipeType<T> getRecipeType() {
		return recipeType;
	}

	@Override
	public Component getTitle() {
		return title;
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
	public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {

		drawPre(graphics, recipe);

		drawStatic(graphics);
		drawAnimated(graphics);

		drawPost(graphics, recipe);

		preLabels(graphics, recipe);

		addLabels(graphics, recipe);

		postLabels(graphics, recipe);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
		setItemInputs(getItemInputs(recipe), builder);
		setFluidInputs(getFluidInputs(recipe), builder);
		setItemOutputs(getItemOutputs(recipe), builder);
		setFluidOutputs(getFluidOutputs(recipe), builder);
		setGasInputs(getGasInputs(recipe), builder);
		setGasOutputs(getGasOutputs(recipe), builder);
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
			if (!firstItemBi && wrap instanceof BiproductPercentWrapperElectroRecipe) {
				this.itemBiLabelFirstIndex = i;
				firstItemBi = true;
			}
		}

	}

	public void setInputSlots(IGuiHelper guiHelper, ItemSlotObject... inputSlots) {
		inputSlotWrappers = new SlotDataWrapper[inputSlots.length];
		ItemSlotObject slot;
		for (int i = 0; i < inputSlots.length; i++) {
			slot = inputSlots[i];
			inputSlotWrappers[i] = new SlotDataWrapper(slot.getItemXStart(), slot.getItemYStart(), slot.getRole());
			ITexture texture = slot.getTexture();
			staticDrawables.add(new StaticWrapper(slot.getX(), slot.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), slot.getWidth(), slot.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));
			if (slot.getIcon() != null) {
				ScreenObject icon = slot.getIcon();
				texture = icon.getTexture();
				staticDrawables.add(new StaticWrapper(icon.getX(), icon.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), icon.getWidth(), icon.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));
			}
		}
	}

	public void setOutputSlots(IGuiHelper guiHelper, ItemSlotObject... outputSlots) {
		outputSlotWrappers = new SlotDataWrapper[outputSlots.length];
		ItemSlotObject slot;
		for (int i = 0; i < outputSlots.length; i++) {
			slot = outputSlots[i];
			outputSlotWrappers[i] = new SlotDataWrapper(slot.getItemXStart(), slot.getItemYStart(), slot.getRole());
			ITexture texture = slot.getTexture();
			staticDrawables.add(new StaticWrapper(slot.getX(), slot.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), slot.getWidth(), slot.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));
			if (slot.getIcon() != null) {
				ScreenObject icon = slot.getIcon();
				texture = icon.getTexture();
				staticDrawables.add(new StaticWrapper(icon.getX(), icon.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), icon.getWidth(), icon.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));
			}
		}
	}

	public void setFluidInputs(IGuiHelper guiHelper, AbstractFluidGaugeObject... gauges) {
		fluidInputWrappers = gauges;
		for (AbstractFluidGaugeObject gauge : fluidInputWrappers) {
			ITexture texture = gauge.getTexture();
			staticDrawables.add(new StaticWrapper(gauge.getX(), gauge.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), gauge.getWidth(), gauge.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));
		}
	}

	public void setFluidOutputs(IGuiHelper guiHelper, AbstractFluidGaugeObject... gauges) {
		fluidOutputWrappers = gauges;
		for (AbstractFluidGaugeObject gauge : fluidOutputWrappers) {
			ITexture texture = gauge.getTexture();
			staticDrawables.add(new StaticWrapper(gauge.getX(), gauge.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), gauge.getWidth(), gauge.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));
		}
	}

	public void setGasInputs(IGuiHelper guiHelper, AbstractGasGaugeObject... gauges) {
		gasInputWrappers = gauges;
		for (AbstractGasGaugeObject gauge : gasInputWrappers) {

			ITexture texture = gauge.getTexture();
			staticDrawables.add(new StaticWrapper(gauge.getX(), gauge.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), gauge.getWidth(), gauge.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));

		}
	}

	public void setGasOutputs(IGuiHelper guiHelper, AbstractGasGaugeObject... gauges) {
		gasOutputWrappers = gauges;
		for (AbstractGasGaugeObject gauge : gasOutputWrappers) {

			ITexture texture = gauge.getTexture();
			staticDrawables.add(new StaticWrapper(gauge.getX(), gauge.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), gauge.getWidth(), gauge.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));

		}
	}

	public void setScreenObjects(IGuiHelper guiHelper, ScreenObject... objects) {

		for (ScreenObject object : objects) {

			ITexture texture = object.getTexture();
			staticDrawables.add(new StaticWrapper(object.getX(), object.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), object.getWidth(), object.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));

		}

	}

	public void setAnimatedArrows(IGuiHelper guiHelper, ArrowAnimatedObject... arrows) {

		ScreenObject staticArrow;

		for (ArrowAnimatedObject arrow : arrows) {

			ITexture texture = arrow.getTexture();
			animatedDrawables.add(new AnimatedWrapper(arrow.getX(), arrow.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), arrow.getWidth(), arrow.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).buildAnimated(getAnimationTime(), arrow.startDirection(), false)));

			staticArrow = arrow.getOffArrow();

			texture = staticArrow.getTexture();
			staticDrawables.add(new StaticWrapper(staticArrow.getX(), staticArrow.getY(), guiHelper.drawableBuilder(texture.getLocation(), texture.textureU(), texture.textureV(), staticArrow.getWidth(), staticArrow.getHeight()).setTextureSize(texture.imageWidth(), texture.imageHeight()).build()));

		}

	}

	public void setItemInputs(List<List<ItemStack>> inputs, IRecipeLayoutBuilder builder) {
		SlotDataWrapper wrapper;
		for (int i = 0; i < inputSlotWrappers.length; i++) {
			wrapper = inputSlotWrappers[i];
			builder.addSlot(wrapper.role(), wrapper.x(), wrapper.y()).addItemStacks(inputs.get(i));
		}
	}

	public void setItemOutputs(List<ItemStack> outputs, IRecipeLayoutBuilder builder) {
		SlotDataWrapper wrapper;
		for (int i = 0; i < outputSlotWrappers.length; i++) {
			wrapper = outputSlotWrappers[i];
			if (i < outputs.size()) {
				builder.addSlot(wrapper.role(), wrapper.x(), wrapper.y()).addItemStack(outputs.get(i));
			}
		}
	}

	public void setFluidInputs(List<List<FluidStack>> inputs, IRecipeLayoutBuilder builder) {
		AbstractFluidGaugeObject wrapper;
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
		AbstractFluidGaugeObject wrapper;
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

	public void setGasInputs(List<List<GasStack>> inputs, IRecipeLayoutBuilder builder) {

		AbstractGasGaugeObject wrapper;
		RecipeIngredientRole role = RecipeIngredientRole.INPUT;
		List<GasStack> stacks;
		for (int i = 0; i < gasInputWrappers.length; i++) {

			wrapper = gasInputWrappers[i];
			stacks = inputs.get(i);

			double amt = stacks.get(0).getAmount();

			double gaugeCap = wrapper.getAmount();

			if (amt > gaugeCap) {
				gaugeCap = (((int) Math.ceil(amt)) * IComponentGasHandler.TANK_MULTIPLIER) * IComponentGasHandler.TANK_MULTIPLIER + IComponentGasHandler.TANK_MULTIPLIER;
			}

			int height = (int) (Math.ceil(amt / gaugeCap * (wrapper.getHeight() - 2)));

			int oneMinusHeight = wrapper.getHeight() - height;

			builder.addSlot(role, wrapper.getX() + 1, wrapper.getY() + wrapper.getHeight() - height).addIngredients(ElectrodynamicsJeiTypes.GAS_STACK, stacks).setCustomRenderer(ElectrodynamicsJeiTypes.GAS_STACK, new IngredientRendererGasStack((int) gaugeCap, -oneMinusHeight + 1, height, wrapper.getBarsTexture()));
		}

	}

	public void setGasOutputs(List<GasStack> outputs, IRecipeLayoutBuilder builder) {

		AbstractGasGaugeObject wrapper;
		RecipeIngredientRole role = RecipeIngredientRole.OUTPUT;
		GasStack stack;
		for (int i = 0; i < gasOutputWrappers.length; i++) {

			wrapper = gasOutputWrappers[i];
			stack = outputs.get(i);

			double amt = stack.getAmount();

			double gaugeCap = wrapper.getAmount();

			if (amt > gaugeCap) {
				gaugeCap = (((int) Math.ceil(amt)) * IComponentGasHandler.TANK_MULTIPLIER) * IComponentGasHandler.TANK_MULTIPLIER + IComponentGasHandler.TANK_MULTIPLIER;
			}

			int height = (int) (Math.ceil(amt / gaugeCap * (wrapper.getHeight() - 2)));

			int oneMinusHeight = wrapper.getHeight() - height;

			builder.addSlot(role, wrapper.getX() + 1, wrapper.getY() + wrapper.getHeight() - height).addIngredient(ElectrodynamicsJeiTypes.GAS_STACK, stack).setCustomRenderer(ElectrodynamicsJeiTypes.GAS_STACK, new IngredientRendererGasStack((int) gaugeCap, -oneMinusHeight + 1, height, wrapper.getBarsTexture()));
		}
	}

	public void drawStatic(GuiGraphics graphics) {
		for (StaticWrapper wrapper : staticDrawables) {
			wrapper.stat().draw(graphics, wrapper.x(), wrapper.y());
		}
	}

	public void drawAnimated(GuiGraphics graphics) {
		for (AnimatedWrapper wrapper : animatedDrawables) {
			wrapper.anim().draw(graphics, wrapper.x(), wrapper.y());
		}
	}

	public void drawPre(GuiGraphics graphics, T recipe) {

	}

	public void drawPost(GuiGraphics graphics, T recipe) {

	}

	public void addLabels(GuiGraphics graphics, T recipe) {
		Font font = Minecraft.getInstance().font;
		for (AbstractLabelWrapper wrap : labels) {
			Component text = wrap.getComponent(this, recipe);
			if (wrap.xIsEnd()) {
				graphics.drawString(font, text, wrap.getXPos() - font.width(text.getVisualOrderText()), wrap.getYPos(), wrap.getColor(), false);
			} else {
				graphics.drawString(font, text, wrap.getXPos(), wrap.getYPos(), wrap.getColor(), false);
			}
		}
	}

	public void preLabels(GuiGraphics graphics, T recipe) {

	}

	public void postLabels(GuiGraphics graphics, T recipe) {

	}

	public List<List<ItemStack>> getItemInputs(T recipe) {
		return new ArrayList<>();
	}

	public List<ItemStack> getItemOutputs(T recipe) {
		return new ArrayList<>();
	}

	public List<List<FluidStack>> getFluidInputs(T recipe) {
		return new ArrayList<>();
	}

	public List<FluidStack> getFluidOutputs(T recipeo) {
		return new ArrayList<>();
	}

	public List<List<GasStack>> getGasInputs(T recipe) {
		return new ArrayList<>();
	}

	public List<GasStack> getGasOutputs(T recipe) {
		return new ArrayList<>();
	}

	private static record SlotDataWrapper(int x, int y, RecipeIngredientRole role) {

	}

	private static record StaticWrapper(int x, int y, IDrawableStatic stat) {

	}

	private static record AnimatedWrapper(int x, int y, IDrawableAnimated anim) {

	}

}
