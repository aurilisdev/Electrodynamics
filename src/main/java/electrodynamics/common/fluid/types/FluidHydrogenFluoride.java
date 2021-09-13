package electrodynamics.common.fluid.types;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidHydrogenFluoride extends FluidNonPlaceable {

    public FluidHydrogenFluoride() {
	super(() -> DeferredRegisters.ITEM_CANISTERREINFORCED, References.ID, "hydrogenfluoride", -375879936);
    }

}
