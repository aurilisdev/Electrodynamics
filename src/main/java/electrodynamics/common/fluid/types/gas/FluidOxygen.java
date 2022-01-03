package electrodynamics.common.fluid.types.gas;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidOxygen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "oxygen";
	
	public FluidOxygen() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "oxygen");
	}

}
