package electrodynamics.api.capability.types.electrodynamic;

public class ElectrodynamicStorage implements ICapabilityElectrodynamic {
	private final double max;
	private double joules;

	public ElectrodynamicStorage(double max, double val) {
		this.max = max;
		joules = val;
	}

	@Override
	public void setJoulesStored(double joules) {
		this.joules = joules;
	}

	@Override
	public double getJoulesStored() {
		return joules;
	}

	@Override
	public double getMaxJoulesStored() {
		return max;
	}

	@Override
	public void onChange() {
		// Not Needed
	}

}
