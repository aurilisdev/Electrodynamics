package electrodynamics.prefab.screen.component;

import java.util.function.DoubleSupplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentProgress extends ScreenComponentGeneric {

	public static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/progress.png");

	private final DoubleSupplier progressInfoHandler;
	private final ProgressBars bar;

	public ScreenComponentProgress(ProgressBars progressBar, final DoubleSupplier progressInfoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(progressBar.off, gui, x, y);
		this.progressInfoHandler = progressInfoHandler;
		bar = progressBar;
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		ProgressTextures on = bar.on;
		RenderingUtils.bindTexture(on.getLocation());
		int progress;
		switch (bar) {
		case PROGRESS_ARROW_LEFT:
			progress = (int) (progressInfoHandler.getAsDouble() * on.textureWidth());
			int xStart = on.textureU() + on.textureWidth() - progress;
			gui.drawTexturedRect(stack, guiWidth + xLocation + on.textureWidth() - progress, guiHeight + yLocation, xStart, on.textureV(), progress, on.textureHeight(), on.imageWidth(), on.imageHeight());
			break;
		case COUNTDOWN_FLAME:
			progress = (int) (progressInfoHandler.getAsDouble() * on.textureHeight());
			gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation + on.textureHeight() - progress, on.textureU(), on.textureV() + on.textureHeight() - progress, on.textureWidth(), progress, on.imageWidth(), on.imageHeight());
			break;
		case PROGRESS_ARROW_RIGHT, BATTERY_CHARGE_RIGHT:
			gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, on.textureU(), on.textureV(), (int) (progressInfoHandler.getAsDouble() * on.textureWidth()), on.textureHeight(), on.imageWidth(), on.imageHeight());
			break;
		default:
			break;
		}

	}

	public enum ProgressBars {

		PROGRESS_ARROW_RIGHT(ProgressTextures.ARROW_RIGHT_OFF, ProgressTextures.ARROW_RIGHT_ON),
		PROGRESS_ARROW_LEFT(ProgressTextures.ARROW_LEFT_OFF, ProgressTextures.ARROW_LEFT_ON),
		COUNTDOWN_FLAME(ProgressTextures.FLAME_OFF, ProgressTextures.FLAME_ON),
		BATTERY_CHARGE_RIGHT(ProgressTextures.BATTER_CHARGE_RIGHT_OFF, ProgressTextures.BATTER_CHARGE_RIGHT_ON);

		public final ProgressTextures off;
		public final ProgressTextures on;

		ProgressBars(ProgressTextures off, ProgressTextures on) {
			this.off = off;
			this.on = on;
		}

	}

	public enum ProgressTextures implements ITexture {
		ARROW_RIGHT_OFF(22, 16, 0, 0, 44, 16, "arrow_right"),
		ARROW_RIGHT_ON(22, 16, 22, 0, 44, 16, "arrow_right"),
		ARROW_LEFT_ON(22, 16, 0, 0, 44, 16, "arrow_left"),
		ARROW_LEFT_OFF(22, 16, 22, 0, 44, 16, "arrow_left"),
		FLAME_ON(14, 14, 0, 0, 14, 28, "flame"),
		FLAME_OFF(14, 14, 0, 14, 14, 28, "flame"),
		BATTER_CHARGE_RIGHT_OFF(19, 10, 0, 0, 38, 10, "battery_charge"),
		BATTER_CHARGE_RIGHT_ON(19, 10, 19, 0, 38, 10, "battery_charge"),
		COMPRESS_ARROW_OFF(15, 9, 0, 0, 15, 9, "compressarrow"),
		DECOMPRESS_ARROW_OFF(15, 9, 0, 0, 15, 9, "decompressarrow");

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		ProgressTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			loc = new ResourceLocation(References.ID + ":textures/screen/component/progress/" + name + ".png");
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