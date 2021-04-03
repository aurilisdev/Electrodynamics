package electrodynamics.api.network.conductor;

import electrodynamics.api.network.AbstractNetwork;
import electrodynamics.api.network.IAbstractConductor;
import electrodynamics.api.network.INetwork;
import electrodynamics.common.block.subtype.SubtypeWire;

public interface IConductor extends IAbstractConductor {
    INetwork getNetwork();

    INetwork getNetwork(boolean createIfNull);

    void refreshNetwork();

    void refreshNetworkIfChange();

    @Override
    void removeFromNetwork();

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
	return getWireType().capacity;
    }

}