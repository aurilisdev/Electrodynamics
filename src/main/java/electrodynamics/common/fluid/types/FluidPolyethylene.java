package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidPolyethylene extends FluidNonPlaceable {

	public static final String FORGE_TAG = "polyethylene";

	public FluidPolyethylene() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "polyethylene", -376664948);
	}

}
