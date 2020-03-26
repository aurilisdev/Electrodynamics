package universalelectricity.api.electricity;

import net.minecraft.util.EnumFacing;

public interface IElectricityProvider extends IElectricTile {
	default int extractElectricity(EnumFacing from, int maxExtract, boolean simulate)
	{
		return 0;
	}

}
