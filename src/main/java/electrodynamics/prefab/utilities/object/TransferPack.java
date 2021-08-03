package electrodynamics.prefab.utilities.object;

public class TransferPack {
    public static final TransferPack EMPTY = new TransferPack(0, 0, 0);
    private double joules;
    private double voltage;
    private double temperature;

    private TransferPack(double joules, double voltage, double temperature) {
	this.joules = joules;
	this.voltage = voltage;
	this.temperature = temperature;
    }

    public static TransferPack ampsVoltage(double amps, double voltage) {
	return new TransferPack(amps / 20.0 * voltage, voltage, 0);
    }

    public static TransferPack joulesVoltage(double joules, double voltage) {
	return new TransferPack(joules, voltage, 0);
    }

    public static TransferPack temperature(double temp) {
	return new TransferPack(0, 0, temp);
    }

    public double getAmps() {
	return joules / voltage * 20.0;
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

    public double getTemperature() {
	return temperature;
    }

    public boolean valid() {
	return (int) voltage != 0 || (int) temperature != 0;
    }

}
