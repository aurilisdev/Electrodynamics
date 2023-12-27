package electrodynamics.prefab.utilities.object;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class TransferPack {
	public static final TransferPack EMPTY = new TransferPack(0, 0);
	private double joules;
	private double voltage;

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
		return joules / voltage * 20.0;
	}

	public double getAmpsInTicks() {
		return joules / voltage;
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

	public boolean valid() {
		return (int) voltage != 0;
	}

	public CompoundNBT writeToTag() {
		CompoundNBT tag = new CompoundNBT();
		tag.putDouble("joules", joules);
		tag.putDouble("voltage", voltage);
		return tag;
	}

	public static TransferPack readFromTag(CompoundNBT tag) {
		return joulesVoltage(tag.getDouble("joules"), tag.getDouble("voltage"));
	}

	public void writeToBuffer(PacketBuffer buf) {
		buf.writeDouble(joules);
		buf.writeDouble(voltage);
	}

	public static TransferPack readFromBuffer(PacketBuffer buf) {
		return joulesVoltage(buf.readDouble(), buf.readDouble());
	}

	@Override
	public String toString() {
		return "Joules: " + joules + " J, Voltage: " + voltage + " V";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TransferPack) {
			TransferPack other = (TransferPack) obj;
			return other.joules == joules && other.voltage == voltage;
		}
		return false;
	}

}
