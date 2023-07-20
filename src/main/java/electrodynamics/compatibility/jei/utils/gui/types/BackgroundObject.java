package electrodynamics.compatibility.jei.utils.gui.types;

import electrodynamics.compatibility.jei.utils.gui.JeiTextures;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;

public class BackgroundObject extends ScreenObject {

	private int width;
	private int height;

	public BackgroundObject(int width, int height) {
		super(JeiTextures.BACKGROUND_DEFAULT, 0, 0);
		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
