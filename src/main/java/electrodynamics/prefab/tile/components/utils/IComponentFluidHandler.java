package electrodynamics.prefab.tile.components.utils;

import electrodynamics.prefab.tile.components.Component;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IComponentFluidHandler extends Component {

	FluidTank[] getInputTanks();
	
	FluidTank[] getOutputTanks();
	
}
