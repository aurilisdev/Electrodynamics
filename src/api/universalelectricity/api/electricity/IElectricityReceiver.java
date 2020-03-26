package universalelectricity.api.electricity;

import net.minecraft.util.EnumFacing;

public interface IElectricityReceiver extends IElectricTile {
	default int receiveElectricity(EnumFacing from, int maxReceive, boolean simulate) {
		return 0;
	}

}
