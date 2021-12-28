package electrodynamics.common.fluid.types.gas;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidHydrogen extends FluidNonPlaceable {

	public static final String FORGE_TAG = "hydrogen";
	
	public FluidHydrogen() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "hydrogen");
	}

}
