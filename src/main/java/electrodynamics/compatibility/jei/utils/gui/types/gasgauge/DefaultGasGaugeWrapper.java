package electrodynamics.compatibility.jei.utils.gui.types.gasgauge;

public class DefaultGasGaugeWrapper extends AbstractGasGaugeWrapper {

	public DefaultGasGaugeWrapper(int xStart, int yStart, double amount) {
		super(GAS_GAUGES, xStart, yStart, 0, 0, 14, 49, amount);
	}

	@Override
	public int getGaugeOffset() {
		return 14;
	}

	@Override
	public int getMercuryOffset() {
		return 28;
	}

}
