package electrodynamics.compatibility.jei.utils.gui.types.gasgauge;

import electrodynamics.api.screen.ITexture;
import electrodynamics.compatibility.jei.utils.gui.ScreenObject;

public abstract class AbstractGasGaugeWrapper extends ScreenObject {

	private double amount;
	
	public AbstractGasGaugeWrapper(ITexture texture, int x, int y, double amount) {
		super(texture, x, y);
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
	
	public abstract int getGaugeOffset();
	
	public abstract int getMercuryOffset();
	
}
