package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;
import java.util.function.DoubleSupplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentProgress extends ScreenComponent {
	public static final int WIDTHARROW = 22;
	private static final int HEIGHTARROW = 16;
	private static final int POSXARROW = 0;
	private static final int POSYARROW = 0;
	private static final int WIDTHFLAME = 14;
	private static final int HEIGHTFLAME = 14;
	private static final int POSXFLAME = 0;
	private static final int POSYFLAME = 19;
	private boolean left = false;
	private boolean isFlame = false;

	private final DoubleSupplier progressInfoHandler;

	public ScreenComponentProgress(final DoubleSupplier progressInfoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(new ResourceLocation(References.ID + ":textures/screen/component/progress.png"), gui, x, y);
		this.progressInfoHandler = progressInfoHandler;
	}

	public ScreenComponentProgress flame() {
		isFlame = true;
		return this;
	}

	public ScreenComponentProgress left() {
		left = true;
		return this;
	}

	@Override
	public Rectangle getBounds(final int guiWidth, final int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, isFlame ? WIDTHFLAME : WIDTHARROW, isFlame ? HEIGHTFLAME : HEIGHTARROW);
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		RenderingUtils.bindTexture(resource);
		if (left) {
			gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, POSXARROW + WIDTHARROW * 3, POSYARROW, WIDTHARROW, HEIGHTARROW);
		} else {
			gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, isFlame ? POSXFLAME : POSXARROW,
					isFlame ? POSYFLAME + HEIGHTFLAME : POSYARROW, isFlame ? WIDTHFLAME : WIDTHARROW, isFlame ? HEIGHTFLAME : HEIGHTARROW);
		}
		if (isFlame) {
			int scale = (int) (progressInfoHandler.getAsDouble() * HEIGHTFLAME);
			gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation + HEIGHTFLAME - scale, POSXFLAME, POSYFLAME + HEIGHTFLAME - scale,
					WIDTHFLAME, scale);
		} else if (left) {
			int progress = (int) (progressInfoHandler.getAsDouble() * WIDTHARROW);
			int xStart = POSXARROW + WIDTHARROW * 2 + (WIDTHARROW - progress);
			gui.drawTexturedRect(stack, guiWidth + xLocation + WIDTHARROW - progress, guiHeight + yLocation, xStart, POSYARROW, progress, HEIGHTARROW);
		} else {
			gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, POSXARROW + WIDTHARROW, POSYARROW,
					(int) (progressInfoHandler.getAsDouble() * WIDTHARROW), HEIGHTARROW);
		}
	}

}