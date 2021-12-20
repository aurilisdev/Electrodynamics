package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidClay extends FluidNonPlaceable {

	public static final String FORGE_TAG = "clay";

	public FluidClay() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "clay");
	}

}
