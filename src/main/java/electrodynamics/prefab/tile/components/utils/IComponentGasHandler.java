package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.tile.components.Component;

public interface IComponentGasHandler extends Component {

	public static final int TANK_MULTIPLIER = 1000;

	PropertyGasTank[] getInputTanks();

	PropertyGasTank[] getOutputTanks();

}
