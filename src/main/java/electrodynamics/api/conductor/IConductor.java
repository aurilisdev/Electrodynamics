package electrodynamics.api.conductor;

import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.electricity.network.ElectricNetwork;

public interface IConductor extends IPowerReceiver {
	ElectricNetwork getNetwork();

	ElectricNetwork getNetwork(boolean createIfNull);

	void setNetwork(ElectricNetwork network);

	void refreshNetwork();

	void removeFromNetwork();

	void fixNetwork();

	void destroyViolently();

	SubtypeWire getWireType();

}