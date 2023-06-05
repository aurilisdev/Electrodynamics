package electrodynamics.api.network.cable.type;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;

public interface IConductor extends IRefreshableCable {

	SubtypeWire getWireType();
	
	WireColor getWireColor();

	@Override
	default Object getConductorType() {
		return getWireType();
	}

	@Override
	default double getMaxTransfer() {
		return getWireType().conductor.ampacity;
	}

}