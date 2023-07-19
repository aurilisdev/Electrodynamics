package electrodynamics.api.network.cable.type;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.common.block.subtype.SubtypeGasPipe;

public interface IGasPipe extends IRefreshableCable {

	SubtypeGasPipe getPipeType();

	@Override
	default Object getConductorType() {
		return getPipeType();
	}

	@Override
	default double getMaxTransfer() {
		return getPipeType().maxTransfer;
	}
	
	default boolean isInsulationFlammable() {
		return getPipeType().insulationMaterial.canCombust;
	}
	
	default double getEffectiveHeatLoss() {
		return getPipeType().effectivePipeHeatLoss;
	}
	
	default boolean canCorrodeFromAcid() {
		return getPipeType().pipeMaterial.corrodedByAcid;
	}

}
