package electrodynamics.compatibility.jei.utils.gui.types.fluidgauge;

import electrodynamics.compatibility.jei.utils.gui.JeiTextures;

public class FluidGaugeObject extends AbstractFluidGaugeObject {

	public FluidGaugeObject(int x, int y, int amount) {
		super(JeiTextures.FLUID_GAUGE_DEFAULT, amount, x, y);
	}

	@Override
	public int getFluidTextWidth() {
		return getWidth() - 2;
	}

	@Override
	public int getFluidTextHeight() {
		return getHeight() - 2;
	}

	@Override
	public int getFluidXPos() {
		return getX() + 1;
	}

	@Override
	public int getFluidYPos() {
		return getY() + getHeight() - 1;
	}

}
