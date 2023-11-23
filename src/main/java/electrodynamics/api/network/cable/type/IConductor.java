package electrodynamics.api.network.cable.type;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.subtype.SubtypeWire;
public interface IConductor extends IRefreshableCable {

	SubtypeWire getWireType();

	@Override
	default Object getConductorType() {
		return getWireType();
	}

	@Override
	default double getMaxTransfer() {
		return getWireType().conductor.ampacity;
	}


}