package electrodynamics.api.network.conductor;

import electrodynamics.api.network.INetwork;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.common.block.subtype.SubtypeWire;

public interface IConductor extends IPowerReceiver {
	INetwork getNetwork();

	INetwork getNetwork(boolean createIfNull);

	void setNetwork(INetwork network);

	void refreshNetwork();

	void removeFromNetwork();

	void fixNetwork();

	void destroyViolently();

	SubtypeWire getWireType();

}