package physica.api.core.electricity;

import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.FaceDirection;

@SuppressWarnings("deprecation")
public interface IElectricityReceiver extends IElectricTile, IEnergyReceiver {
	default int getElectricityStored(FaceDirection from)
	{
		return 0;
	}

	default int getElectricCapacity(FaceDirection from)
	{
		return 0;
	}

	default int receiveElectricity(FaceDirection from, int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	@Deprecated
	default int getEnergyStored(ForgeDirection from)
	{
		return getElectricityStored(FaceDirection.Parse(from));
	}

	@Override
	@Deprecated
	default int getMaxEnergyStored(ForgeDirection from)
	{
		return getElectricCapacity(FaceDirection.Parse(from));
	}

	@Override
	@Deprecated
	default int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return receiveElectricity(FaceDirection.Parse(from), maxReceive, simulate);
	}
}
