package physica.api.core.cable;

import cofh.api.energy.IEnergyReceiver;
import physica.library.net.energy.EnergyNetwork;

public abstract interface IConductor extends IEnergyReceiver {
	public abstract EnergyNetwork getNetwork();

	public abstract EnergyNetwork getNetwork(boolean createIfNull);

	public EnumConductorType getCableType();

	public abstract void setNetwork(EnergyNetwork network);

	public abstract void refreshNetwork();

	public abstract void removeFromNetwork();

	public abstract void fixNetwork();
}
