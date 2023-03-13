package electrodynamics.prefab.tile.components.type;

import electrodynamics.api.fluid.PropertyFluidTank;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.IComponentFluidHandler;

public class ComponentGasHandlerMulti implements IComponentFluidHandler {

	@Override
	public ComponentType getType() {
		return ComponentType.GasHandler;
	}

	@Override
	public PropertyFluidTank[] getInputTanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyFluidTank[] getOutputTanks() {
		// TODO Auto-generated method stub
		return null;
	}

}
