package electrodynamics.compatibility.jei.utils.gui.types.fluidgauge;

import electrodynamics.compatibility.jei.utils.JeiTextures;

public class FluidGaugeObject extends AbstractFluidGaugeWrapper {

	public FluidGaugeObject(int x, int y, int amount) {
		super(JeiTextures.FLUID_GAUGE_DEFAULT, amount, x, y);
	}

	public int getFluidTextWidth() {
		return getWidth() - 2;
	}

	public int getFluidTextHeight() {
		return getHeight() - 2;
	}

	public int getFluidXPos() {
		return getX() + 1;
	}

	public int getFluidYPos() {
		return getY() + getHeight() - 1;
	}

}
