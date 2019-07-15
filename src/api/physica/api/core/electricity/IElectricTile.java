package physica.api.core.electricity;

import cofh.api.energy.IEnergyConnection;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
public interface IElectricTile extends IEnergyConnection {

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
