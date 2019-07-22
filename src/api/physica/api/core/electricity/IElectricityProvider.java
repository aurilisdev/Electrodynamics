package physica.api.core.electricity;

import cofh.api.energy.IEnergyProvider;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.FaceDirection;

@SuppressWarnings("deprecation")
public interface IElectricityProvider extends IElectricTile, IEnergyProvider {
	abstract int getElectricityStored(FaceDirection from);

	abstract int getElectricCapacity(FaceDirection from);

	default int extractElectricity(FaceDirection from, int maxExtract, boolean simulate)
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
	default int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return extractElectricity(FaceDirection.Parse(from), maxExtract, simulate);
	}
}
