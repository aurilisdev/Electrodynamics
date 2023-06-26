package electrodynamics.compatibility.jei.utils.gui.types.gasgauge;

import electrodynamics.compatibility.jei.utils.gui.ScreenObject;

public abstract class AbstractGasGaugeObject extends ScreenObject {

	private IGasGaugeTexture mercury;
	private IGasGaugeTexture bars;
	
	private double amount;
	
	public AbstractGasGaugeObject(IGasGaugeTexture base, IGasGaugeTexture mercury, IGasGaugeTexture bars, int x, int y, double amount) {
		super(base, x, y);
		this.mercury = mercury;
		this.bars = bars;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
	
	public IGasGaugeTexture getMercuryTexture() {
		return mercury;
	}
	
	public IGasGaugeTexture getBarsTexture() {
		return bars;
	}
	
}
