package electrodynamics.api.network.cable.type;

import electrodynamics.api.network.cable.IRefreshableConductor;
import electrodynamics.common.block.subtype.SubtypeWire;

public interface IConductor extends IRefreshableConductor {

	SubtypeWire getWireType();

	@Override
	default Object getConductorType() {
		return getWireType();
	}

	@Override
	default double getMaxTransfer() {
		return getWireType().capacity;
	}

}