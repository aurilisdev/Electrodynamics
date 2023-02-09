package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponent;
import electrodynamics.prefab.utilities.RenderingUtils;

/**
 * simple implementation of AbstractScreenComponent that allows custom images to be drawn to the screen
 * 
 * @author skip999
 *
 */
public class ScreenComponentGeneric extends AbstractScreenComponent {

	private int color = RenderingUtils.WHITE;

	public ScreenComponentGeneric(ITexture texture, IScreenWrapper gui, int x, int y) {
		super(texture, gui, x, y);
	}

	public ScreenComponentGeneric setColor(int color) {
		this.color = color;
		return this;
	}

	@Override
	public Rectangle getBounds(int guiWidth, int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, texture.textureWidth(), texture.textureHeight());
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		RenderingUtils.bindTexture(texture.getLocation());
		RenderingUtils.color(color);
		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
		RenderingUtils.resetColor();
	}

}
