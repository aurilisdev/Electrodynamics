package electrodynamics.compatibility.jei.utils.ingredients;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.prefab.utilities.RenderingUtils;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;

public class IngredientRendererGasStack implements IIngredientRenderer<GasStack> {

	public static final IIngredientRenderer<GasStack> LIST_RENDERER = new IIngredientRenderer<>() {

		@Override
		public void render(PoseStack stack, GasStack ingredient) {
		}

		@Override
		public List<Component> getTooltip(GasStack ingredient, TooltipFlag tooltipFlag) {
			return new ArrayList<>();
		}
		
	};
	
	private final GasTextureWrapper gasTextureWrapper;
	private final GasGaugeTextureWrapper gasGaugeTextureWrapper;
	
	
	public IngredientRendererGasStack(GasTextureWrapper gasTextureWrapper, GasGaugeTextureWrapper gasGaugeTextureWrapper) {
		this.gasTextureWrapper = gasTextureWrapper;
		this.gasGaugeTextureWrapper = gasGaugeTextureWrapper;
	}
	
	@Override
	public void render(PoseStack stack, GasStack ingredient) {
		if(ingredient.isEmpty()) {
			return;
		}
		stack.pushPose();
		
		RenderingUtils.bindTexture(gasTextureWrapper.loc());
		Screen.blit(stack, 0, 0, gasTextureWrapper.u(), gasTextureWrapper.v(), gasTextureWrapper.width(), gasTextureWrapper.height(), 256, 256);
		
		RenderingUtils.bindTexture(gasGaugeTextureWrapper.loc());
		Screen.blit(stack, gasGaugeTextureWrapper.xOffset(), gasGaugeTextureWrapper.yOffset(), gasGaugeTextureWrapper.u(), gasGaugeTextureWrapper.v(), gasGaugeTextureWrapper.width(), gasGaugeTextureWrapper.height(), 256, 256);
		
		stack.popPose();
	}

	@Override
	public List<Component> getTooltip(GasStack ingredient, TooltipFlag tooltipFlag) {
		List<Component> tooltips = new ArrayList<>();
		tooltips.add(ingredient.getGas().getDescription());
		if(!ingredient.isEmpty()) {
			tooltips.add(Component.literal(ChatFormatter.formatFluidMilibuckets(ingredient.getAmount())).withStyle(ChatFormatting.GRAY));
			tooltips.add(Component.literal(ChatFormatter.getChatDisplayShort(ingredient.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.GRAY));
			tooltips.add(Component.literal(ChatFormatter.getChatDisplayShort(ingredient.getPressure(), DisplayUnit.PRESSURE_ATM)).withStyle(ChatFormatting.GRAY));
		} 
		
		return tooltips;
	}
	
	@Override
	public int getWidth() {
		return gasTextureWrapper.width();
	}
	
	@Override
	public int getHeight() {
		return gasTextureWrapper.height() - 1;
	}
	
	public static record GasTextureWrapper(ResourceLocation loc, int u, int v, int width, int height) {
		
	}
	
	public static record GasGaugeTextureWrapper(ResourceLocation loc, int xOffset, int yOffset, int u, int v, int width, int height) {
		
	}

}
