package electrodynamics.api.tile.electric;

public interface IJoulesStorage {
	void setJoulesStored(double joules);

	double getJoulesStored();

	double getMaxJoulesStored();

}
