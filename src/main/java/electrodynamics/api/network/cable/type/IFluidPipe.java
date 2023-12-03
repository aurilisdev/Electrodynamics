package electrodynamics.api.network.cable.type;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;

public interface IFluidPipe extends IRefreshableCable {

	SubtypeFluidPipe getPipeType();

	@Override
	default Object getConductorType() {
		return getPipeType();
	}

	@Override
	default double getMaxTransfer() {
		return getPipeType().maxTransfer;
	}

}