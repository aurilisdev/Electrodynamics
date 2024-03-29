package electrodynamics.prefab.screen.component.types.gauges;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.utils.IGasTank;
import electrodynamics.api.screen.ITexture;
import electrodynamics.client.ClientRegister;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentGasGauge extends ScreenComponentGeneric {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/gas.png");

	public final Supplier<IGasTank> gasTank;

	public ScreenComponentGasGauge(Supplier<IGasTank> gasStack, int x, int y) {
		super(GasGaugeTextures.BACKGROUND_DEFAULT, x, y);
		this.gasTank = gasStack;
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);

		IGasTank tank = gasTank.get();

		if (tank != null) {

			renderMercuryTexture(graphics, guiWidth + xLocation + 1, guiHeight + yLocation + 1, (float) tank.getGasAmount() / (float) tank.getCapacity());

		}

		GasGaugeTextures texture = GasGaugeTextures.LEVEL_DEFAULT;

		graphics.blit(texture.getLocation(), guiWidth + xLocation, guiHeight + yLocation + 1, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());

	}

	@Override
	public void renderForeground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, super.texture.textureWidth(), super.texture.textureHeight())) {

			IGasTank tank = gasTank.get();

			List<FormattedCharSequence> tooltips = new ArrayList<>();

			if (tank == null) {
				return;
			}

			GasStack gas = tank.getGas();

			if (gas.isEmpty()) {

				tooltips.add(ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(tank.getCapacity())).withStyle(ChatFormatting.GRAY).getVisualOrderText());

			} else {

				tooltips.add(gas.getGas().getDescription().getVisualOrderText());
				tooltips.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(tank.getGasAmount()), ChatFormatter.formatFluidMilibuckets(tank.getCapacity())).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN).withStyle(ChatFormatting.GRAY).getVisualOrderText());
				tooltips.add(ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnit.PRESSURE_ATM).withStyle(ChatFormatting.GRAY).getVisualOrderText());

			}

			graphics.renderTooltip(gui.getFontRenderer(), tooltips, xAxis, yAxis);

		}
	}

	public static void renderMercuryTexture(GuiGraphics graphics, int x, int y, float progress) {

		TextureAtlasSprite mercury = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_MERCURY);

		Matrix4f matrix = graphics.pose().last().pose();

		RenderingUtils.bindTexture(mercury.atlasLocation());

		int height = (int) (progress * 47);

		int x1 = x;
		int x2 = x1 + 12;

		int y1 = y + 47 - height;
		int y2 = y1 + height;

		float minU = mercury.getU0();
		float maxU = mercury.getU1();

		float minV = mercury.getV0();
		float maxV = mercury.getV1();

		float deltaV = maxV - minV;

		minV = maxV - deltaV * progress;

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix, x1, y2, 0).uv(minU, maxV).endVertex();
		bufferbuilder.vertex(matrix, x2, y2, 0).uv(maxU, maxV).endVertex();
		bufferbuilder.vertex(matrix, x2, y1, 0).uv(maxU, minV).endVertex();
		bufferbuilder.vertex(matrix, x1, y1, 0).uv(minU, minV).endVertex();
		BufferUploader.drawWithShader(bufferbuilder.end());

	}

	public enum GasGaugeTextures implements ITexture {
		BACKGROUND_DEFAULT(14, 49, 0, 0, 256, 256, TEXTURE),
		LEVEL_DEFAULT(14, 49, 14, 0, 256, 256, TEXTURE);

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
