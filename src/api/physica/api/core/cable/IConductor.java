package physica.api.core.cable;

import cofh.api.energy.IEnergyReceiver;
import physica.library.net.energy.EnergyNetwork;

public interface IConductor extends IEnergyReceiver {
	public EnergyNetwork getNetwork();

	public EnergyNetwork getNetwork(boolean createIfNull);

	public EnumConductorType getCableType();

	public void setNetwork(EnergyNetwork network);

	public void refreshNetwork();

	public void removeFromNetwork();

	public void fixNetwork();

	public void destroyNodeViolently();
}
