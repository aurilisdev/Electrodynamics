package universalelectricity.api.electricity;

import net.minecraft.util.EnumFacing;

public interface IElectricTile {

	default boolean canConnectElectricity(EnumFacing from) {
		return true;
	}

	default int getElectricityStored(EnumFacing from) {
		return 0;
	}

	default int getElectricCapacity(EnumFacing from) {
		return 0;
	}

}
