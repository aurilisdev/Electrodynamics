package electrodynamics.api.tile.electric;

public class ElectrodynamicStorage implements IElectrodynamic {
	private double max;
	private double joules;

	public ElectrodynamicStorage(double max, double val) {
		this.max = max;
		this.joules = val;
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

}
