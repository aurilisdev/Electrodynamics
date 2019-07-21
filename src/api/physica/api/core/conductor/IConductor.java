package physica.api.core.conductor;

import physica.api.core.electricity.IElectricityReceiver;
import physica.library.net.energy.EnergyNetwork;

public interface IConductor extends IElectricityReceiver {
	public EnergyNetwork getNetwork();

	public EnergyNetwork getNetwork(boolean createIfNull);

	public EnumConductorType getCableType();

	public void setNetwork(EnergyNetwork network);

	public void refreshNetwork();

	public void removeFromNetwork();

	public void fixNetwork();

	public void destroyNodeViolently();
}
