package electrodynamics.api.network.cable.type;

import electrodynamics.api.network.cable.IRefreshableConductor;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;

public interface IFluidPipe extends IRefreshableConductor {

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