package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.tile.components.IComponent;

public interface IComponentGasHandler extends IComponent {

	public static final int TANK_MULTIPLIER = 1000;

	PropertyGasTank[] getInputTanks();

	PropertyGasTank[] getOutputTanks();

}
