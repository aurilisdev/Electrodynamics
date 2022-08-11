package physica.api.core.electricity;

import cofh.api.energy.IEnergyHandler;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.Face;

public interface IElectricityHandler extends IElectricityProvider, IElectricityReceiver, IEnergyHandler {

	@Override
	default int getElectricityStored(Face from)
	{
		return 0;
	}

	@Override
	default int getElectricCapacity(Face from)
	{
		return 0;
	}

	@Override
	default int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return IElectricityReceiver.super.receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	default int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return IElectricityProvider.super.extractEnergy(from, maxExtract, simulate);
	}

	@Override
	default int getEnergyStored(ForgeDirection from)
	{
		return getElectricityStored(Face.Parse(from));
	}

	@Override
	default int getMaxEnergyStored(ForgeDirection from)
	{
		return getElectricCapacity(Face.Parse(from));
	}

}
