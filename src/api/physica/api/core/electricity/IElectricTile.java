package physica.api.core.electricity;

import cofh.api.energy.IEnergyConnection;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
public interface IElectricTile extends IEnergyConnection {

	public static String ELECTRICITY_NBT = "Energy";

	default void setElectricityStored(int electricity)
	{
	}

	default boolean canConnectElectricity(ForgeDirection from)
	{
		return true;
	}

	@Override
	@Deprecated
	default boolean canConnectEnergy(ForgeDirection from)
	{
		return canConnectElectricity(from);
	}

}
