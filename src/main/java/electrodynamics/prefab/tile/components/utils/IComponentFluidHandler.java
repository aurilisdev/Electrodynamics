package electrodynamics.prefab.tile.components.utils;

import electrodynamics.api.fluid.PropertyFluidTank;
import electrodynamics.prefab.tile.components.IComponent;

public interface IComponentFluidHandler extends IComponent {

	public static final int TANK_MULTIPLER = 1000;

	PropertyFluidTank[] getInputTanks();

	PropertyFluidTank[] getOutputTanks();

}
