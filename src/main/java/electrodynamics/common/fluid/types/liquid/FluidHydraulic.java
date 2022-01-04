package electrodynamics.common.fluid.types.liquid;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidHydraulic extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydraulic_fluid";

	public FluidHydraulic() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "hydraulic");
	}

}
