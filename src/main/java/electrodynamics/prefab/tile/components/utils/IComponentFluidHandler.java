package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.fluid.PropertyFluidTank;
import electrodynamics.prefab.tile.components.Component;

public interface IComponentFluidHandler extends Component {

	PropertyFluidTank[] getInputTanks();
	
	PropertyFluidTank[] getOutputTanks();
	
}
