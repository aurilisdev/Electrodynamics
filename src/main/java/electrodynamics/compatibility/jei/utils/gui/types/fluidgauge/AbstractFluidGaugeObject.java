package electrodynamics.compatibility.jei.utils.gui.types.fluidgauge;

import electrodynamics.api.screen.ITexture;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;

public abstract class AbstractFluidGaugeObject extends ScreenObject {

	private int amount;

	public AbstractFluidGaugeObject(ITexture texture, int amount, int x, int y) {
		super(texture, x, y);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public abstract int getFluidTextWidth();

	public abstract int getFluidTextHeight();

	public abstract int getFluidXPos();

	public abstract int getFluidYPos();

}
