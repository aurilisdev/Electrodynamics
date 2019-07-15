package physica.api.core.electricity;

import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
public interface IElectricityReceiver extends IElectricTile, IEnergyReceiver {
	default int getElectricityStored(ForgeDirection from)
	{
		return 0;
	}

	default int getElectricCapacity(ForgeDirection from)
	{
		return 0;
	}

	default int receiveElectricity(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	@Deprecated
	default int getEnergyStored(ForgeDirection from)
	{
		return getElectricityStored(from);
	}

	@Override
	@Deprecated
	default int getMaxEnergyStored(ForgeDirection from)
	{
		return getElectricCapacity(from);
	}

	@Override
	@Deprecated
	default int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return receiveElectricity(from, maxReceive, simulate);
	}
}
