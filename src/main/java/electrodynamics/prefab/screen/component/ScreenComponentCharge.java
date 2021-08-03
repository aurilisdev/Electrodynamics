package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;
import java.util.function.DoubleSupplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.util.ResourceLocation;

public class ScreenComponentCharge extends ScreenComponent {

    public static final int WIDTHARROW = 19;
    private static final int HEIGHTARROW = 10;
    private static final int POSXARROW = 0;
    private static final int POSYARROW = 0;

    private final DoubleSupplier progressInfoHandler;

    public ScreenComponentCharge(final DoubleSupplier progressInfoHandler, IScreenWrapper gui, int x, int y) {
	super(new ResourceLocation(References.ID + ":textures/screen/component/charge.png"), gui, x, y);
	this.progressInfoHandler = progressInfoHandler;
    }

    @Override
    public Rectangle getBounds(final int guiWidth, final int guiHeight) {
	return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, WIDTHARROW, HEIGHTARROW);
    }

    @Override
    public void renderBackground(MatrixStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
	UtilitiesRendering.bindTexture(resource);

	gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, POSXARROW, POSYARROW, WIDTHARROW, HEIGHTARROW);

	gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, POSXARROW + WIDTHARROW, POSYARROW,
		(int) (progressInfoHandler.getAsDouble() * WIDTHARROW), HEIGHTARROW);
    }

}
