package electrodynamics.api.network.pipe;

import electrodynamics.api.network.IAbstractConductor;
import electrodynamics.api.network.INetwork;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.prefab.network.AbstractNetwork;

public interface IPipe extends IAbstractConductor {
    INetwork getNetwork();

    INetwork getNetwork(boolean createIfNull);

    void refreshNetwork();

    void refreshNetworkIfChange();

    @Override
    void removeFromNetwork();

    void destroyViolently();

    SubtypePipe getPipeType();

    @Override
    void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

    @Override
    default Object getConductorType() {
	return getPipeType();
    }

    @Override
    default double getMaxTransfer() {
	return getPipeType().maxTransfer;
    }

}