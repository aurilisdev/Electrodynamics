package electrodynamics.api.network.cable;

import electrodynamics.api.network.INetwork;
import electrodynamics.prefab.network.AbstractNetwork;

public interface IRefreshableConductor extends IAbstractConductor {

	INetwork getNetwork();

	INetwork getNetwork(boolean createIfNull);

	void refreshNetwork();

	void refreshNetworkIfChange();

	@Override
	void removeFromNetwork();

	void destroyViolently();

	@Override
	void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);
	
}
