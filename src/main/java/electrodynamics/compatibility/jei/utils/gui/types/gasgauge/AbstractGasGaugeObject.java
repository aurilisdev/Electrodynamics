package electrodynamics.compatibility.jei.utils.gui.types.gasgauge;

import electrodynamics.compatibility.jei.utils.gui.ScreenObject;

public abstract class AbstractGasGaugeObject extends ScreenObject {

	private IGasGaugeTexture bars;
	
	private double amount;
	
	public AbstractGasGaugeObject(IGasGaugeTexture base, IGasGaugeTexture bars, int x, int y, double amount) {
		super(base, x, y);
		this.bars = bars;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
	
	public IGasGaugeTexture getBarsTexture() {
		return bars;
	}
	
}
