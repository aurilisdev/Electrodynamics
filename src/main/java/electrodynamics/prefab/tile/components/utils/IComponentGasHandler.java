package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.prefab.tile.components.IComponent;

public interface IComponentGasHandler extends IComponent {
	
	PropertyGasTank[] getInputTanks();
	
	PropertyGasTank[] getOUtputTanks();

}
