package electrodynamics.api.tile.processing;

import electrodynamics.api.tile.electric.IElectrodynamic;

public interface IElectricProcessor extends IElectrodynamic {
    double getJoulesPerTick();
}
