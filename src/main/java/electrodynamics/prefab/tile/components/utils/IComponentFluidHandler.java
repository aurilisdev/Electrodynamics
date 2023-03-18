package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.fluid.PropertyFluidTank;
import electrodynamics.prefab.tile.components.Component;

public interface IComponentFluidHandler extends Component {
	
	public static final int TANK_MULTIPLER = 1000;

	PropertyFluidTank[] getInputTanks();

	PropertyFluidTank[] getOutputTanks();

}
