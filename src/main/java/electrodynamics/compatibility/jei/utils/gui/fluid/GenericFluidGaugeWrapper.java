package electrodynamics.compatibility.jei.utils.gui.fluid;

import electrodynamics.compatibility.jei.utils.gui.ScreenObjectWrapper;

public abstract class GenericFluidGaugeWrapper extends ScreenObjectWrapper {

    private int amount;

    public GenericFluidGaugeWrapper(String texture, int amount, int xStart, int yStart, int textX, int textY, int height, int width) {
	super(texture, xStart, yStart, textX, textY, height, width);
	this.amount = amount;
    }

    public int getAmount() {
	return amount;
    }

    public int getFluidTextHeight() {
	return getWidth() - 2;
    }

    public int getFluidTextWidth() {
	return getLength() - 2;
    }

    public int getFluidTextXPos() {
	return getXPos() + 1;
    }

    public int getFluidTextYPos() {
	return getYPos() + getWidth() - 2;
    }

}
