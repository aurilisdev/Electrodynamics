package physica.api.core.conductor;

import physica.api.core.electricity.IElectricityReceiver;
import physica.library.net.energy.ElectricNetwork;

public interface IConductor extends IElectricityReceiver {
	public ElectricNetwork getNetwork();

	public ElectricNetwork getNetwork(boolean createIfNull);

	public EnumConductorType getCableType();

	public void setNetwork(ElectricNetwork network);

	public void refreshNetwork();

	public void removeFromNetwork();

	public void fixNetwork();

	public void destroyNodeViolently();
}
