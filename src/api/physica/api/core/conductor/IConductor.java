package physica.api.core.conductor;

import physica.api.core.electricity.IElectricityReceiver;
import physica.library.net.energy.ElectricNetwork;

public interface IConductor extends IElectricityReceiver {
	ElectricNetwork getNetwork();

	ElectricNetwork getNetwork(boolean createIfNull);

	EnumConductorType getCableType();

	void setNetwork(ElectricNetwork network);

	void refreshNetwork();

	void removeFromNetwork();

	void fixNetwork();

	void destroyNodeViolently();
}
