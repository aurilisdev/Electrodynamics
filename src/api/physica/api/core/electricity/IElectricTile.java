package physica.api.core.electricity;

import cofh.api.energy.IEnergyConnection;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.abstraction.Face;

public interface IElectricTile extends IEnergyConnection {

	public static String ELECTRICITY_NBT = "Energy";

	default void setElectricityStored(int electricity)
	{
	}

	default boolean canConnectElectricity(Face from)
	{
		return true;
	}

	@Override
	@Deprecated
	default boolean canConnectEnergy(ForgeDirection from)
	{
		return canConnectElectricity(Face.Parse(from));
	}

}
