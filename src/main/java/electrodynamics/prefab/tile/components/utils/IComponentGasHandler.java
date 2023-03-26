package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.tile.components.Component;

public interface IComponentGasHandler extends Component {
	
	PropertyGasTank[] getInputTanks();
	
	PropertyGasTank[] getOUtputTanks();

}
