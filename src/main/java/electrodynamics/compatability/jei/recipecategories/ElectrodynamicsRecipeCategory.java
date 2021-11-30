package electrodynamics.compatability.jei.recipecategories;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatability.jei.utils.gui.ScreenObjectWrapper;
import electrodynamics.compatability.jei.utils.gui.arrows.animated.ArrowAnimatedWrapper;
import electrodynamics.compatability.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatability.jei.utils.gui.fluid.GenericFluidGaugeWrapper;
import electrodynamics.compatability.jei.utils.gui.item.GenericItemSlotWrapper;
import electrodynamics.compatability.jei.utils.label.GenericLabelWrapper;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class ElectrodynamicsRecipeCategory<T extends ElectrodynamicsRecipe> implements IRecipeCategory<T> {

    private int ANIMATION_LENGTH;

    private String RECIPE_GROUP;
    private String MOD_ID;

    private IDrawable BACKGROUND;
    private IDrawable ICON;

    private Class<T> RECIPE_CATEGORY_CLASS;
    
    private GenericLabelWrapper[] LABELS;
    
    private LoadingCache<Integer, List<IDrawableAnimated>> ANIMATED_ARROWS;
    private LoadingCache<Integer, List<IDrawableStatic>> STATIC_ARROWS;
    private LoadingCache<Integer, List<IDrawableStatic>> INPUT_SLOTS;
    private LoadingCache<Integer, List<IDrawableStatic>> OUTPUT_SLOTS;
    private LoadingCache<Integer, List<IDrawableStatic>> FLUID_INPUTS;
    private LoadingCache<Integer, List<IDrawableStatic>> FLUID_OUTPUTS;
    
    private GenericItemSlotWrapper[] inSlots = new GenericItemSlotWrapper[0];
    private GenericItemSlotWrapper[] outSlots = new GenericItemSlotWrapper[0];
    private GenericFluidGaugeWrapper[] fluidInputs = new GenericFluidGaugeWrapper[0];
    private GenericFluidGaugeWrapper[] fluidOutputs = new GenericFluidGaugeWrapper[0];
    private ArrowAnimatedWrapper[] animArrows = new ArrowAnimatedWrapper[0];
    private ScreenObjectWrapper[] staticArrows = new ScreenObjectWrapper[0];

    public ElectrodynamicsRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine,
	    BackgroundWrapper wrapper, Class<T> recipeCategoryClass, int animationTime) {

		RECIPE_GROUP = recipeGroup;
		MOD_ID = modID;
	
		RECIPE_CATEGORY_CLASS = recipeCategoryClass;
	
		ICON = guiHelper.createDrawableIngredient(inputMachine);
		BACKGROUND = guiHelper.createDrawable(new ResourceLocation(modID, wrapper.getTexture()), wrapper.getTextX(), wrapper.getTextY(), wrapper.getLength(), wrapper.getWidth());
	
		ANIMATION_LENGTH = animationTime;
    }

    @Override
    public Class<T> getRecipeClass() {
    	return RECIPE_CATEGORY_CLASS;
    }

    @Override
    public Component getTitle() {
    	return new TranslatableComponent("gui.jei.category." + RECIPE_GROUP);
    }

    @Override
    public IDrawable getBackground() {
    	return BACKGROUND;
    }

    @Override
    public IDrawable getIcon() {
    	return ICON;
    }

    public String getRecipeGroup() {
    	return RECIPE_GROUP;
    }

    public int getAnimationTime() {
    	return ANIMATION_LENGTH;
    }
    
    public void addDescriptions(PoseStack stack) {
    	Font fontRenderer = Minecraft.getInstance().font;
    	TranslatableComponent text;
    	for(GenericLabelWrapper wrap : LABELS) {
    		text = new TranslatableComponent("gui.jei.category." + getRecipeGroup() + ".info." + wrap.getName(), getAnimationTime() / 20);
    		fontRenderer.draw(stack, text, wrap.getEndXPos() - fontRenderer.width(text), wrap.getYPos(), wrap.getColor());
    	}
    }
    
    public void setInputSlots(IGuiHelper guiHelper, GenericItemSlotWrapper...inputSlots) {
    	inSlots = inputSlots;
    	INPUT_SLOTS = CacheBuilder.newBuilder().maximumSize(inputSlots.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			public List<IDrawableStatic> load(Integer time){
				List<IDrawableStatic> slots = new ArrayList<>();
				for(ScreenObjectWrapper slot : inputSlots) {
					slots.add(guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, slot.getTexture()), slot.getTextX(), slot.getTextY(), slot.getLength(), slot.getWidth()).build());
				}
				return slots;
			}
		});
    }
    
    public void setOutputSlots(IGuiHelper guiHelper, GenericItemSlotWrapper...outputSlots) {
    	outSlots = outputSlots;
    	OUTPUT_SLOTS = CacheBuilder.newBuilder().maximumSize(outputSlots.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			public List<IDrawableStatic> load(Integer time){
				List<IDrawableStatic> slots = new ArrayList<>();
				for(ScreenObjectWrapper slot : outputSlots) {
					slots.add(guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, slot.getTexture()), slot.getTextX(), slot.getTextY(), slot.getLength(), slot.getWidth()).build());
				}
				return slots;
			}
		});
    }
    
    public void setFluidInputs(IGuiHelper guiHelper, GenericFluidGaugeWrapper...gauges) {
    	fluidInputs = gauges;
    	FLUID_INPUTS = CacheBuilder.newBuilder().maximumSize(fluidInputs.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			public List<IDrawableStatic> load(Integer time){
				List<IDrawableStatic> gauges = new ArrayList<>();
				for(ScreenObjectWrapper gauge : fluidInputs) {
					gauges.add(guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, gauge.getTexture()), gauge.getTextX(), gauge.getTextY(), gauge.getLength(), gauge.getWidth()).build());
				}
				return gauges;
			}
		});
    }
    
    public void setFluidOutputs(IGuiHelper guiHelper, GenericFluidGaugeWrapper...gauges) {
    	fluidOutputs = gauges;
    	FLUID_OUTPUTS = CacheBuilder.newBuilder().maximumSize(fluidOutputs.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			public List<IDrawableStatic> load(Integer time){
				List<IDrawableStatic> gauges = new ArrayList<>();
				for(ScreenObjectWrapper gauge : fluidOutputs) {
					gauges.add(guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, gauge.getTexture()), gauge.getTextX(), gauge.getTextY(), gauge.getLength(), gauge.getWidth()).build());
				}
				return gauges;
			}
		});
    }
    
    public void setStaticArrows(IGuiHelper guiHelper, ScreenObjectWrapper...arrows) {
    	if(staticArrows.length == 0) {
    		staticArrows = arrows;
    	} else {
    		staticArrows = ArrayUtils.addAll(staticArrows, arrows);
    	}
    	STATIC_ARROWS = CacheBuilder.newBuilder().maximumSize(staticArrows.length).build(new CacheLoader<Integer, List<IDrawableStatic>>() {
			public List<IDrawableStatic> load(Integer time){
				List<IDrawableStatic> arrows = new ArrayList<>();
				for(ScreenObjectWrapper arrow : staticArrows) {
					arrows.add(guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, arrow.getTexture()), arrow.getTextX(), arrow.getTextY(), arrow.getLength(), arrow.getWidth()).build());
				}
				return arrows;
			}
		});
    }
    
    public void setAnimatedArrows(IGuiHelper guiHelper, ArrowAnimatedWrapper...arrows) {
    	ScreenObjectWrapper[] temp = new ScreenObjectWrapper[arrows.length];
		for(int i = 0; i < arrows.length; i++) {
			temp[i] = arrows[i].getStaticArrow();
		}
		setStaticArrows(guiHelper, temp);
    	animArrows = arrows;
    	ANIMATED_ARROWS = CacheBuilder.newBuilder().maximumSize(animArrows.length).build(new CacheLoader<Integer, List<IDrawableAnimated>>() {
		    @Override
		    public List<IDrawableAnimated> load(Integer time) {
		    	List<IDrawableAnimated> arrows = new ArrayList<>();
		    	for(ArrowAnimatedWrapper arrow : animArrows) {
		    		arrows.add(guiHelper.drawableBuilder(new ResourceLocation(MOD_ID, arrow.getTexture()), arrow.getTextX(), 
		    				arrow.getTextY(), arrow.getLength(), arrow.getWidth()).buildAnimated(time, arrow.getStartDirection(), false));
		    	}
		    	
			return arrows;
		    }
		});
    }
    
    public void setLabels(GenericLabelWrapper...labels) {
    	LABELS = labels;
    }
    
    public void setItemInputs(IGuiItemStackGroup guiItemStacks) {
    	GenericItemSlotWrapper wrapper;
		for(int i = 0; i < inSlots.length; i++) {
			wrapper = inSlots[i];
			guiItemStacks.init(i, true, wrapper.itemXStart(), wrapper.itemYStart());
		}
    }
    
    public void setItemOutputs(IGuiItemStackGroup guiItemStacks) {
    	GenericItemSlotWrapper wrapper;
    	int offset = inSlots.length + fluidInputs.length;
		for(int i = 0; i < outSlots.length; i++) {
			wrapper = outSlots[i];
			guiItemStacks.init(i + offset, false, wrapper.itemXStart(), wrapper.itemYStart());
		}
    }
    
    public void setFluidInputs(IGuiFluidStackGroup guiFluidStacks, List<FluidIngredient> ings) {
    	int offset = inSlots.length;
		
		GenericFluidGaugeWrapper wrap;
		FluidStack stack;
		for(int i = 0; i < fluidInputs.length; i++) {
			wrap = fluidInputs[i];
			stack = ings.get(i).getFluidStack();

			int leftHeightOffset = (int) Math.ceil(stack.getAmount() / (float) wrap.getAmount() * wrap.getFluidTextHeight());
			int leftStartY = wrap.getFluidTextYPos() - leftHeightOffset + 1;

			guiFluidStacks.init(i + offset, true, wrap.getFluidTextXPos(), leftStartY, wrap.getFluidTextWidth(), 
				leftHeightOffset, stack.getAmount(), true, null);
			
		}
    }
    
    public void setFluidOutputs(IGuiFluidStackGroup guiFluidStacks, ElectrodynamicsRecipe recipe, int indexOffset, @Nullable FluidStack recipeOutput) {
    	int offset = inSlots.length + outSlots.length + fluidInputs.length;
    	GenericFluidGaugeWrapper wrap;
    	FluidStack stack;
    	if(indexOffset == 1) {
    		wrap = fluidOutputs[0];
    		stack = recipeOutput;

    		int leftHeightOffset = (int) Math.ceil(stack.getAmount() / (float) wrap.getAmount() * wrap.getFluidTextHeight());
    		int leftStartY = wrap.getFluidTextYPos() - leftHeightOffset + 1;

    		guiFluidStacks.init(offset, false, wrap.getFluidTextXPos(), leftStartY, wrap.getFluidTextWidth(), 
    			leftHeightOffset, stack.getAmount(), true, null);
    		offset++;
    	}
    	
    	
		if(recipe.hasFluidBiproducts()) {
			ProbableFluid[] stacks = recipe.getFluidBiproducts();
			for(int i = 0; i < recipe.getFluidBiproductCount(); i++) {
				wrap = fluidOutputs[i + indexOffset];
				stack = stacks[i].getFullStack();

				int leftHeightOffset = (int) Math.ceil(stack.getAmount() / (float) wrap.getAmount() * wrap.getFluidTextHeight());
				int leftStartY = wrap.getYPos() - leftHeightOffset + 1;

				guiFluidStacks.init(i + offset - indexOffset, false, wrap.getXPos(), leftStartY, wrap.getFluidTextWidth(), 
					leftHeightOffset, stack.getAmount(), true,null);
				
			}
		}
    }
    
    public void drawInputSlots(PoseStack matrixStack) {
    	List<IDrawableStatic> inputSlots = INPUT_SLOTS.getUnchecked(getAnimationTime());
    	IDrawableStatic image;
    	ScreenObjectWrapper wrapper;
    	for(int i = 0; i < inputSlots.size(); i++) {
			image = inputSlots.get(i);
			wrapper = inSlots[i];
    		image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
    }
    
    public void drawOutputSlots(PoseStack matrixStack) {
    	List<IDrawableStatic> outputSlots = OUTPUT_SLOTS.getUnchecked(getAnimationTime());
    	IDrawableStatic image;
    	ScreenObjectWrapper wrapper;
    	for(int i = 0; i < outputSlots.size(); i++) {
			image = outputSlots.get(i);
			wrapper = outSlots[i];
    		image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
    }
    
    public void drawFluidInputs(PoseStack matrixStack) {
    	List<IDrawableStatic> inFluidGauges = FLUID_INPUTS.getUnchecked(getAnimationTime());
    	IDrawableStatic image;
    	ScreenObjectWrapper wrapper;
    	for(int i = 0; i < inFluidGauges.size(); i++) {
			image = inFluidGauges.get(i);
			wrapper = fluidInputs[i];
    		image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
    }
    
    public void drawFluidOutputs(PoseStack matrixStack) {
    	List<IDrawableStatic> fluidGauges = FLUID_OUTPUTS.getUnchecked(getAnimationTime());
    	IDrawableStatic image;
    	ScreenObjectWrapper wrapper;
    	for(int i = 0; i < fluidGauges.size(); i++) {
    		image = fluidGauges.get(i);
    		wrapper = fluidOutputs[i];
    		image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
    	}	
    }
    
    public void drawStaticArrows(PoseStack matrixStack) {
    	List<IDrawableStatic> arrows = STATIC_ARROWS.getUnchecked(getAnimationTime());
    	IDrawableStatic image;
    	ScreenObjectWrapper wrapper;
    	for(int i = 0; i < arrows.size(); i++) {
			image = arrows.get(i);
			wrapper = staticArrows[i];
    		image.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
    }
    
    public void drawAnimatedArrows(PoseStack matrixStack) {
    	List<IDrawableAnimated> arrows = ANIMATED_ARROWS.getUnchecked(getAnimationTime());
    	IDrawableAnimated arrow;
    	ScreenObjectWrapper wrapper;
    	for(int i = 0; i < arrows.size(); i++) {
			arrow = arrows.get(i);
			wrapper = animArrows[i];
    		arrow.draw(matrixStack, wrapper.getXPos(), wrapper.getYPos());
		}
    }

}
