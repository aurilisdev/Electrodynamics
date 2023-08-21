package electrodynamics.prefab.screen.component.types;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.ITexture.Textures;
import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.GuiGraphics;

/**
 * simple implementation of AbstractScreenComponent that allows custom images to be drawn to the screen
 * 
 * @author skip999
 *
 */
public class ScreenComponentGeneric extends AbstractScreenComponent {

	public ITexture texture;

	public int color = RenderingUtils.WHITE;

	public ScreenComponentGeneric(ITexture texture, int x, int y) {
		super(x, y, texture.textureWidth(), texture.textureHeight());
		this.texture = texture;
	}

	public ScreenComponentGeneric(int x, int y, int width, int height) {
		super(x, y, width, height);
		texture = Textures.NONE;
	}

	public ScreenComponentGeneric setColor(int color) {
		this.color = color;
		return this;
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (!isVisible()) {
			return;
		}
		RenderingUtils.color(color);
		graphics.blit(texture.getLocation(), guiWidth + xLocation, guiHeight + yLocation, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
		RenderingUtils.resetColor();
	}

}
