package electrodynamics.api.network.pipe;

import electrodynamics.api.network.INetwork;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.api.networks.IAbstractConductor;
import electrodynamics.common.block.subtype.SubtypePipe;

public interface IPipe extends IAbstractConductor {
    INetwork getNetwork();

    INetwork getNetwork(boolean createIfNull);

    void refreshNetwork();

    @Override
    void removeFromNetwork();

    void fixNetwork();

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