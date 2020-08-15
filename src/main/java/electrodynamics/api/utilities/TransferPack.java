package electrodynamics.api.utilities;

public class TransferPack {
	public static final TransferPack EMPTY = new TransferPack(0, 0);
	private double joules, voltage;

	private TransferPack(double joules, double voltage) {
		this.joules = joules;
		this.voltage = voltage;
	}

	public static TransferPack ampsVoltage(double amps, double voltage) {
		return new TransferPack(amps / 20.0 * voltage, voltage);
	}

	public static TransferPack joulesVoltage(double joules, double voltage) {
		return new TransferPack(joules, voltage);
	}

	public double getAmps() {
		return (joules / voltage) * 20.0;
	}

	public double getVoltage() {
		return voltage;
	}

	public double getJoules() {
		return joules;
	}

	public double getWatts() {
		return joules * 20.0;
	}

}
