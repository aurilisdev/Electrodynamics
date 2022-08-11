package physica.api.core.electricity;

import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.Face;

public interface IElectricityReceiver extends IElectricTile, IEnergyReceiver {
	default int getElectricityStored(Face from) {
		return 0;
	}

	default int getElectricCapacity(Face from) {
		return 0;
	}

	default int receiveElectricity(Face from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	@Deprecated
	default int getEnergyStored(ForgeDirection from) {
		return getElectricityStored(Face.Parse(from));
	}

	@Override
	@Deprecated
	default int getMaxEnergyStored(ForgeDirection from) {
		return getElectricCapacity(Face.Parse(from));
	}

	@Override
	@Deprecated
	default int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return receiveElectricity(Face.Parse(from), maxReceive, simulate);
	}
}
