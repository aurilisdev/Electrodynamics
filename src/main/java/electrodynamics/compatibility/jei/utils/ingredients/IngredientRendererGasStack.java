package electrodynamics.compatibility.jei.utils.ingredients;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.compatibility.jei.utils.gui.types.gasgauge.IGasGaugeTexture;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import electrodynamics.prefab.utilities.RenderingUtils;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
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
	private final IGasGaugeTexture bars;

	public IngredientRendererGasStack(int tankAmount, int mercuryOffset, int tooltipHeight, IGasGaugeTexture bars) {
		this.tankAmount = tankAmount;
		this.mercuryOffset = mercuryOffset;
		this.tooltipHeight = tooltipHeight;
		this.bars = bars;
	}

	@Override
	public void render(PoseStack stack, GasStack ingredient) {
		if (ingredient.isEmpty()) {
			return;
		}
		stack.pushPose();

		float ratio = (float) ingredient.getAmount() / tankAmount;

		ScreenComponentGasGauge.renderMercuryTexture(stack, 0, mercuryOffset, ratio);

		RenderingUtils.bindTexture(bars.getLocation());
		GuiComponent.blit(stack, bars.getXOffset(), mercuryOffset + bars.getYOffset(), bars.textureU(), bars.textureV(), bars.textureWidth(), bars.textureHeight(), bars.imageWidth(), bars.imageHeight());

		stack.popPose();
	}

	@Override
	public List<Component> getTooltip(GasStack ingredient, TooltipFlag tooltipFlag) {
		List<Component> tooltips = new ArrayList<>();
		tooltips.add(ingredient.getGas().getDescription());
		if (!ingredient.isEmpty()) {
			tooltips.add(ChatFormatter.formatFluidMilibuckets(ingredient.getAmount()).withStyle(ChatFormatting.GRAY));
			tooltips.add(ChatFormatter.getChatDisplayShort(ingredient.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN).withStyle(ChatFormatting.GRAY));
			tooltips.add(ChatFormatter.getChatDisplayShort(ingredient.getPressure(), DisplayUnit.PRESSURE_ATM).withStyle(ChatFormatting.GRAY));
		}

		return tooltips;
	}

	@Override
	public int getWidth() {
		return bars.textureWidth() - 2;
	}

	@Override
	public int getHeight() {
		return tooltipHeight - 1;
	}

}
