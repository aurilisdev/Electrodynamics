package physica.api.core.electricity;

import cofh.api.energy.IEnergyProvider;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
public interface IElectricityProvider extends IElectricTile, IEnergyProvider {
	abstract int getElectricityStored(ForgeDirection from);

	abstract int getElectricCapacity(ForgeDirection from);

	default int extractElectricity(ForgeDirection from, int maxExtract, boolean simulate)
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
	default int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return extractElectricity(from, maxExtract, simulate);
	}
}
