package electrodynamics.api.electricity.generator;

import electrodynamics.prefab.utilities.object.TransferPack;

public interface IElectricGenerator {

	void setMultiplier(double val);

	double getMultiplier();

	TransferPack getProduced();
}
