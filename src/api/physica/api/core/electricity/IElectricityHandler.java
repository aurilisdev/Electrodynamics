package physica.api.core.electricity;

import net.minecraftforge.common.util.ForgeDirection;

public interface IElectricityHandler extends IElectricityProvider, IElectricityReceiver {

	default int getElectricityStored(ForgeDirection from)
	{
		return 0;
	}

	default int getElectricCapacity(ForgeDirection from)
	{
		return 0;
	}

	@Override
	default int getEnergyStored(ForgeDirection from)
	{
		return getElectricityStored(from);
	}

	@Override
	default int getMaxEnergyStored(ForgeDirection from)
	{
		return getElectricCapacity(from);
	}

}
