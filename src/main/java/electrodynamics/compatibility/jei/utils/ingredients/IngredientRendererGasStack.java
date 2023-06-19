package electrodynamics.compatibility.jei.utils.ingredients;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
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
	
	private final int tankAmount;
	private final int mercuryOffset;
	private final int tooltipHeight;
	private final GasGaugeTextureWrapper gasGaugeTextureWrapper;
	
	
	public IngredientRendererGasStack(int tankAmount, int mercuryOffset, int tooltipHeight, GasGaugeTextureWrapper gasGaugeTextureWrapper) {
		this.tankAmount = tankAmount;
		this.mercuryOffset = mercuryOffset;
		this.tooltipHeight = tooltipHeight;
		this.gasGaugeTextureWrapper = gasGaugeTextureWrapper;
	}
	
	@Override
	public void render(PoseStack stack, GasStack ingredient) {
		if(ingredient.isEmpty()) {
			return;
		}
		stack.pushPose();
		
		ScreenComponentGasGauge.renderMercuryTexture(stack, 0, mercuryOffset, (float) ingredient.getAmount() / (float) tankAmount);
		
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
		return gasGaugeTextureWrapper.width() - 2;
	}
	
	@Override
	public int getHeight() {
		return tooltipHeight - 1;
	}
	
	public static record GasGaugeTextureWrapper(ResourceLocation loc, int xOffset, int yOffset, int u, int v, int width, int height) {
		
	}

}
