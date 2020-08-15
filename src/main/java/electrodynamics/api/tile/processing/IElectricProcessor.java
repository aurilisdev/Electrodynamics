package electrodynamics.api.tile.processing;

import electrodynamics.api.tile.electric.IJoulesStorage;

public interface IElectricProcessor extends IJoulesStorage {
	double getJoulesPerTick();
}
