package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidHydrogenFluoride extends FluidNonPlaceable {

	// Tags typically have underscores inbetween names!
	public static final String FORGE_TAG = "hydrofluoric_acid";

	public FluidHydrogenFluoride() {
		super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "hydrogenfluoride", -375879936);
	}

}
