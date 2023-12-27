package electrodynamics.prefab.screen.component.types;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.ITexture.Textures;
import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;

/**
 * simple implementation of AbstractScreenComponent that allows custom images to be drawn to the screen
 * 
 * @author skip999
 *
 */
public class ScreenComponentGeneric extends AbstractScreenComponent {

	public ITexture texture;

	public Color color = Color.WHITE;

	public ScreenComponentGeneric(ITexture texture, int x, int y) {
		super(x, y, texture.textureWidth(), texture.textureHeight());
		this.texture = texture;
	}

	public ScreenComponentGeneric(int x, int y, int width, int height) {
		super(x, y, width, height);
		texture = Textures.NONE;
	}

	public ScreenComponentGeneric setColor(Color color) {
		this.color = color;
		return this;
	}

	@Override
	public void renderBackground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (!isVisible()) {
			return;
		}
		RenderingUtils.setShaderColor(color);
		RenderingUtils.bindTexture(texture.getLocation());
		blit(stack, guiWidth + this.x, guiHeight + this.y, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
		RenderingUtils.resetShaderColor();
	}

}
