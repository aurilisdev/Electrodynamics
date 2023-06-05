package electrodynamics.compatibility.jei.utils.gui.fluid;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;
import net.minecraft.resources.ResourceLocation;

public abstract class GenericFluidGaugeWrapper extends ScreenObjectWrapper {

	private int amount;

	public GenericFluidGaugeWrapper(ResourceLocation texture, int amount, int xStart, int yStart, int textX, int textY, int width, int height) {
		super(texture, xStart, yStart, textX, textY, width, height);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public int getFluidTextWidth() {
		return getWidth() - 2;
	}

	public int getFluidTextHeight() {
		return getHeight() - 2;
	}

	public int getFluidXPos() {
		return getXPos() + 1;
	}

	public int getFluidYPos() {
		return getYPos() + getHeight() - 1;
	}

}
