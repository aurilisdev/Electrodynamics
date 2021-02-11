package electrodynamics.api.network.conductor;

import electrodynamics.api.network.INetwork;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.api.networks.IAbstractConductor;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.common.block.subtype.SubtypeWire;

public interface IConductor extends IPowerReceiver, IAbstractConductor {
	INetwork getNetwork();

	INetwork getNetwork(boolean createIfNull);

	void refreshNetwork();

	@Override
	void removeFromNetwork();

	void fixNetwork();

	void destroyViolently();

	SubtypeWire getWireType();

	@Override
	void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

	@Override
	default Object getConductorType() {
		return getWireType();
	}

	@Override
	default double getMaxTransfer() {
		return getWireType().maxAmps;
	}

}