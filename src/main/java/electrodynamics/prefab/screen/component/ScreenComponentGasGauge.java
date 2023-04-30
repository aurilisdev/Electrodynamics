package electrodynamics.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentGasGauge extends ScreenComponentGeneric {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/gas.png");

	protected final Supplier<GasTank> gasTank;

	public ScreenComponentGasGauge(Supplier<GasTank> gasStack, IScreenWrapper gui, int x, int y) {
		super(GasGaugeTextures.BACKGROUND_DEFAULT, gui, x, y);
		this.gasTank = gasStack;
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);

		GasTank tank = gasTank.get();

		GasGaugeTextures texture;

		if (tank != null) {

			texture = GasGaugeTextures.MERCURY_FLUID;

			RenderingUtils.bindTexture(texture.getLocation());

			int progress = (int) ((tank.getGas().getAmount() / tank.getCapacity()) * texture.textureHeight());

			gui.drawTexturedRect(stack, guiWidth + xLocation + 1, guiHeight + yLocation + texture.textureHeight() - progress, texture.textureU(), texture.textureV() + texture.textureHeight() - progress, texture.textureWidth(), progress, texture.imageWidth(), texture.imageHeight());

		}

		texture = GasGaugeTextures.LEVEL_DEFAULT;

		RenderingUtils.bindTexture(texture.getLocation());

		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());

	}

	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis) {
		if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, super.texture.textureWidth(), super.texture.textureHeight())) {

			GasTank tank = gasTank.get();

			List<FormattedCharSequence> tooltips = new ArrayList<>();

			if (tank == null) {
				return;
			}

			GasStack gas = tank.getGas();

			if (gas.isEmpty()) {

				//tooltips.add(gas.getGas().getDescription().getVisualOrderText());
				tooltips.add(Component.literal( "0 / " + ChatFormatter.formatFluidMilibuckets(tank.getCapacity())).getVisualOrderText());

			} else {

				tooltips.add(gas.getGas().getDescription().getVisualOrderText());
				tooltips.add(Component.literal(ChatFormatter.formatFluidMilibuckets(tank.getGasAmount()) + " / " + ChatFormatter.formatFluidMilibuckets(tank.getCapacity())).getVisualOrderText());
				tooltips.add(Component.literal(ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN)).getVisualOrderText());
				tooltips.add(Component.literal(ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnit.PRESSURE_ATM)).getVisualOrderText());

			}

			gui.displayTooltips(stack, tooltips, xAxis, yAxis);

		}
	}

	public enum GasGaugeTextures implements ITexture {
		BACKGROUND_DEFAULT(14, 49, 0, 0, 256, 256, TEXTURE), LEVEL_DEFAULT(14, 49, 14, 0, 256, 256, TEXTURE), MERCURY_FLUID(14, 49, 28, 0, 256, 256, TEXTURE);

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		GasGaugeTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.loc = loc;
		}

		@Override
		public ResourceLocation getLocation() {
			return loc;
		}

		@Override
		public int imageHeight() {
			return imageHeight;
		}

		@Override
		public int imageWidth() {
			return imageWidth;
		}

		@Override
		public int textureHeight() {
			return textureHeight;
		}

		@Override
		public int textureU() {
			return textureU;
		}

		@Override
		public int textureV() {
			return textureV;
		}

		@Override
		public int textureWidth() {
			return textureWidth;
		}

	}

}
