package electrodynamics.api.utilities;

public class TransferPack {
	public static final TransferPack EMPTY = new TransferPack(0, 0);
	private double amps, voltage;

	private TransferPack(double amps, double voltage) {
		this.amps = amps;
		this.voltage = voltage;
	}

	public static TransferPack ampsVoltage(double amps, double voltage) {
		return new TransferPack(amps, voltage);
	}

	public static TransferPack joulesVoltage(double joules, double voltage) {
		return new TransferPack(joules / voltage, voltage);
	}

	public double getAmps() {
		return amps;
	}

	public double getVoltage() {
		return voltage;
	}

	public double getJoules() {
		return amps * voltage;
	}

}
